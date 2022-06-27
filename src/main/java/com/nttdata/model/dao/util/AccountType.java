package com.nttdata.model.dao.util;



public enum AccountType {
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

    /**
     * Funcionalidad agregada:
     * Personal -> VIP
     * Empresarial -> PYME
     *
     * */
    AHORRO, CUENTA_CORRIENTE,PLAZO_FIJO, PERSONAL,EMPRESARIAL,TARJETA_CREDITO,AHORRO_VIP,CUENTA_CORRIENTE_PYME,NONE
}
