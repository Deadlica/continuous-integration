package CIserver.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CIController.class)
@AutoConfigureMockMvc
public class CIControllerTests {

    @Test
    void postRequestTest() throws Exception {
        CIController webhookController = new CIController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(webhookController).build();
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"repository\":{\"name\":\"Webhook-Testing\",\"clone_url\":\"https://github.com/NoelMT/Webhook-Testing.git\"},\"commits\":[{\"id\":\"dc1dd5850c7a0d677c430bcda19a7a08fb2d59ac\",\"message\":\"nother one\",\"timestamp\":\"2024-02-11T16:46:08+01:00\"}]}"))
                .andExpect(status().isOk());

    }
}
