package br.com.zup.proposta.proposta;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.endereco.Endereco;
import br.com.zup.proposta.proposta.analisarproposta.AnalisarPropostaRequest;
import br.com.zup.proposta.util.Criptografia;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String documento;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotNull
    @Embedded
    private Endereco endereco;
    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal salario;
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropostaStatus status;
    @OneToOne(mappedBy = "proposta", cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta(){
    }

    public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank Endereco endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = Criptografia.encode(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.dataCriacao = LocalDateTime.now();
        this.status = PropostaStatus.NAO_ELEGIVEL;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void atualizarNumeroCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public void atualizarStatus(PropostaStatus status) {
        this.status = status;
    }

    public AnalisarPropostaRequest toDadosSolicitante() {
        return new AnalisarPropostaRequest(Criptografia.decode(this.documento), this.nome, this.id);
    }

    public PropostaResponse toPropostaResponse() {
        return new PropostaResponse(Criptografia.decode(this.documento), this.email, this.nome, this.endereco.toEnderecoResponse(), this.salario, this.status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(documento, proposta.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }
}
