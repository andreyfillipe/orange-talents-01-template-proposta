package br.com.zup.proposta.proposta;

import br.com.zup.proposta.config.validacao.ApiErroException;
import br.com.zup.proposta.proposta.analisarproposta.AnalisarPropostaClient;
import br.com.zup.proposta.proposta.analisarproposta.AnalisarPropostaResponse;
import br.com.zup.proposta.proposta.analisarproposta.AnalisarPropostaStatus;
import br.com.zup.proposta.cartao.CartaoClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnalisarPropostaClient analisarPropostaClient;

    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, List<String>>> salvar(@RequestBody @Valid PropostaRequest request,
                                                            UriComponentsBuilder builder) {
        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            return ResponseEntity.unprocessableEntity().body(Map.of("mensagem", Arrays.asList("Já existe proposta com o documento informado")));
        }

        Proposta proposta = request.toProposta();
        propostaRepository.save(proposta);

        try {
            AnalisarPropostaResponse responseAnalise = analisarPropostaClient.analisar(proposta.toDadosSolicitante());
            proposta.atualizarStatus(responseAnalise.getResultadoSolicitacao().converte());
        } catch (FeignException.UnprocessableEntity ex) {
            proposta.atualizarStatus(AnalisarPropostaStatus.COM_RESTRICAO.converte());
        }

        URI uri = builder.path("propostas/{id}").build(proposta.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PropostaResponse> filtrar(@PathVariable Long id) {
        Proposta proposta = propostaRepository.findById(id).orElseThrow(() -> new ApiErroException(HttpStatus.NOT_FOUND, "Proposta não encontrada"));
        PropostaResponse response = proposta.toPropostaResponse();

        return ResponseEntity.ok(response);
    }
}
