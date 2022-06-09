package com.nttdata.service.impl;

import com.nttdata.client.ClientResultClient;
import com.nttdata.client.dto.ClientResult;
import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.Customer;
import com.nttdata.model.dao.Product;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.model.response.AccountResponse;
import com.nttdata.model.response.CustomerResponse;
import com.nttdata.model.response.ProductResponse;
import com.nttdata.repository.ProductRepository;
import com.nttdata.service.AccountService;
import com.nttdata.service.CustomerService;
import com.nttdata.service.ProductService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOGGER= LoggerFactory.getLogger("ProductServiceImpl");
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final ClientResultClient clientResultClient;
    private final AccountService accountService;
    public ProductServiceImpl(ProductRepository productRepository,ClientResultClient clientResultClient,
                              CustomerService customerService,
                              AccountService accountService){
        this.productRepository=productRepository;
        this.clientResultClient=clientResultClient;
        this.customerService=customerService;
        this.accountService=accountService;
    }

    /**
     *
     * @param productRequest
     * producto a ingresar debemos validar:
     *
     * -si es personal solo puede tener un máximo de
     * una cuenta de ahorro, una cuenta corriente o
     * cuentas a plazo fijo
     *
     * -si es una empresa, no puede tener una
     * cuenta de ahorro o de plazo fijo pero sí
     * múltiples cuentas corrientes
     *
     * 1: persona
     * 2: empresa
     * */
    @Override
    public void create(ProductRequest productRequest) {
        switch (productRequest.getCustomerType()){
            case 1:
                //verificamos la existencia del la persona
                //en el microservicio cliente, si no existe lo creamos
                try{
                    ClientResult result= clientResultClient.retrievePersonResult(productRequest.getCustomerId());
                    LOGGER.info("# resultado de la busqueda: "+result.getClientId());

                    //ahora verificamos en productos, que productos cuenta,
                    // hacemos una consulta a customer findAll()
                   Optional<Customer> customer= customerService.findAll()
                            .stream()
                            .filter(s->s.getClientId().equals(result.getClientId())).findFirst();

                    //buscamos en product para ver que productos tiene
                    if(customer.isPresent()){
                        LOGGER.info("# viedo si tiene porductos");
                        Optional<Product> productOptional=productRepository.findById(customer.get().getProductId());

                        // si cuenta con un prodcuto, verificamos que no se vaya a repetir el mismo tipo
                        // de cuenta ya que el cliente persona solo puede tener 1 producto de:
                        // ahorro, cuenta corriente o plazo fijo
                        if(productOptional.isPresent()){
                            //realizamos una busqueda a la cuenta(Account), para ver el tipo de cuenta que tiene
                           List<Account> accounts= accountService.findAll()
                                    .stream()
                                    .filter(s->s.getProductId().equals(productOptional.get().getId()))
                                   .collect(Collectors.toList());
                           // validamos el tipo de cuenta a crear
                            boolean count= accounts
                                    .stream()
                                    .noneMatch(s->s.getAccountType()==productRequest.getAccountType());
                            if(count){
                                // obtenemos el id del producto
                                LOGGER.info("# creando una nueva cuenta con el mismo cliente");
                                String productId=productOptional.get().getId();
                                Account account=new Account();
                                account.setProductId(productId);
                                account.setAccountType(productRequest.getAccountType());
                                account.setCardNumber(productRequest.getCardNumber());
                                account.setAmount(productRequest.getAmount());
                                accountService.create(account);
                            }
                        }
                    }else{

                        LOGGER.info("# no existe en la base de productos");
                        //de lo contrario no tiene ningun tipo de producto, entonces lo
                        //creamos sin ninguna validacion de productos
                        Product product=new Product();
                        product.setProductType(productRequest.getProductType());
                        product.setCustomerType(productRequest.getCustomerType());
                        product.setClientId(productRequest.getCustomerId());
                        productRepository.save(product);
                        LOGGER.info("# creado correctamente");
                        Optional<Product> p=productRepository.findAll()
                                .stream()
                                .filter(s->s.getClientId().equals(productRequest.getCustomerId())).findFirst();

                        Account account=new Account();
                        account.setAmount(productRequest.getAmount());
                        account.setCardNumber(productRequest.getCardNumber());
                        account.setAccountType(productRequest.getAccountType());
                        account.setProductId(p.get().getId());
                        accountService.create(account);

                        Customer cust=new Customer();
                        cust.setClientId(productRequest.getCustomerId());
                        cust.setProductId(p.get().getId());
                        customerService.create(cust);
                    }



                }catch (Exception e){
                    // cuando no exite en el microservicio de clientes
                    LOGGER.error("# no se encontro: "+e.getMessage());
                }
                break;
                //cuentas para empresas
            case 2:
                LOGGER.error("# cuentas para empresa ");
                try{
                    //verificamos la existencia del la persona
                    //en el microservicio cliente, si no existe lo creamos
                    ClientResult resultClient= clientResultClient.retrieveCompanyResult(productRequest.getCustomerId());
                    LOGGER.info("# conctado a microservicio cliente:  "+resultClient.getClientId());


                    // un cliente empresarial puede tener multiples cuentas corrientes, pero
                    // no de ahorro ni de plazo fijo
                    if(productRequest.getAccountType()!= 1 &&
                            productRequest.getAccountType()!=3 &&
                            productRequest.getAccountType()!=4){
                        // verificamos que tipo de productos tiene ese cliente,
                        //para eso obtenemos del documento customer para obtener el id
                        // del producto
                       Optional<Customer> customer= customerService.findAll()
                                .stream()
                                .filter(s->s.getClientId().equals(productRequest.getCustomerId()))
                                .findFirst();

                        if(customer.isPresent()){
                            LOGGER.info("# se encontro un registro :  "+customer.get().getClientId());
                            // ahora buscamos el producto para obtener la cuenta
                           Optional<Product> product= productRepository.findById(customer.get().getProductId());
                           // validamos para tener muchas cuentas corrientes 2
                            if(product.isPresent()){
                                switch (productRequest.getAccountType()){
                                    case 2:
                                        LOGGER.error("# tipo de cuenta 2");
                                        Account account=new Account();
                                        account.setProductId(product.get().getId());
                                        account.setAccountType(productRequest.getAccountType());
                                        account.setAmount(productRequest.getAmount());
                                        account.setCardNumber(productRequest.getCardNumber());
                                        accountService.create(account);
                                        break;
                                    case 5:
                                    case 6:
                                        LOGGER.error("# tipo de cuenta 5 y 6");
                                        // verificamos que no tenga la cuenta empresarial ya que solo se pide solo una
                                        // por ese tipo de cuenta
                                        boolean data=accountService
                                                .findAll()
                                                .stream()
                                                .noneMatch(s->s.getAccountType()==5||s.getAccountType()==6);
                                                if(data){
                                                    Account accounts=new Account();
                                                    accounts.setProductId(product.get().getId());
                                                    accounts.setAccountType(productRequest.getAccountType());
                                                    accounts.setAmount(productRequest.getAmount());
                                                    accounts.setCardNumber(productRequest.getCardNumber());
                                                    accountService.create(accounts);
                                                }
                                        break;
                                }
                            }

                        }
                        else{
                            // no tiene ningun producto la
                            LOGGER.info("# el cliente empresa, no esta en los registros de producto");
                            //de lo contrario no tiene ningun tipo de producto, entonces lo
                            //creamos sin ninguna validacion de productos
                            Product product=new Product();
                            product.setProductType(productRequest.getProductType());
                            product.setCustomerType(productRequest.getCustomerType());
                            product.setClientId(productRequest.getCustomerId());
                            productRepository.save(product);
                            LOGGER.info("# creado correctamente");
                            Optional<Product> p=productRepository.findAll()
                                    .stream()
                                    .filter(s->s.getClientId().equals(productRequest.getCustomerId())).findFirst();

                            Account account=new Account();
                            account.setAmount(productRequest.getAmount());
                            account.setCardNumber(productRequest.getCardNumber());
                            account.setAccountType(productRequest.getAccountType());
                            account.setProductId(p.get().getId());
                            accountService.create(account);

                            Customer cust=new Customer();
                            cust.setClientId(productRequest.getCustomerId());
                            cust.setProductId(p.get().getId());
                            customerService.create(cust);

                        }
                    }


                }catch(Exception e){
                    LOGGER.error("# no se encontro: "+e.getMessage());
                }

                break;
        }

    }

    /**
     * @param id
     * consultamos los productos de un cliente
     * determinado por su id
     * */
    @Override
    public List<ProductResponse> getProdcutFindById(String id) {
        Optional<Product> product=productRepository
                .findAll()
                .stream().filter(s->s.getClientId().equals(id)).findFirst();
        ProductResponse productResponse=new ProductResponse();


        if(product.isPresent()){
            LOGGER.info("# se encontro dato");
           String productType=product.get().getProductType()==1? "Pasivo": "Activo";
           String customerType= product.get().getProductType()==1? "Persona": "Empresa";
            productResponse.setProductType(productType);
            productResponse.setCustomerType(customerType);

            accountService.findAll()
                    .stream()
                    .filter(s->s.getProductId().equals(product.get().getId()))
                    .forEach(data->{
                        String accountType=null;
                        switch (data.getAccountType()){
                            case 1: accountType="Ahorro";break;
                            case 2: accountType="Cuenta corriente"; break;
                            case 3: accountType="Plazo fijo";break;
                            case 4: accountType="Personal";break;
                            case 5: accountType="Empresarial"; break;
                            case 6: accountType="Tarjeta de Credito";break;
                        }
                        productResponse
                                .getAccountResponses()
                                .add(new AccountResponse(accountType,data.getCardNumber(),data.getAmount()));
                    });
            customerService.findAll().stream()
                    .filter(s->s.getProductId().equals(product.get().getId()))
                    .forEach(data->{
                        productResponse
                                .getCustomerResponses()
                                .add(new CustomerResponse(data.getClientId()));
                    });
        }

       return new ArrayList<>(Arrays.asList(productResponse));
    }


}
