package fr.miage.m2.bankprojectcarte;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

import org.hibernate.criterion.Example;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.miage.m2.bankprojectcarte.controller.CarteController;
import fr.miage.m2.bankprojectcarte.model.Carte;
import fr.miage.m2.bankprojectcarte.repository.CarteRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*; 
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankprojectCarteApplicationTests {
	
	private MockMvc mockMvc;
	
	@Mock
	private CarteRepository cr;
	
	@InjectMocks
	private CarteController co;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(co)
				.build();
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<Carte> cartes = new ArrayList();
		cartes.add(new Carte("1","2"));
		cartes.add(new Carte("3","4"));
		when(cr.findAll()).thenReturn(cartes);
		mockMvc.perform(get("/cartes"))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
						.andExpect(jsonPath("$.content", hasSize(2)))
						.andExpect(jsonPath("$.content.[0].idCompte", is("1")))
						.andExpect(jsonPath("$.content.[0].numCarte", is("2")))
						.andExpect(jsonPath("$.content.[1].idCompte", is("3")))
						.andExpect(jsonPath("$.content.[1].numCarte", is("4")));
		verify(cr, times(1)).findAll();
		verifyNoMoreInteractions(cr);
	}
	
	@Test
	public void testGetCarte() throws Exception {
		Carte carte = new Carte("1","2");
		carte.setId("1");
		
		when(cr.findById("1")).thenReturn(Optional.ofNullable(carte));
		mockMvc.perform(get("/cartes/{id}","1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is("1")))
				.andExpect(jsonPath("$.idCompte", is("1")))
				.andExpect(jsonPath("$.numCarte", is("2")));
		verify(cr, times(1)).findById("1");
		verifyNoMoreInteractions(cr);
	}
	
	/*@Test
	public void testPostCarte() throws Exception {
		Carte carte = new Carte("1","2");
		carte.setId("1");
		when(cr.existsById("1")).thenReturn(false);
		mockMvc.perform(
				post("/cartes")
					.secure(false)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(carte))
				)
				.andExpect(status().isCreated());
		verify(cr, times(1)).existsById("1");
		verify(cr, times(1)).save(carte);
		verifyNoMoreInteractions(cr);
	}*/

	@Test
	public void contextLoads() {
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        Logger.getLogger((BankprojectCarteApplicationTests.class.getName())).log(Level.SEVERE, jsonContent);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  

}
