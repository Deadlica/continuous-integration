package CIserver.rest;

import CIserver.app.GitHubRepositoryDownloader;
import CIserver.app.MavenCompiler;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * This class setups REST endpoints and handles different HTTP requests
 */
@RestController
public class CIController {

  @GetMapping("/test")
  public ResponseEntity<String> testGet() {
    return ResponseEntity.ok("CI server GET job done");
  }

  /**
   * Method for handling GitHub webhooks to download and test code
   * @param requestBody GitHub's webhooks payload
   * @return String response to indicate success/failure
   * @throws IOException
   */
  @PostMapping("/push")
  public ResponseEntity<String> postPost(@RequestBody String requestBody) throws IOException {
    // Parses json
    JSONObject jsonObject = new JSONObject(requestBody);
    JSONObject repository = jsonObject.getJSONObject("repository");
    String commit = jsonObject.getString("after");
    String repositoryName = repository.getString("name");
    String owner = repository.getJSONObject("owner").getString("name");

    // Download GitHub Repository
    GitHubRepositoryDownloader gitHubDownloader = new GitHubRepositoryDownloader();
    String repo = gitHubDownloader.download(commit, owner, repositoryName);
    if(repo.isEmpty()) {
      return ResponseEntity.ok("Failed to download repository " + repositoryName);
    }

    // Compiles maven project
    MavenCompiler mavenCompiler = new MavenCompiler();
    String output = mavenCompiler.compile(repo);
    if(output.isEmpty()) {
      return ResponseEntity.ok("Failed to compile repository " + repositoryName);
    }
    //
    // Code here for writing build log to database/file
    //
    String repoPath = System.getProperty("user.dir") + File.separator + repo;
    FileUtils.deleteDirectory(new File(repoPath));
    File file = new File(repoPath);
    if(file.exists()) {
      output = "Failed to delete repository from server: " + repo;
    }
    return ResponseEntity.ok(output);
  }

  @GetMapping("/error")
  public ResponseEntity<String> error() {
    return ResponseEntity.ok("error");
  }
}
