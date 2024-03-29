package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.*;
import com.myapps.store.ecommerce.payload.OrderDto;
import com.myapps.store.ecommerce.payload.OrderRequest;
import com.myapps.store.ecommerce.payload.OrderResponse;
import com.myapps.store.ecommerce.repository.CartRepository;
import com.myapps.store.ecommerce.repository.OrderRepository;
import com.myapps.store.ecommerce.repository.UserRepository;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {

    // Razorpay
    private static final String KEY = "rzp_test_5xtWTTvFiifAdd";
    private static final String KEY_SECRET = "FOECyV9QzinpjwC3Gpxr47xc";
    private static final String CURRENCY = "INR";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDto createOrder(OrderRequest request, String username) {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        int cartId = request.getCartId();
        String orderAddress = request.getOrderAddress();
        // find cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));
        // getting CartItem
        Set<CartItem> items = cart.getCartItem();
        Order order = new Order();

        AtomicReference<Double> totalOrderPrice = new AtomicReference<Double>(0.0);
        Set<OrderItem> orderItems = items.stream().map((cartItem) -> {
            OrderItem orderItem = new OrderItem();
            // set cartItem into OrderItem

            // set product in orderItem
            orderItem.setProduct(cartItem.getProduct());

            // set productQty in orderItem

            orderItem.setProductQuantity(cartItem.getQuantity());

            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);

            totalOrderPrice.set(totalOrderPrice.get() + orderItem.getTotalProductPrice());
            int productId = orderItem.getProduct().getProductId();

            return orderItem;
        }).collect(Collectors.toSet());

        boolean paidStatus = request.isPaid();
        String paidStatusString = paidStatus == true ? "PAID" : "NOT PAID";

        order.setBillingAddress(orderAddress);
        order.setOrderDelivered(null);
        order.setOrderStatus("CREATED");
        order.setPaymentStatus(paidStatusString);
        order.setUser(user);
        order.setOrderItem(orderItems);
        order.setOrderAmt(totalOrderPrice.get());
        order.setOrderCreatedAt(new Date());
        Order save;
        if (order.getOrderAmt() > 0) {
            save = orderRepository.save(order);
            cart.getCartItem().clear();
            cartRepository.save(cart);
        } else {
            throw new ResourceNotFoundException("Please add items to cart first and then proceed to place the " + "order");
        }

        return modelMapper.map(save, OrderDto.class);
    }

    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not " + "found"));
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }

    public OrderDto findOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return modelMapper.map(order, OrderDto.class);
    }

    public OrderResponse findAllOrders(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> findAll = orderRepository.findAll(pageable);
        List<Order> content = findAll.getContent();

        // change order to orderDto
        List<OrderDto> collect = content.stream().map((each) -> this.modelMapper.map(each, OrderDto.class)).collect(Collectors.toList());
        OrderResponse response = new OrderResponse();
        response.setContent(collect);
        response.setPageNumber(findAll.getNumber());
        response.setLastPage(findAll.isLast());
        response.setPageSize(findAll.getSize());
        response.setTotalPage(findAll.getTotalPages());

        // getTotalElements return Long
        response.setTotalElements(findAll.getTotalElements());

        return response;
    }

    public OrderResponse findAllOrdersByUser(int pageNumber, int pageSize, String username) {

        User foundUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User " + "not found"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> findAll = orderRepository.findAllByUser(foundUser, pageable);
        List<Order> content = findAll.getContent();

        // change order to orderDto
        List<OrderDto> collect = content.stream().map((each) -> this.modelMapper.map(each, OrderDto.class)).collect(Collectors.toList());
        OrderResponse response = new OrderResponse();
        response.setContent(collect);
        response.setPageNumber(findAll.getNumber());
        response.setLastPage(findAll.isLast());
        response.setPageSize(findAll.getSize());
        response.setTotalPage(findAll.getTotalPages());

        // getTotalElements return Long
        response.setTotalElements(findAll.getTotalElements());

        return response;
    }

    // Razorpay
    public TransactionDetails createTransaction(double amount) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100));
            jsonObject.put("currency", CURRENCY);


            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            com.razorpay.Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails = prepareTransactionDetails(order);

            return transactionDetails;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(com.razorpay.Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);

        return transactionDetails;
    }

}
