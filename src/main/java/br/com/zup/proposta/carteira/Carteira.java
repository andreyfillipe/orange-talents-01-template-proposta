package br.com.zup.proposta.carteira;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraTipo tipo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraStatus status;
    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(@NotBlank @Email String email, @NotNull CarteiraTipo tipo, Cartao cartao) {
        this.email = email;
        this.tipo = tipo;
        this.cartao = cartao;
        this.status = CarteiraStatus.NAO_ASSOCIADA;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraTipo getTipo() {
        return tipo;
    }

    public void atualizarStatus(CarteiraStatus status) {
        this.status = status;
    }
}
