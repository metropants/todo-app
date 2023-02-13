package xyz.metropants.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTests {

    public static final String VALID_BODY = """
                        {
                            "username": "test",
                            "password": "password123"
                        }
                """;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldRegisterUser() throws Exception {
        mvc.perform(post("/auth/register").content(VALID_BODY).contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeInvalidUsername() throws Exception {
        String body = """
                        {
                            "username": "invalid_username",
                            "password": "password123"
                        }
                """;

        mvc.perform(post("/auth/register").content(body).contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAccessToken() throws Exception {
        mvc.perform(post("/auth/login").content(VALID_BODY).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists());
    }

}
