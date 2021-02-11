package br.com.zup.proposta.proposta.controller;

import br.com.zup.proposta.endereco.Endereco;
import br.com.zup.proposta.endereco.EnderecoRequest;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.PropostaRequest;
import br.com.zup.proposta.proposta.PropostaStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PropostaSalvarTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PropostaRepository propostaRepository;

    @Test
    @DisplayName("Salvar uma proposta com sucesso Elegível")
    public void salvarElegivel() throws Exception {
        PropostaRequest proposta = novaPropostaRequest("");
        String json = mapper.writeValueAsString(proposta);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.size()).isOne();
        assertThat(propostaSalvar.get(0).getDocumento()).isEqualTo(proposta.getDocumento());
        assertThat(propostaSalvar.get(0).getStatus()).isEqualTo(PropostaStatus.ELEGIVEL);
    }

    @Test
    @DisplayName("Salvar uma proposta com sucesso Não elegível")
    public void salvarNaoElegivel() throws Exception {
        PropostaRequest proposta = novaPropostaRequest("342.042.440-09");
        String json = mapper.writeValueAsString(proposta);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.size()).isOne();
        assertThat(propostaSalvar.get(0).getDocumento()).isEqualTo(proposta.getDocumento());
        assertThat(propostaSalvar.get(0).getStatus()).isEqualTo(PropostaStatus.NAO_ELEGIVEL);
    }

    @Test
    @DisplayName("Validar uma proposta com documento duplicado")
    public void validardocumentoDuplicado() throws Exception {
        Proposta novaProposta = novaProposta();
        propostaRepository.save(novaProposta);

        PropostaRequest proposta = novaPropostaRequest("");
        String json = mapper.writeValueAsString(proposta);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.size()).isOne();
        assertThat(propostaSalvar.get(0).getDocumento()).isEqualTo(novaProposta.getDocumento());
    }

    @Test
    @DisplayName("Validar dados inválidos")
    public void naoSalvarPropostaComDadosInvalidos() throws Exception {
        PropostaRequest proposta = novaPropostaRequestInvalida();
        String json = mapper.writeValueAsString(proposta);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.isEmpty()).isTrue();
    }

    private PropostaRequest novaPropostaRequest(String documento) {
        String novoDocumento = documento == "" ? "123.456.789-09" : documento;
        EnderecoRequest endereco = new EnderecoRequest("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        return new PropostaRequest(novoDocumento, "email@email.com", "Nome", endereco, new BigDecimal(1500.00));
    }

    private PropostaRequest novaPropostaRequestInvalida() {
        EnderecoRequest endereco = new EnderecoRequest("", "", "", "", "", "", "");
        return new PropostaRequest("", "", "", endereco, null);
    }

    private Proposta novaProposta() {
        Endereco endereco = new Endereco("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        return new Proposta("123.456.789-09", "email@email.com", "Nome", endereco, new BigDecimal(1500.00));
    }
}
