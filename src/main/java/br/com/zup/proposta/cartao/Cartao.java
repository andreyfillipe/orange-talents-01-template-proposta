package br.com.zup.proposta.cartao;

import br.com.zup.proposta.biometria.Biometria;
import br.com.zup.proposta.bloqueio.Bloqueio;
import br.com.zup.proposta.carteira.Carteira;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.viagem.Viagem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    private BigDecimal limite;
    @Enumerated(EnumType.STRING)
    private CartaoStatus status;
    @OneToOne
    @MapsId
    private Proposta proposta;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Biometria> biometrias;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Bloqueio> bloqueios;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Viagem> viagens;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Carteira> carteiras;

    @Deprecated
    public Cartao(){
    }

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, BigDecimal limite, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
        this.status = CartaoStatus.DESBLOQUEADO;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void atualizarStatus(CartaoStatus status) {
        this.status = status;
    }

    public void vincularBiometria(Biometria biometria) {
        this.biometrias.add(biometria);
    }

    public void vincularBloqueio(Bloqueio bloqueio) {
        this.bloqueios.add(bloqueio);
    }

    public void vincularViagem(Viagem viagem) {
        this.viagens.add(viagem);
    }

    public void vincularCarteira(Carteira carteira) {
        this.carteiras.add(carteira);
    }
}
