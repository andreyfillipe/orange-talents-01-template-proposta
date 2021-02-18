package br.com.zup.proposta.cartao;

import br.com.zup.proposta.biometria.Biometria;
import br.com.zup.proposta.bloqueio.Bloqueio;
import br.com.zup.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
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
    private CartaoStatus status = CartaoStatus.DESBLOQUEADO;
    @OneToOne
    @MapsId
    private Proposta proposta;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Biometria> biometrias;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Bloqueio> bloqueios;

    @Deprecated
    public Cartao(){
    }

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, BigDecimal limite, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
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

    public Bloqueio toBloqueio(HttpServletRequest request) {
        String xRealIp = request.getHeader("X-REAL-IP");
        String xForwardedFor = request.getHeader("X-FORWARDED-FOR");
        String remoteAddr = request.getRemoteAddr();
        String ip = (xRealIp != null) ? xRealIp : ((xForwardedFor != null) ? xForwardedFor : remoteAddr);

        String userAgent = request.getHeader("USER-AGENT");

        return new Bloqueio(ip, userAgent, this);
    }
}
