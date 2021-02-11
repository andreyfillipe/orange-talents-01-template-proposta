package br.com.zup.proposta.proposta.analisarproposta;

import br.com.zup.proposta.proposta.PropostaStatus;

public enum AnalisarPropostaStatus {

    COM_RESTRICAO,
    SEM_RESTRICAO;

    public PropostaStatus converte() {
        if (this.equals(COM_RESTRICAO)) {
            return PropostaStatus.NAO_ELEGIVEL;
        }
        return PropostaStatus.ELEGIVEL;
    }
}
