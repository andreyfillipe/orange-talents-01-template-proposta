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
        EnderecoRequest enderecoRequest = new EnderecoRequest("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        PropostaRequest propostaRequest = new PropostaRequest("123.456.789-09", "email@email.com", "Nome", enderecoRequest, new BigDecimal(1500.00));

        String json = mapper.writeValueAsString(propostaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.size()).isOne();
        assertThat(propostaSalvar.get(0).getDocumento()).isEqualTo(propostaRequest.getDocumento());
        assertThat(propostaSalvar.get(0).getStatus()).isEqualTo(PropostaStatus.ELEGIVEL);
    }

    @Test
    @DisplayName("Salvar uma proposta com sucesso Não elegível")
    public void salvarNaoElegivel() throws Exception {
        EnderecoRequest enderecoRequest = new EnderecoRequest("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        PropostaRequest propostaRequest = new PropostaRequest("342.042.440-09", "email@email.com", "Nome", enderecoRequest, new BigDecimal(1500.00));

        String json = mapper.writeValueAsString(propostaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.size()).isOne();
        assertThat(propostaSalvar.get(0).getDocumento()).isEqualTo(propostaRequest.getDocumento());
        assertThat(propostaSalvar.get(0).getStatus()).isEqualTo(PropostaStatus.NAO_ELEGIVEL);
    }

    @Test
    @DisplayName("Validar uma proposta com documento duplicado")
    public void validarDocumentoDuplicado() throws Exception {
        Endereco endereco = new Endereco("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        Proposta proposta = new Proposta("123.456.789-09", "email@email.com", "Nome", endereco, new BigDecimal(1500.00));

        propostaRepository.save(proposta);

        EnderecoRequest enderecoRequest = new EnderecoRequest("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        PropostaRequest propostaRequest = new PropostaRequest("123.456.789-09", "email@email.com", "Nome", enderecoRequest, new BigDecimal(1500.00));

        String json = mapper.writeValueAsString(propostaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.size()).isOne();
        assertThat(propostaSalvar.get(0).getDocumento()).isEqualTo(proposta.getDocumento());
    }

    @Test
    @DisplayName("Validar dados inválidos")
    public void naoSalvarPropostaComDadosInvalidos() throws Exception {
        EnderecoRequest enderecoRequest = new EnderecoRequest("", "", "", "", "", "", "");
        PropostaRequest propostaRequest = new PropostaRequest("", "", "", enderecoRequest, null);
        String json = mapper.writeValueAsString(propostaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/propostas")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
        List<Proposta> propostaSalvar = propostaRepository.findAll();
        assertThat(propostaSalvar.isEmpty()).isTrue();
    }
}
