package CIserver.app;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GitHubRepositoryDownloader.class)
class GitHubRepositoryDownloaderTests {

    /**
     * Tests the downloadRepository method of GitHubRepositoryDownloader and verifies
     * that it can successfully download the repository of a given commit id
     * @throws IOException GitHubRepositoryDownloader can throw IOException
     */
    @Test
    void downloadRepository() throws IOException {
        String commit = "4ed227fa48fc9106d176ed74d797484d5fa24790";
        String owner = "Deadlica";
        String repo = "continuous-integration";

        GitHubRepositoryDownloader gitHubRepositoryDownloader = new GitHubRepositoryDownloader();
        gitHubRepositoryDownloader.download(commit, owner, repo);

        File zipFile = new File(owner + "-" + repo + "-" + commit.substring(0, 7));
        assert zipFile.exists();
    }
}
