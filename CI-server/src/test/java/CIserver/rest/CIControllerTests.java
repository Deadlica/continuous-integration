package CIserver.rest;

import CIserver.app.CiServerApplication;
import CIserver.app.GitHubRepositoryDownloader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = CiServerApplication.class)
@AutoConfigureMockMvc
class CIControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPushmappnig() throws Exception {

        String requestBody = "your-request-body";  // Replace with an actual request body for testing

        mockMvc.perform(MockMvcRequestBuilders.post("/push")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("CI Server work started"));
    }
    /*
    @Test
    void testShowBuildwithcommit() throws Exception {
        String commit = "4ed227fa48fc9106d176ed74d797484d5fa24790";
        mockMvc.perform(MockMvcRequestBuilders.get("/builds/{commit}",commit) )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }*/
}
