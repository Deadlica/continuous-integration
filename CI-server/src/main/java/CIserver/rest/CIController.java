package CIserver.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;



@RestController
public class CIController {

  @GetMapping("/test")
  public ResponseEntity<String> testGet() {
    return ResponseEntity.ok("CI server GET job done");
  }

  @GetMapping("/")
  public ResponseEntity<String> defGet() {
    return ResponseEntity.ok("CI server GET job starting");
  }

  @PostMapping("/example")
  public ResponseEntity<String> postPost(@RequestBody String requestBody) {
    return ResponseEntity.ok("CI server POST job done");
  }

  @PostMapping("/")
  public ResponseEntity<String> postJson(@RequestBody String payload) {
    System.out.println("Received webhook payload:");
    System.out.println(payload);
    JsonParser jsonParser = new JsonParser();
    JsonElement jsonElement = jsonParser.parse(payload);
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    String repoName = jsonObject.get("repository").getAsJsonObject().get("name").getAsString();
    String commitId = jsonObject.get("commits").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
    String clone_url = jsonObject.get("repository").getAsJsonObject().get("clone_url").getAsString();

    return ResponseEntity.ok("CI server POST job done" + "\n" + "Repository: " + repoName + "\n" + "Commit ID: "
            + commitId + "\n" + "Clone URL: " + clone_url);
  }

}
