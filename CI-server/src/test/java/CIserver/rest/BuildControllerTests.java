package CIserver.rest;

import CIserver.app.CiServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(classes = CiServerApplication.class)
@AutoConfigureMockMvc
class BuildControllerTests {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests the push mapping of the CI server
     * @throws Exception
     */
    @Test
    void testShowBuild() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/builds"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    /*
    @Test
    void testShowBuildwithcommit() throws Exception {
        String commit = "4ed227fa48fc9106d176ed74d797484d5fa24790";
        mockMvc.perform(MockMvcRequestBuilders.get("/builds/{commit}",commit) )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }*/
}
