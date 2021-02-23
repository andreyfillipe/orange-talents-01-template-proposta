package br.com.zup.proposta.carteira.associar;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "carteiras", url = "${url.proposta.cartao}")
public interface AssociarClient {

    @PostMapping("{id}/carteiras")
    AssociarResponse associar(@PathVariable("id") String id, @RequestBody @Valid AssociarRequest request);
}
