package com.orders;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.model.Cliente;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private Cliente cliente = new Cliente(null, "Teste", "99999999999", new Date(), null);

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @WithMockUser(username = "test", password = "pass")
    @Test
    public void getAllClientes() throws Exception {

        mvc.perform(get("/clientes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void getAllClienteSemAuth() throws Exception {
        mvc.perform(get("/clientes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "test", password = "pass")
    @Test
    public void create() throws Exception {

        mvc.perform( MockMvcRequestBuilders
            .post("/clientes")
            .content(asJsonString(this.cliente))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @WithMockUser(username = "test", password = "pass")
    @Test
    public void getCLienteById() throws Exception {
        mvc.perform(get("/clientes/{id}", 14)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("14"));
    }

    @WithMockUser(username = "test", password = "pass")
    @Test
    public void updateCliente() throws Exception {
        Cliente clienteUpdate = new Cliente();
        clienteUpdate.setNome("Test Update");
        clienteUpdate.setCpf("99999999998");
        clienteUpdate.setDataNascimento(new Date());

        mvc.perform( MockMvcRequestBuilders
            .put("/clientes/{id}", 44)
            .content(asJsonString(clienteUpdate))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @WithMockUser(username = "test", password = "pass")
    @Test
    public void deleteCliente() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .delete("/clientes/{id}", 44))
            .andExpect(status().isOk());
    }

}