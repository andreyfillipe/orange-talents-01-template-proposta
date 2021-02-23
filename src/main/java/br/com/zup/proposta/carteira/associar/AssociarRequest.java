package br.com.zup.proposta.carteira.associar;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociarRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String carteira;

    public AssociarRequest(@NotBlank @Email String email, @NotBlank String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
