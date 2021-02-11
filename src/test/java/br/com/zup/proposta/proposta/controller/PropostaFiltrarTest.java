package br.com.zup.proposta.proposta.controller;

import br.com.zup.proposta.endereco.Endereco;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PropostaFiltrarTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PropostaRepository propostaRepository;

    @Test
    @DisplayName("Filtrar proposta com sucesso")
    public void filtrar() throws Exception {
        Endereco endereco = new Endereco("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        Proposta proposta = new Proposta("123.456.789-09", "email@email.com", "Nome", endereco, new BigDecimal(1500.00));
        propostaRepository.save(proposta);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/propostas/" + proposta.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Filtrar proposta que não existe")
    public void filtrarNaoExiste() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/propostas/" + 1)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
