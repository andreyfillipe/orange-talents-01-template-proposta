package br.com.zup.proposta.proposta;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    @Query("select p from Proposta p LEFT JOIN p.cartao c where c is null")
    List<Proposta> findByPropostasSemCartao(Pageable paginacao);
}
