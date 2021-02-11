package br.com.zup.proposta.cartao;

import br.com.zup.proposta.proposta.Proposta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoResponse {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private BigDecimal limite;
    private Long idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, Long idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public Cartao toCartao(Proposta proposta) {
        return new Cartao(this.id, this.emitidoEm, this.titular, this.limite, proposta);
    }
}
