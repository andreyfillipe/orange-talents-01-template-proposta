package br.com.zup.proposta.viagem;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String destino;
    @NotNull
    @Future
    @Column(nullable = false)
    private LocalDate dataTermino;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataAvisoViagem;
    @NotBlank
    @Column(nullable = false)
    private String ip;
    @NotBlank
    @Column(nullable = false)
    private String userAgent;
    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Deprecated
    public Viagem(){
    }

    public Viagem(@NotBlank String destino, @NotNull @Future LocalDate dataTermino, @NotBlank String ip, @NotBlank String userAgent, Cartao cartao) {
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.dataTermino = LocalDate.now();
        this.dataAvisoViagem = LocalDateTime.now();
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }
}
