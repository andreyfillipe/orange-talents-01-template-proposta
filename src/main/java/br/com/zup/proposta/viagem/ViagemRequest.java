package br.com.zup.proposta.viagem;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @Future
    private LocalDate dataTermino;

    public ViagemRequest(@NotBlank String destino, @NotNull @Future LocalDate dataTermino) {
        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public Viagem toViagem(HttpServletRequest request, Cartao cartao) {
        return new Viagem(this.destino, this.dataTermino, Util.getIp(request), Util.getUserAgent(request), cartao);
    }
}
