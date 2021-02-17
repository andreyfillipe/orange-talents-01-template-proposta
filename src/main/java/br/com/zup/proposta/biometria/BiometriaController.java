package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.config.validacao.ApiErroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cartoes/{id}/biometrias")
public class BiometriaController {

    private CartaoRepository cartaoRepository;

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> salvar(@PathVariable Long id,
                                       @RequestBody @Valid BiometriaRequest request,
                                       UriComponentsBuilder builder) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ApiErroException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        Biometria biometria = request.toBiometria(cartao);
        cartao.vincularBiometria(biometria);
        cartaoRepository.save(cartao);

        URI uri = builder.path("/cartoes/{id}/biometrias/{id}").build(cartao.getId(), biometria.getId());
        return ResponseEntity.created(uri).build();
    }
}
