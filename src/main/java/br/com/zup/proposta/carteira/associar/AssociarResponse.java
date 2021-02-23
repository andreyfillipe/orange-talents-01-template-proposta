package br.com.zup.proposta.carteira.associar;

import br.com.zup.proposta.carteira.CarteiraStatus;

public class AssociarResponse {

    private CarteiraStatus resultado;
    private String id;

    public AssociarResponse(CarteiraStatus resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public CarteiraStatus getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
