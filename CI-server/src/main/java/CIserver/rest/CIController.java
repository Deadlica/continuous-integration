package CIserver.rest;

import CIserver.app.BuildThread;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    BuildThread buildThread = new BuildThread(requestBody);
    buildThread.start();
    return ResponseEntity.ok("CI Server work started");
  }

  /**
   * Method for handling error requests
   * @return String response to indicate error
   */
  @GetMapping("/error")
  public ResponseEntity<String> error() {
    return ResponseEntity.ok("error");
  }
}
