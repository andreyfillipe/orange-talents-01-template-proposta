package br.com.zup.proposta.bloqueio.bloquear;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bloquearCartao", url = "${cartao.proposta.url}")
public interface BloquearCartaoClient {

    @PostMapping(value = "{id}/bloqueios")
    BloquearResponse bloquear(@PathVariable("id") String id, @RequestBody BloquearRequest request);
}
