package br.com.zup.proposta.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.carteira.associar.AssociarRequest;
import br.com.zup.proposta.carteira.associar.AssociarResponse;
import br.com.zup.proposta.carteira.associar.AssociarClient;
import br.com.zup.proposta.config.validacao.ApiErroException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cartoes")
public class CarteiraController {

    private CartaoRepository cartaoRepository;
    private AssociarClient associarClient;

    public CarteiraController(CartaoRepository cartaoRepository, AssociarClient associarClient) {
        this.cartaoRepository = cartaoRepository;
        this.associarClient = associarClient;
    }

    @PostMapping("{id}/carteiras")
    @Transactional
    public ResponseEntity<Void> salvar(@PathVariable Long id,
                                       @RequestBody @Valid CarteiraRequest request,
                                       UriComponentsBuilder builder) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ApiErroException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        Carteira carteira = request.toCarteira(cartao);

        if (cartaoRepository.existsByCarteira(cartao.getNumeroCartao(), carteira.getTipo())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            AssociarResponse responseCarteira = associarClient.associar(cartao.getNumeroCartao(), new AssociarRequest(carteira.getEmail(), carteira.getTipo().converter()));
            carteira.atualizarStatus(CarteiraStatus.ASSOCIADA);
            cartao.vincularCarteira(carteira);
            cartaoRepository.save(cartao);
        } catch (FeignException.UnprocessableEntity ex) {
            return ResponseEntity.unprocessableEntity().build();
        }

        URI uri = builder.path("/cartoes/{id}/carteiras/{id}").build(cartao.getId(), carteira.getId());
        return ResponseEntity.created(uri).build();
    }
}
