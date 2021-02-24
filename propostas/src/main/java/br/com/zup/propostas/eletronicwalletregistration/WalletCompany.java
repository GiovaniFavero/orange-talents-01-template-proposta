package br.com.zup.propostas.eletronicwalletregistration;

public enum WalletCompany {

    PAYPAL, SAMSUNG_PAY;

    public static boolean contains(String value) {
        for (WalletCompany w : WalletCompany.values()) {
            if (w.name().equals(value)){
                return true;
            }
        }
        return false;
    }

}
