package br.com.balanzo.api.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FamiliesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void listFamilies_returnsEmptyList_whenNoAuth() throws Exception {
        mockMvc.perform(get("/api/v1/families"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
