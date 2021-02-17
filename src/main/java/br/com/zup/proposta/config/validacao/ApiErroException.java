package br.com.zup.proposta.config.validacao;

import org.springframework.http.HttpStatus;

public class ApiErroException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String mensagem;

    public ApiErroException(HttpStatus httpStatus, String mensagem) {
        super(mensagem);
        this.httpStatus = httpStatus;
        this.mensagem = mensagem;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMensagem() {
        return mensagem;
    }
}
