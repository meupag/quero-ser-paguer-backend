package com.javatechie.spring.mysql.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javatechie.spring.mysql.api.SpringMysqlApplicationTests;
import com.javatechie.spring.mysql.api.controller.ClienteController;

public class ClienteControllerTest extends SpringMysqlApplicationTests{
	
	private MockMvc mockMvc;
	
	@Autowired(required = true)
	private ClienteController clienteController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
	}
	
	@Test
	public void testGETIndexSalarioMinimoController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}
