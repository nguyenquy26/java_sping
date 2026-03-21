package vn.hoidanit.laptopshop.controller.client;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class HomePasgeController {
    private final ProductService productService;

    public HomePasgeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("productList", productService.getAllProduct());
        return "client/homepage/show";
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        return "client/auth/register";
    }

}
