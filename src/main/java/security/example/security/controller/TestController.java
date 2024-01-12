package security.example.security.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super/test")
@CrossOrigin
public class TestController {
    @GetMapping("/text")
    public String testSuper(){
        return  "hii";
    }
}
