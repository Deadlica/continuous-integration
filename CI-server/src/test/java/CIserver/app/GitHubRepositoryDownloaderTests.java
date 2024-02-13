package CIserver.app;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = GitHubRepositoryDownloader.class)
class GitHubRepositoryDownloaderTests {

    /**
     * Tests the downloadRepository method of GitHubRepositoryDownloader and verifies
     * that it can successfully download the repository of a given commit id
     * @throws IOException GitHubRepositoryDownloader can throw IOException
     */
    @Test
    void downloadRepositoryTest() throws IOException {
        String commit = "4ed227fa48fc9106d176ed74d797484d5fa24790";
        String owner = "Deadlica";
        String repo = "continuous-integration";

        GitHubRepositoryDownloader gitHubRepositoryDownloader = new GitHubRepositoryDownloader();
        gitHubRepositoryDownloader.download(commit, owner, repo);

        File file = new File(owner + "-" + repo + "-" + commit.substring(0, 7));
        assert file.exists();
    }

/**
     * Tests the downloadRepository method of GitHubRepositoryDownloader and verifies
     * that it throws an IOException when the commit id,repo,or owner name is invalid
     * @throws IOException GitHubRepositoryDownloader can throw IOException
     */
    @Test
    void downloadRepositoryTestThrowsException() throws IOException{
        String commit = "4ed227fa48fcfsa9106d176ed74ds<ghds<fgdawsd797484d5fa24790";
        String owner = "Deahbfdzddlica";
        String repo = "continuous-igsd<gsdntegration";

        GitHubRepositoryDownloader gitHubRepositoryDownloader = new GitHubRepositoryDownloader();
        assertThrows(IOException.class, () -> gitHubRepositoryDownloader.download(commit, owner, repo));

    }

    @Test
    void downloadRepositoryExtractTest() throws IOException{
        String commit = "4ed227fa48fc9106d176ed74d797484d5fa24790";
        String owner = "Deadlica";
        String repo = "continuous-integration";


        GitHubRepositoryDownloader gitHubRepositoryDownloader = new GitHubRepositoryDownloader();
        gitHubRepositoryDownloader.download(commit, owner, repo);

        File file = new File(owner + "-" + repo + "-" + commit.substring(0, 7) + ".zip" );
        assert !file.exists();
    }
}
