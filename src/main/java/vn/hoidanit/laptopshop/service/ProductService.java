package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    public List<Object[]> getFactorySummary() {
        return productRepository.getFactorySummary();
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            // check cart da ton tai chua
            Cart cart = cartRepository.findByUser(user);
            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = cartRepository.save(otherCart);
            }
            // Save CartDetail
            // Tim product theo id
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();
                // check san pham da duoc them vao hay chua
                CartDetail oldDetail = cartDetailRepository.findByCartAndProduct(cart, realProduct);
                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setQuantity(1);
                    cartDetail.setPrice(realProduct.getPrice());

                    cartDetailRepository.save(cartDetail);
                    // update Cart
                    int sumCart = cart.getSum() + 1;
                    cart.setSum(sumCart);
                    cartRepository.save(cart);
                    session.setAttribute("sum", sumCart);

                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

}
