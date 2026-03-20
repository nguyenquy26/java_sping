package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getDashboard(Model model) {
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("productList", productList);
        return "admin/product/index";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(Model model, @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProducBindingResult, @RequestParam("fileUpload") MultipartFile file) {
        List<FieldError> errors = newProducBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProducBindingResult.hasErrors()) {
            model.addAttribute("newProduct", product);
            return "admin/product/create";
        }

        String pathProduct = uploadService.handleSaveUploadFile(file, "product");
        product.setImage(pathProduct);
        productService.saveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductById(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("updateProduct", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model, @ModelAttribute("updateProduct") @Valid Product product,
            BindingResult updateProductBindingResult,
            @RequestParam("fileUpload") MultipartFile file) {

        List<FieldError> errors = updateProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (updateProductBindingResult.hasErrors()) {
            model.addAttribute("newProduct", product);
            return "admin/product/update";
        }

        Product currentProduct = productService.getProductById(product.getId());
        String pathImgae = uploadService.handleSaveUploadFile(file, "product");
        if (currentProduct != null) {
            currentProduct.setImage(pathImgae);
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setTarget(product.getTarget());
            productService.saveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Product currentProduct = productService.getProductById(id);
        model.addAttribute("deleteProduct", currentProduct);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postMethodName(Model model, @ModelAttribute("deleteProduct") Product product) {
        productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }

}
