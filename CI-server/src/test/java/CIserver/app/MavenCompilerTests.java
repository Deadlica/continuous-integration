package CIserver.app;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;



import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GitHubRepositoryDownloader.class)
class MavenCompilerTests {
/*
    @Test
    void workingCompililation() throws IOException {
        String commit = "4ed227fa48fc9106d176ed74d797484d5fa24790";
        String owner = "Deadlica";
        String repo = "continuous-integration";

        GitHubRepositoryDownloader gitHubRepositoryDownloader = new GitHubRepositoryDownloader();
        gitHubRepositoryDownloader.download(commit, owner, repo);


        MavenCompiler comp = new MavenCompiler();
        String output = comp.compile(owner + "-" + repo + "-" + commit.substring(0, 7));

        System.out.println(output);
        assertTrue(output.contains("BUILD SUCCESS"));

    }
    */
    /*
    @Test
    void syntaxError() throws IOException {


        MavenCompiler comp = new MavenCompiler();
        String output = comp.compile("src/test/Deadlica-continuous-integration-4ed227f-syntax-error");
        //add maven compiles code here

        assertTrue(output.contains("BUILD FAILURE"));
    }*/
}
