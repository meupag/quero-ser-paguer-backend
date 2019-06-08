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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pag.modelo.Produto;
import com.pag.rest.utils.RestUtil;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = QueroSerPaguerBackendApplication.class)
public class TestProdutoController {
 
    @Autowired
    private WebApplicationContext wac;
    
    
 
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
 
    private MockMvc mockMvc;
    String accessToken;
    Produto produto;
    String idProduto;
    
    @Before
    public void setup() throws OAuthSystemException, OAuthProblemException, ParseException {

    	accessToken = TestUtils.obtainAccessToken();
    	produto = TestUtils.getProduto();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
    }
    
    @Test
    public void novoProduto() throws Exception {
        
        ResultActions retorno = mockMvc.perform(post("/v1/produtos/")
          .header("Authorization", "Bearer " + accessToken).contentType("application/json")
          .content(RestUtil.toJson(produto)));
        idProduto = TestUtils.getId(retorno);
        retorno.andExpect(status().isCreated());
        getprodutoById();
        alterarproduto();
        apagarproduto();
      }
    

    private void getprodutoById() throws Exception {
         mockMvc.perform(get("/v1/produtos/" + idProduto)
          .header("Authorization", "Bearer " + accessToken))
          .andExpect(status().isOk());
      }
    

    private  void alterarproduto() throws Exception {
        String accessToken = TestUtils.obtainAccessToken();
        produto.setId(idProduto);
         mockMvc.perform(put("/v1/produtos/")
          .header("Authorization", "Bearer " + accessToken).contentType("application/json")
          .content(RestUtil.toJson(produto)))
          .andExpect(status().isOk());
      }
    

    private  void apagarproduto() throws Exception {
    	
         mockMvc.perform(delete("/v1/produtos/" + idProduto)
          .header("Authorization", "Bearer " + accessToken))
          .andExpect(status().isOk());


      }
    


}