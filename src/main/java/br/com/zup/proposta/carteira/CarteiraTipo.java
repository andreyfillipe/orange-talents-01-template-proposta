package br.com.zup.proposta.carteira;

public enum CarteiraTipo {

    PAYPAL,
    SAMSUNGPAY;

    public String converter() {
        if (this.equals(PAYPAL)) {
            return "PayPal";
        }
        return "Samsung Pay";
    }
}
