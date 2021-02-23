package br.com.zup.proposta.cartao;

import br.com.zup.proposta.carteira.CarteiraTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartaoRepository  extends JpaRepository<Cartao, Long> {

    @Query("select case when count(ca) > 0 then true else false end from Cartao c join c.carteiras ca where c.numeroCartao = :numeroCartao and ca.tipo = :tipo")
    boolean existsByCarteira(String numeroCartao, CarteiraTipo tipo);
}
