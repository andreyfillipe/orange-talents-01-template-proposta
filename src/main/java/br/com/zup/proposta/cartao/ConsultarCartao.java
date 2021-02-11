package br.com.zup.proposta.cartao;

import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarCartao {

    @Autowired
    private CartaoClient cartaoClient;

    @Autowired
    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Pageable paginacao = PageRequest.of(0, 1000);

    @Scheduled(fixedRateString = "${cartao.consulta}")
    public void consultarCartao() {
        List<Proposta> propostas = propostaRepository.findByPropostasSemCartao(paginacao);
        if (!propostas.isEmpty()) {
            propostas.forEach(p -> {
                try {
                    CartaoResponse responseCartao = cartaoClient.consultar(p.getId());
                    p.atualizarNumeroCartao(responseCartao.toCartao(p));
                    propostaRepository.save(p);
                } catch (FeignException ex) {
                    logger.info("Proposta: " + p.getId() + " ainda não possui cartão disponível");
                }
            });
        }
    }
}
