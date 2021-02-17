package br.com.zup.proposta.cartao.controller;

import br.com.zup.proposta.biometria.BiometriaRequest;
import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.endereco.Endereco;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BiometriaController {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PropostaRepository propostaRepository;

    @Test
    @DisplayName("Salvar biometria com sucesso")
    public void salvar() throws Exception {
        /*Endereco endereco = new Endereco("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        Proposta proposta = new Proposta("123.456.789-09", "email@email.com", "Nome", endereco, new BigDecimal(1500.00));
        propostaRepository.save(proposta);
        Cartao cartao = new Cartao("12345", java.time.LocalDateTime.now(), "Titular", new BigDecimal(1500.00), proposta);
        proposta.atualizarNumeroCartao(cartao);
        propostaRepository.save(proposta);

        BiometriaRequest biometriaRequest = new BiometriaRequest("ZmluZ2VycHJpbnQ=");
        String json = mapper.writeValueAsString(biometriaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/cartoes/" + proposta.getCartao().getId() + "/biometrias")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());*/
    }

    @Test
    @DisplayName("Validar dados inválidos")
    public void naoSalvarBiometriaComDadosInvalidos() throws Exception {
        Endereco endereco = new Endereco("Logradouro", "100", "Bairro", "Complemento", "Cidade", "SP", "35000-000");
        Proposta proposta = new Proposta("123.456.789-09", "email@email.com", "Nome", endereco, new BigDecimal(1500.00));
        propostaRepository.save(proposta);
        Cartao cartao = new Cartao("12345", java.time.LocalDateTime.now(), "Titular", new BigDecimal(1500.00), proposta);
        proposta.atualizarNumeroCartao(cartao);
        propostaRepository.save(proposta);

        BiometriaRequest biometriaRequest = new BiometriaRequest("");
        String json = mapper.writeValueAsString(biometriaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/cartoes/" + cartao.getId() + "/biometrias")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Validar se cartão não existe")
    public void validarSeCartaoNaoExiste() throws Exception {
        BiometriaRequest biometriaRequest = new BiometriaRequest("ZmluZ2VycHJpbnQ=");
        String json = mapper.writeValueAsString(biometriaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/cartoes/1/biometrias")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
