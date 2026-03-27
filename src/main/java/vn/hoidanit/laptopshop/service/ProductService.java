package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
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

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart getCartByCartDetail(CartDetail cartDetail) {
        return cartRepository.findByCartDetails(cartDetail);
    }

    public CartDetail getCartDetailById(long id) {
        return cartDetailRepository.findById(id).orElse(null);
    }

    public void deletedCartDetailById(long id) {
        cartDetailRepository.deleteById(id);
    }

    public void deletedCartById(long id) {
        cartRepository.deleteById(id);
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail realCartDetail = cdOptional.get();
                realCartDetail.setQuantity(cartDetail.getQuantity());
                cartDetailRepository.save(realCartDetail);
            }

        }
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

    public void handlePlaceOrder(User user, HttpSession session, String receiverAddress, String receiverName,
            String receiverPhone) {
        // create Order
        Order order = new Order();
        order.setUser(user);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order = orderRepository.save(order);

        // create OrderDetail
        // Step 1 get cartByUser
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetailRepository.save(orderDetail);
                }
                // Step 2: delete Cart_Detail
                for (CartDetail cartDetail : cartDetails) {
                    cartDetailRepository.deleteById(cartDetail.getId());
                    ;
                }

                cartRepository.deleteById(cart.getId());

                // Step 3: Update session
                session.setAttribute("sum", 0);
            }
        }
    }

}
