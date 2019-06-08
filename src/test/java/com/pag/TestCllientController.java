package com.pag;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pag.modelo.Cliente;
import com.pag.rest.utils.RestUtil;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = QueroSerPaguerBackendApplication.class)
public class TestCllientController {
 
    @Autowired
    private WebApplicationContext wac;
    
    
 
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
 
    private MockMvc mockMvc;
    String accessToken;
    Cliente cliente;
    String jsonCliente;
    
    @Before
    public void setup() throws OAuthSystemException, OAuthProblemException, ParseException {

    	accessToken = TestUtils.obtainAccessToken();
    	cliente = TestUtils.getCliente();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
        
    }
    
    @Test
    public void novoCliente() throws Exception {
        
        mockMvc.perform(post("/v1/clientes/")
          .header("Authorization", "Bearer " + accessToken).contentType("application/json")
          .content(RestUtil.toJson(cliente)))
          .andExpect(status().isCreated());
      }
    
    @Test
    public void getClienteByCPF() throws Exception {
    	
         mockMvc.perform(get("/v1/clientes/" + cliente.getCpf())
          .header("Authorization", "Bearer " + accessToken))
          .andExpect(status().isOk());
      }
    
    @Test
    public void alterarCliente() throws Exception {
        String accessToken = TestUtils.obtainAccessToken();
         mockMvc.perform(put("/v1/clientes/")
          .header("Authorization", "Bearer " + accessToken).contentType("application/json")
          .content(RestUtil.toJson(cliente)))
          .andExpect(status().isOk());
      }
    
    @Test
    public void apagarCliente() throws Exception {
         mockMvc.perform(delete("/v1/clientes/" + cliente.getCpf())
          .header("Authorization", "Bearer " + accessToken))
          .andExpect(status().isOk());
      }
    
    

}