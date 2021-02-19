package br.com.zup.proposta.proposta.analisarproposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "analisarProposta", url = "${url.proposta.analise}")
public interface AnalisarPropostaClient {

    @PostMapping
    AnalisarPropostaResponse analisar(@RequestBody @Valid AnalisarPropostaRequest request);
}
