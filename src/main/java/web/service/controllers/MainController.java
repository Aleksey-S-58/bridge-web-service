package web.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("Hello this is web service!");
    }
}
