package br.com.zup.proposta.cartao.consulta;

import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.PropostaStatus;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class ConsultarCartao {

    private CartaoClient cartaoClient;
    private PropostaRepository propostaRepository;
    private TransactionTemplate transactionManager;

    public ConsultarCartao(CartaoClient cartaoClient, PropostaRepository propostaRepository, TransactionTemplate transactionManager) {
        this.cartaoClient = cartaoClient;
        this.propostaRepository = propostaRepository;
        this.transactionManager = transactionManager;
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("ConstantConditions")
    @Scheduled(fixedDelayString = "${cartao.consulta}")
    public void consultarCartao() {
        boolean pendente = true;
        while (pendente) {
            pendente = transactionManager.execute(transactionStatus -> {
                List<Proposta> propostas = propostaRepository.findTop5ByStatusOrderByDataCriacaoAsc(PropostaStatus.ELEGIVEL);
                if (propostas.isEmpty()) {
                    return false;
                }

                propostas.forEach(proposta -> {
                    CartaoResponse responseCartao = getCartaoIdResponse(proposta); // timeout=100
                    if(responseCartao.getId() != null) {
                        proposta.atualizarNumeroCartao(responseCartao.toCartao(proposta));
                        proposta.atualizarStatus(PropostaStatus.ELEGIVEL_COM_CARTAO);
                        propostaRepository.save(proposta);
                    }
                });
                return true;
            });
        }
    }

    private CartaoResponse getCartaoIdResponse(Proposta proposta) {
        try {
            return cartaoClient.consultar(proposta.getId());
        } catch (FeignException.InternalServerError e) {
            logger.info("Proposta: " + proposta.getId() + " ainda não possui cartão disponível");
            return null;
        }
    }
}
