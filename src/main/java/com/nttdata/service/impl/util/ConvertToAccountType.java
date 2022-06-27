package com.nttdata.service.impl.util;

import com.nttdata.model.dao.util.AccountType;
import com.nttdata.model.request.ProductRequest;

public class ConvertToAccountType {


    /**p
     * 1: Ahorro
     * 2: Cuenta corriente
     * 3: Plazo fijo
     *
     * TC
     * 4: Personal
     * 5: Empresarial
     * 6: Tarjeta de Crédito personal o empresarial
     * */
    public static AccountType convert(ProductRequest productRequest){


        AccountType accountType;
           switch (productRequest.getAccountType()){
               case 1:
                    accountType=AccountType.AHORRO; break;
               case 2:
                   accountType= AccountType.CUENTA_CORRIENTE;break;
               case 3:
                   accountType= AccountType.PLAZO_FIJO;break;
               case 4:
                   accountType= AccountType.PERSONAL;break;
               case 5:
                   accountType= AccountType.EMPRESARIAL;break;
               case 6:
                   accountType= AccountType.TARJETA_CREDITO;break;
               case 7:
                   accountType= AccountType.AHORRO_VIP;break;
               case 8:
                   accountType= AccountType.CUENTA_CORRIENTE_PYME;break;
               default: accountType=AccountType.NONE;
           }
        return accountType;
    }
}
