package br.com.zup.proposta.cartao.consulta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartoes", url = "${url.proposta.cartao}")
public interface CartaoClient {

    @GetMapping
    CartaoResponse consultar(@RequestParam Long idProposta);
}
