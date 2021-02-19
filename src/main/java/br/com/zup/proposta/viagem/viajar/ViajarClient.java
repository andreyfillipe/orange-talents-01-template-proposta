package br.com.zup.proposta.viagem.viajar;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "avisoViagem", url = "${url.proposta.cartao}")
public interface ViajarClient {

    @PostMapping(value = "{id}/avisos")
    ViajarResponse avisar(@PathVariable("id") String id, @RequestBody @Valid ViajarRequest request);
}
