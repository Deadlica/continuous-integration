package CIserver.rest;

import CIserver.app.GitHubRepositoryDownloader;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CIController {

  @GetMapping("/test")
  public ResponseEntity<String> testGet() {
    return ResponseEntity.ok("CI server GET job done");
  }

  @PostMapping("/push")
  public String postPost(@RequestBody String requestBody) throws IOException {
    JSONObject jsonObject = new JSONObject(requestBody);
    JSONObject repository = jsonObject.getJSONObject("repository");
    String commit = jsonObject.getString("after");
    String repositoryName = repository.getString("name");
    String owner = repository.getJSONObject("owner").getString("name");

    GitHubRepositoryDownloader gitHubDownloader = new GitHubRepositoryDownloader();
    gitHubDownloader.download(commit, owner, repositoryName);

    return "CI server POST job done";
  }

  @GetMapping("/error")
  public ResponseEntity<String> error() {
    return ResponseEntity.ok("error");
  }
}
