package br.com.zup.proposta.config.validacao;

import java.util.List;

public class FormResponse {

    private List<String> mensagem;

    public FormResponse(List<String> mensagem) {
        this.mensagem = mensagem;
    }

    public List<String> getMensagem() {
        return mensagem;
    }
}
