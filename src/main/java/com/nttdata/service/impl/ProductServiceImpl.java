package com.nttdata.service.impl;

import com.nttdata.client.ClientResultClient;
import com.nttdata.client.dao.ClientResult;
import com.nttdata.events.EventDispatcher;
import com.nttdata.model.dao.*;
import com.nttdata.model.dao.util.AccountType;
import com.nttdata.model.dao.util.ClientType;
import com.nttdata.model.dao.util.ProdcutType;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.ProductRepository;
import com.nttdata.service.AccountService;
import com.nttdata.service.ProductService;
import com.nttdata.service.impl.util.ConvertToAccountType;
import com.nttdata.util.reporte.GeneratePdfReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl extends AbstractServiceImpl<Product> implements ProductService {

    private final Logger LOGGER= LoggerFactory.getLogger("ProductServiceImpl");
    private ClientResult clientResult;
    private final ProductRepository productRepository;
    private final ClientResultClient clientResultClient;
    private final AccountService accountService;
    private final GeneratePdfReport generatePdfReport;

    public ProductServiceImpl(ProductRepository productRepository,
                              AccountService accountService,
                              ClientResultClient clientResultClient,
                              GeneratePdfReport generatePdfReport,
                              EventDispatcher eventDispatcher){
        this.productRepository=productRepository;
        this.accountService=accountService;
        this.clientResultClient=clientResultClient;
        this.generatePdfReport=generatePdfReport;
    }

    @Override
    AbstractRepository<Product> getRepo() {
        return productRepository;
    }

    @Override
    public Optional<Product>  createProduct(ProductRequest productRequest) {

        /**
         * Validamos el tipo de cliente
         * 1 persona
         * 2 empresa
         * */
        if(productRequest.getClientType() == 1){
            /**
             *  1 vefificamos la exitencia del usuario
             *  haecemos una llamada al microservicio cliente
             *
             *  2 verificamos que solo tenga una cuenta de ahorro,
             *  cuenta corriente, y si es cuenta a plazo fijo, puede tener muchos
             * */
            if (existClient(productRequest.getUserId(),1)){
                /**
                 * Si existe el usuario, filtamos el tipo de producto
                 * 1 PASIVO
                 * 2 ACTIVO
                 * */
                if(productRequest.getProductType()==1){
                    /**
                     * validamos si la cuenta a crear es:
                     * 1 C. AHORRO
                     * 2 C. CORRIENTE
                     * */
                    if(productRequest.getAccountType() == 1 ||
                            productRequest.getAccountType() == 2 ){

                        /**
                         * Funcionalidad para crear una cunta con
                         * un perfil, en este caso es VIP(C. ahorro)
                         * el monto minimo para esta cuenta es de 1000
                         * y el cliente debe de tener una tarjeta
                         * de credito
                         * */
                        if(productRequest.getAccountType()==1){
                            if(((productRequest.getAmount().compareTo(new BigDecimal(1000)))>=0) &&
                                    (clientResult.getProfileType().equals("VIP"))){

                                return createProductVip( productRequest);
                            }else return Optional.empty();
                        }else
                       return createProductCcForPerson(productRequest);

                    }if(productRequest.getAccountType() == 3){
                        /**
                         * 3 cuenta plazo fijo, un cliente personal,
                         * puede tener varias cuaentas de p. fijo
                         * */

                        return   createProductPfForPerson(productRequest);
                    }

                    /**
                     * 2 ACTIVO
                     * */
                }if(productRequest.getProductType()==2){
                    if(productRequest.getAccountType()== 4 || productRequest.getAccountType()== 6)
                        return createProductAcivePersonal( productRequest);
                }
            } else{

                /**
                 * Aqui el usuario no existe como cliente
                 * Modificacion para el proyecto 4
                 * que un usurio puede tener un monedero movil
                 * llamado yanqui,
                 * aqui lanza un evento hacia microservicion
                 * cliente
                 * */
                //eventDispatcher.publish();
                return Optional.empty();
            }
            return Optional.empty();


            /**
             * Creacion de producto para una EMPRESA
             * */
        }if(productRequest.getClientType() == 2){
            /**
             * Verificamos la exitencia del usuario tipo
             * EMPRESA
             * */
            if(existClient(productRequest.getUserId(), 2)){
                /**
                 * para PASIVO, solo esta permitido tener
                 * muchas cuentas CORRIENTES
                 * */
                if(productRequest.getProductType()==1){
                    LOGGER.info("# cliente: "+clientResult.getProfileType());
                    if((productRequest.getAccountType()==2) && (clientResult.getProfileType().equals("PYME"))){
                        LOGGER.info("#  si hay");
                       return createProductMype( productRequest);
                    }
                    else
                      return createProductPfForPerson(productRequest);
                }
                /**
                 * para ACTIVO
                 * */
                if(productRequest.getProductType() == 2){
                     if(productRequest.getAccountType()==5||productRequest.getAccountType()==6)
                        return createProductAcivePersonal( productRequest);

                }
            }
        }
        return Optional.empty();
    }

    @Cacheable(value = "productsCache",key = "#id")
    @Override
    public Optional<Product> findProductByClient(String id) {
        return productRepository.findAll()
                .stream()
                .filter(s->s.getUserId()
                        .stream()
                        .anyMatch(user->user.equals(id))).findFirst();
    }

    @Override
    public Optional<ByteArrayInputStream> generateReport(String id) {
      Optional<Product>product= findProductByClient(id);
      if(product.isPresent()){
         return Optional.of(generatePdfReport.clientReport(product.get()));
      }else{
          return Optional.empty();
      }

    }


    /**
     * 19/06/2022
     *Funcionalidad omitida, esta funionalidad
     * debe de ir en cliente
     * */
    private Optional<Product> createProductVip(ProductRequest productRequest){
        Optional<Product> productOptional=productRepository.findAll()
                .stream()
                .filter(s->s.getUserId().stream().anyMatch(id->id.equals(productRequest.getUserId())))
                .filter(account->account.getAccounts()
                        .stream()
                        .anyMatch(type->type.getAccountType().equals(AccountType.TARJETA_CREDITO)))
                .filter(account->account.getAccounts()
                        .stream()
                        .noneMatch(type->type.getAccountType().equals(AccountType.AHORRO)))
                .findFirst();
        if(productOptional.isPresent()){
            Product product=productOptional.get();
            Optional<Account> account=createAccountPf(productRequest,productRequest.getAccountType());
            account.ifPresent(s->product.getAccounts().add(s));
            return Optional.of(productRepository.save(product));
        }else return Optional.empty();
    }
    private Optional<Product>createProductMype(ProductRequest productRequest){

        Optional<Product> productOptional=productRepository.findAll()
                .stream()
                .filter(s->s.getUserId().stream().anyMatch(id->id.equals(productRequest.getUserId())))
                .filter(account->account.getAccounts()
                        .stream()
                        .anyMatch(type->type.getAccountType().equals(AccountType.TARJETA_CREDITO)))
                .findFirst();
        if(productOptional.isPresent()){
            /**
             * Update con la cuenta tipo Activo personal
             * */
            Product product=productOptional.get();
            Optional<Account> account=createAccountPf(productRequest,productRequest.getAccountType());
            account.ifPresent(s->product.getAccounts().add(s));
            return Optional.of(productRepository.save(product));

        }else{
            //
            return Optional.empty();
        }
    }
    private Optional<Product> createProductAcivePersonal(ProductRequest productRequest){
        AccountType accountType=ConvertToAccountType.convert(productRequest);
        Optional<Product> productOptional=productRepository.findAll()
                .stream()
                .filter(s->s.getUserId().stream().anyMatch(id->id.equals(productRequest.getUserId())))
                .filter(account->account.getAccounts()
                        .stream()
                        .noneMatch(type->type.getAccountType().equals(accountType)))
                .findFirst();
        if(productOptional.isPresent()){
            /**
             * Update con la cuenta tipo Activo personal
             * */
            Product product=productOptional.get();
            Optional<Account> account=createAccountPf(productRequest,productRequest.getAccountType());
            account.ifPresent(s->product.getAccounts().add(s));
            return Optional.of(productRepository.save(product));

        }else{
            Product product=new Product();
            product.setState(1);
            product.setCreatedAt(LocalDateTime.now());

            if(productRequest.getClientType()==1)
                product.setClientType(ClientType.PERSONAL);
            if(productRequest.getClientType()==2)
                product.setClientType(ClientType.EMPRESARIAL);
            createAccountPf(productRequest,productRequest.getAccountType())
                    .ifPresent(s->product.setAccounts(Arrays.asList(s)));
            product.setUserId(Arrays.asList(productRequest.getUserId()));
            return Optional.of(productRepository.save(product));
        }
    }
    private Optional<Product> createProductCcForPerson(ProductRequest productRequest){

        AccountType accountType=ConvertToAccountType.convert(productRequest);
        //PLAZO_FIJO
        //CUENTA_CORRIENTE

        Optional<Product> productOptional=productRepository.findAll()
                .stream()
                .filter(s->s.getUserId()
                        .stream()
                        .anyMatch(id->id.equals(productRequest.getUserId())))
                .filter(count->count.getAccounts()
                        .stream()
                        .noneMatch(type->type.getAccountType().equals(accountType))).findAny();
        if(productOptional.isPresent()){
            /**
             * Actualizamos producto con el nueva cuenta
             * */
            Product product=productOptional.get();
            Optional<Account> account=createAccountPf(productRequest,productRequest.getAccountType());
            account.ifPresent(s->product.getAccounts().add(s));
            return Optional.of(productRepository.save(product));
        }else {
            /**
             *
             * */

            //nuevo producto
          return Optional.empty();
        }
    }

    @Cacheable(value = "productsCache")
    private Optional<Product> createProductPfForPerson(ProductRequest productRequest){
        /**
         * Verificamos la existencia de un producto de plazo fijo,
         * si no hay lo creamos
         * *un cliente puede tener muchas cuentas d eplazo fijo
         * */
        Optional<Product> productOptional=productRepository.findAll()
                .stream()
                .filter(s->s.getUserId().stream().anyMatch(id->id.equals(productRequest.getUserId())))
                        .findFirst();

        if(productOptional.isPresent()){
            //actualizamos
            Product productFind=productOptional.get();
            createAccountPf(productRequest,productRequest.getAccountType())
                    .ifPresent(s->productFind.getAccounts().add(s));
            return Optional.of(productRepository.save(productFind));
        }else{
            //nuevo producto
            Product product=new Product();
            product.setState(1);
            product.setCreatedAt(LocalDateTime.now());
            if(productRequest.getClientType()==1)
                product.setClientType(ClientType.PERSONAL);
            if(productRequest.getClientType()==2)
                product.setClientType(ClientType.EMPRESARIAL);
            createAccountPf(productRequest,productRequest.getAccountType())
                    .ifPresent(s->product.setAccounts(Arrays.asList(s)));
            product.setUserId(Arrays.asList(productRequest.getUserId()));
            return Optional.of(productRepository.save(product));
        }


    }
    private Optional<Account>createAccountCh(){
        return Optional.empty();
    }
    private Optional<Account> createAccountPf(ProductRequest productRequest,int accountType){

        Account account=new Account();
        switch (accountType)
        {
            case 1:account.setAccountType(AccountType.AHORRO);break;
            case 2:account.setAccountType(AccountType.CUENTA_CORRIENTE);break;
            case 3:account.setAccountType(AccountType.PLAZO_FIJO);break;
            case 4:account.setAccountType(AccountType.PERSONAL);break;
            case 5:account.setAccountType(AccountType.EMPRESARIAL);break;
            case 6:account.setAccountType(AccountType.TARJETA_CREDITO);break;
            case 7:account.setAccountType(AccountType.AHORRO_VIP);break;
            case 8:account.setAccountType(AccountType.CUENTA_CORRIENTE_PYME);break;
        }
        account.setAmount(productRequest.getAmount());
        if (productRequest.getProductType()== 1)
            account.setProdcutType(ProdcutType.PASIVO);
        if (productRequest.getProductType()== 2)
            account.setProdcutType(ProdcutType.ACTIVO);
        account.setCardNumber(productRequest.getCardNumber());
        account.setMaxNumberTransactions(productRequest.getMaxNumberTransactions());
        account.setMinAmountMonth(productRequest.getMinAmountMonth());
        account.setCreditLimit(productRequest.getCreditLimit());
       return Optional.of(accountService.create(account));
    }

    private boolean existClient(String id, int typeUser){

        try{
            if(typeUser==1){
                clientResult=clientResultClient.retrievePersonResult(id);
            }
            else{
                clientResult= clientResultClient.retrieveCompanyResult(id);
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
