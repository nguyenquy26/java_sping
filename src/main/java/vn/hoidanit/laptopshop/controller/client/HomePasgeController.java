package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePasgeController {
    @GetMapping("/")
    public String getHomePage() {
        return "client/homepage/show";
    }

}
