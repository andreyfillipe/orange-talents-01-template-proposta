package br.com.zup.proposta.proposta.analisarproposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analisarProposta", url = "${analise.proposta.url}")
public interface AnalisarPropostaClient {

    @PostMapping
    AnalisarPropostaResponse analisar(@RequestBody AnalisarPropostaRequest request);
}
