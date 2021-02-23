package br.com.zup.proposta.carteira;

import br.com.zup.proposta.cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;
    @NotNull
    private CarteiraTipo tipo;

    public CarteiraRequest(@NotBlank @Email String email, @NotNull CarteiraTipo tipo) {
        this.email = email;
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraTipo getTipo() {
        return tipo;
    }

    public Carteira toCarteira(Cartao cartao) {
        return new Carteira(this.email, this.tipo, cartao);
    }
}
