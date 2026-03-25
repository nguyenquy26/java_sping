package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePasgeController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePasgeController(ProductService productService, UserService userService,
            PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        model.addAttribute("productList", productService.getAllProduct());
        HttpSession session = request.getSession(false);

        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String postRegister(Model model, @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult registeBindingResult) {
        if (registeBindingResult.hasErrors()) {
            model.addAttribute("registerUser", registerDTO);
            return "client/auth/register";
        }

        User user = userService.registerDTOtoUser(registerDTO);
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(userService.getRoleByName("USER"));
        userService.handleSaveUser(user);
        return "redirect:login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {
        return "client/auth/deny";
    }
}
