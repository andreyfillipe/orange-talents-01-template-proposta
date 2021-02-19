package br.com.zup.proposta.bloqueio;

import br.com.zup.proposta.bloqueio.bloquear.BloquearClient;
import br.com.zup.proposta.bloqueio.bloquear.BloquearRequest;
import br.com.zup.proposta.bloqueio.bloquear.BloquearResponse;
import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.cartao.CartaoStatus;
import br.com.zup.proposta.config.validacao.ApiErroException;
import br.com.zup.proposta.util.Util;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cartoes/{id}/bloqueios")
public class BloqueioController {

    private CartaoRepository cartaoRepository;
    private BloquearClient bloquearClient;

    public BloqueioController(CartaoRepository cartaoRepository, BloquearClient bloquearClient) {
        this.cartaoRepository = cartaoRepository;
        this.bloquearClient = bloquearClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> salvar(@PathVariable Long id,
                                       HttpServletRequest httpServletRequest) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ApiErroException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        Bloqueio bloqueio = new Bloqueio(Util.getIp(httpServletRequest), Util.getUserAgent(httpServletRequest), cartao);
        cartao.vincularBloqueio(bloqueio);
        cartaoRepository.save(cartao);

        try {
            BloquearResponse responseBloquear = bloquearClient.bloquear(cartao.getNumeroCartao(), new BloquearRequest("Sistema de proposta"));
            cartao.atualizarStatus(CartaoStatus.BLOQUEADO);
        } catch (FeignException.UnprocessableEntity ex) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok().build();
    }
}
