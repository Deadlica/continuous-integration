package CIserver.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CIController {

  @GetMapping("/test")
  public ResponseEntity<String> testGet() {
    return ResponseEntity.ok("CI server GET job done");
  }

  @PostMapping("/example")
  public ResponseEntity<String> postPost(@RequestBody String requestBody) {
    return ResponseEntity.ok("CI server POST job done");
  }
}
