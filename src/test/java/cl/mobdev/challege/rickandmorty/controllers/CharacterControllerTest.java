package cl.mobdev.challege.rickandmorty.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import cl.mobdev.challege.rickandmorty.character.domain.Character;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.CharacterClient;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.LocationClient;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.models.CharacterModel;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.models.LocationModel;
import feign.FeignException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.io.InputStream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class CharacterControllerTest {

    private final Logger logger = LoggerFactory.getLogger (CharacterControllerTest.class);
    public static final String API_PATH_ROLE = "/api/v1/character/";
    public static final ObjectMapper JSON_MAPPER = new ObjectMapper(new JsonFactory());
     
    public CharacterControllerTest () {
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }  

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationClient lClientRest;

    @MockBean
    private CharacterClient cClientRest;

    private class TestErrorException extends FeignException {
        public TestErrorException (int status, String message){
            super (404, message);
        }
    }
   

    @Test
    public void obtener_character_WHEN_id_eq_1_THEN_rs_status_SUCCESS (TestInfo info) throws Exception {
        logger.debug (info.getDisplayName ());

        InputStream inps = this.getClass ().getResourceAsStream ("/rs/entity/character.json");
        Character rsExpect = JSON_MAPPER.readValue (inps, Character.class);

        inps = this.getClass ().getResourceAsStream ("/rs/model/location-model.json");
        LocationModel locationRs = JSON_MAPPER.readValue (inps, LocationModel.class);

        inps = this.getClass ().getResourceAsStream ("/rs/model/character-model.json");
        CharacterModel characterModel = JSON_MAPPER.readValue (inps, CharacterModel.class);

        Mockito.doReturn(characterModel ).when(cClientRest).get(Mockito.eq(1));
        Mockito.doReturn(locationRs ).when(lClientRest).get(Mockito.eq(1));

        
        MvcResult mvcResult = mvc.perform (get (API_PATH_ROLE.concat("1"))
                        .contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                        .andExpect (status ().isOk())
                        .andReturn ();

        byte[] contentAsByteArray = mvcResult.getResponse ().getContentAsByteArray ();
        Character character = JSON_MAPPER.readValue (contentAsByteArray, Character.class);
       
        assertEquals(rsExpect, character);
        Mockito.verify(cClientRest).get(Mockito.eq(1));
        Mockito.verify(lClientRest).get(Mockito.eq(1));
    }


    @Test
    public void obtener_character_WHEN_id_eq_1000_THEN_rs_status_NO_FOUND_msg_CHARACTER_NO_EXISTE (TestInfo info) throws Exception {
        Mockito.reset(cClientRest,lClientRest);
        Mockito.doThrow(new TestErrorException(404, "Character not found")).when(cClientRest).get(Mockito.eq(1000));
        MvcResult mvcResult = mvc.perform (get (API_PATH_ROLE.concat("1000"))
                        .contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                        .andExpect (status ().isNotFound())
                        .andReturn ();

        byte[] contentAsByteArray = mvcResult.getResponse ().getContentAsByteArray ();
        JsonNode error = JSON_MAPPER.readValue (contentAsByteArray, JsonNode.class);
        assertEquals("Not Found", error.get("error").asText());
        assertEquals("Character no existe", error.get("message").asText());
        Mockito.verify(cClientRest).get(Mockito.eq(1000));
    }
    

    @Test
    public void obtener_character_WHEN_id_eq_1000_THEN_rs_status_NO_FOUND_msg_CHARACTERS_LOCATION_NO_EXISTE (TestInfo info) throws Exception {
        InputStream inps = this.getClass ().getResourceAsStream ("/rs/model/character-model.json");
        CharacterModel characterModel = JSON_MAPPER.readValue (inps, CharacterModel.class);
        Mockito.reset(cClientRest,lClientRest);
        Mockito.doReturn(characterModel ).when(cClientRest).get(Mockito.eq(1000));              
        Mockito.doThrow(new TestErrorException(404, "Location not found")).when(lClientRest).get(Mockito.eq(1));

        MvcResult mvcResult = mvc.perform (get (API_PATH_ROLE.concat("1000"))
                        .contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                        .andExpect (status ().isNotFound())
                        .andReturn ();

        byte[] contentAsByteArray = mvcResult.getResponse ().getContentAsByteArray ();
        JsonNode error = JSON_MAPPER.readValue (contentAsByteArray, JsonNode.class);
        assertEquals("Not Found", error.get("error").asText());
        assertEquals("Character's Location no existe", error.get("message").asText());
        Mockito.verify(cClientRest).get(Mockito.eq(1000));
        Mockito.verify(lClientRest).get(Mockito.eq(1));
    }

    @Test
    public void obtener_character_WHEN_id_eq_10_y_character_no_tiene_url_origin_DEBE_retornar_character_sin_location (TestInfo info) throws Exception {
        InputStream inps = this.getClass ().getResourceAsStream ("/rs/entity/character-sin-origin.json");
        Character rsExpect = JSON_MAPPER.readValue (inps, Character.class);
       
        inps = this.getClass ().getResourceAsStream ("/rs/model/character-sin-url-origin.json");
        CharacterModel characterModel = JSON_MAPPER.readValue (inps, CharacterModel.class);
        Mockito.reset(cClientRest,lClientRest);
        Mockito.doReturn(characterModel ).when(cClientRest).get(Mockito.eq(10));

        MvcResult mvcResult = mvc.perform (get (API_PATH_ROLE.concat("10"))
                        .contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                        .andExpect (status ().isOk())
                        .andReturn ();

        byte[] contentAsByteArray = mvcResult.getResponse ().getContentAsByteArray ();
        Character character = JSON_MAPPER.readValue (contentAsByteArray, Character.class);
        
        assertEquals(rsExpect, character);
        Mockito.verify(cClientRest).get(Mockito.eq(10));
        
    }

    @Test
    public void obtener_character_WHEN_id_eq_10_y_character_no_tiene_origin_DEBE_retornar_character_sin_origin (TestInfo info) throws Exception {
        InputStream inps = this.getClass ().getResourceAsStream ("/rs/entity/character-sin-origin.json");
        Character rsExpect = JSON_MAPPER.readValue (inps, Character.class);
        
        inps = this.getClass ().getResourceAsStream ("/rs/model/character-sin-origin.json");
        CharacterModel characterModel = JSON_MAPPER.readValue (inps, CharacterModel.class);
        Mockito.reset(cClientRest,lClientRest);
        Mockito.doReturn(characterModel ).when(cClientRest).get(Mockito.eq(10));

        MvcResult mvcResult = mvc.perform (get (API_PATH_ROLE.concat("10"))
                        .contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                        .andExpect (status ().isOk())
                        .andReturn ();

        byte[] contentAsByteArray = mvcResult.getResponse ().getContentAsByteArray ();
        Character character = JSON_MAPPER.readValue (contentAsByteArray, Character.class);
        
        assertEquals(rsExpect, character);
        Mockito.verify(cClientRest).get(Mockito.eq(10));
    }

    @Test
    public void obtener_character_WHEN_id_eq_1_y_location_tiene_id_no_valido_DEBE_retornar_BAD_REQUEST(TestInfo info) throws Exception {
        InputStream inps = this.getClass ().getResourceAsStream ("/rs/model/character-url-origin-bad-id.json");
        CharacterModel characterModel = JSON_MAPPER.readValue (inps, CharacterModel.class);
        Mockito.reset(cClientRest,lClientRest);
        Mockito.doReturn(characterModel ).when(cClientRest).get(Mockito.eq(10));

        MvcResult mvcResult = mvc.perform (get (API_PATH_ROLE.concat("10"))
                        .contentType (MediaType.APPLICATION_JSON)
                        .accept (MediaType.APPLICATION_JSON))
                        .andExpect (status ().isBadRequest())
                        .andReturn ();

        byte[] contentAsByteArray = mvcResult.getResponse ().getContentAsByteArray ();
        JsonNode error = JSON_MAPPER.readValue (contentAsByteArray, JsonNode.class);
        assertEquals("Bad Request", error.get("error").asText());
        assertEquals("Character's location id no es numérico", error.get("message").asText());
        Mockito.verify(cClientRest).get(Mockito.eq(10));        
    }
}
