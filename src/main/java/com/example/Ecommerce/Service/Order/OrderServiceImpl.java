package com.example.Ecommerce.Service.Order;

import com.example.Ecommerce.DTO.OrderDTO;
import com.example.Ecommerce.Entities.*;
import com.example.Ecommerce.Repositries.CartItemRepository;
import com.example.Ecommerce.Repositries.OrderRepository;
import com.example.Ecommerce.Service.Cart.CartServiceImpl;
import com.example.Ecommerce.Service.Cart.CartItem.CartItemServiceImpl;
import com.example.Ecommerce.Service.Order.OrderItem.OrderItemServiceImpl;
import com.example.Ecommerce.Service.User.UserServiceImpl;
import com.example.Ecommerce.Service.product.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartServiceImpl cartService;
    private final CartItemRepository cartItemRepository;
    private final CartItemServiceImpl cartItemService;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final OrderItemServiceImpl orderItemService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CartServiceImpl cartService,
                            CartItemRepository cartItemRepository,
                            CartItemServiceImpl cartItemService,
                            ProductServiceImpl productService,
                            UserServiceImpl userService,
                            OrderItemServiceImpl orderItemService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
        this.orderItemService = orderItemService;
    }


    @Override
    public Order saveOrder(OrderDTO order) {

        User user = userService.getById(order.getUserId());

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItemService.moveCartItemToOrderItems(user.getCart().getCartItems() , createdOrder));
        createdOrder.setTotalPrice(user.getCart().getTotalPrice());
        createdOrder.setAddress(order.getAddress());
        createdOrder.setCity(order.getCity());
        createdOrder.setPhoneNumber(order.getPhoneNumber());
        createdOrder.setOrderDate(LocalDateTime.now());

        createdOrder.setStatus("PENDING");

        Payment payment = new Payment();
        payment.setAmount(order.getPaymentDetails().getAmount());
        payment.setPaymentMethod(order.getPaymentDetails().getPaymentMethod());
        payment.setStatus("PENDING");

        createdOrder.setPayment(payment);

        //update products quantities
        for(CartItem cartItem: user.getCart().getCartItems()){
            Product pr = cartItemRepository.findProductByCartItemId(cartItem.getId());
            pr.setQuantity(pr.getQuantity() - cartItem.getQuantity());
            productService.updateProduct(pr,pr.getId());
        }

        // empty the cart for new order
        cartItemService.deleteAllCartItems(user.getCart().getId());
        user.getCart().setTotalPrice(0.0);
        cartService.updateCart(user.getCart(),user.getCart().getId());


       return orderRepository.save(createdOrder);
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId).get();
    }

    @Override
    public Order updateOrderStatus(String status, Long orderId){
        Order order  = orderRepository.findById(orderId).get();
        order.setStatus(status);
        return orderRepository.save(order);
    }

}
