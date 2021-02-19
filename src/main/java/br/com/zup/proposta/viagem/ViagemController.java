package br.com.zup.proposta.viagem;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.config.validacao.ApiErroException;
import br.com.zup.proposta.viagem.viajar.ViajarClient;
import br.com.zup.proposta.viagem.viajar.ViajarRequest;
import br.com.zup.proposta.viagem.viajar.ViajarResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes/{id}/viagens")
public class ViagemController {

    private CartaoRepository cartaoRepository;
    private ViajarClient viajarClient;

    public ViagemController(CartaoRepository cartaoRepository, ViajarClient viajarClient) {
        this.cartaoRepository = cartaoRepository;
        this.viajarClient = viajarClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> salvar(@PathVariable Long id,
                                       @RequestBody @Valid ViagemRequest request,
                                       HttpServletRequest httpServletRequest) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ApiErroException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        Viagem viagem = request.toViagem(httpServletRequest, cartao);

        try {
            ViajarResponse responseViajar = viajarClient.avisar(cartao.getNumeroCartao(), new ViajarRequest(viagem.getDestino(), viagem.getDataTermino()));
            cartao.vincularViagem(viagem);
            cartaoRepository.save(cartao);
        } catch (FeignException.UnprocessableEntity ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
