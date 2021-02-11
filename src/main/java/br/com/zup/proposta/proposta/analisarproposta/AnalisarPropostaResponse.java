package br.com.zup.proposta.proposta.analisarproposta;

public class AnalisarPropostaResponse {

    private String documento;
    private String nome;
    private AnalisarPropostaStatus resultadoSolicitacao;
    private Long idProposta;

    @Deprecated
    public AnalisarPropostaResponse(){
    }

    public AnalisarPropostaResponse(String documento, String nome, AnalisarPropostaStatus resultadoSolicitacao, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public AnalisarPropostaStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
