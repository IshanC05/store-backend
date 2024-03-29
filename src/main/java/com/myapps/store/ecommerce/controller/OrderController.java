package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.model.TransactionDetails;
import com.myapps.store.ecommerce.payload.ApiResponse;
import com.myapps.store.ecommerce.payload.OrderDto;
import com.myapps.store.ecommerce.payload.OrderRequest;
import com.myapps.store.ecommerce.payload.OrderResponse;
import com.myapps.store.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderReq, Principal p) {
        String username = p.getName();
        OrderDto order = orderService.createOrder(orderReq, username);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> cancelOrderById(@PathVariable int orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(new ApiResponse("Order Cancelled with id:" + orderId, true), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable int orderId) {
        OrderDto orderDto = orderService.findOrder(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public OrderResponse findAllOrders(@RequestParam(defaultValue = "100", value = "pageSize") int pageSize,
                                       @RequestParam(defaultValue = "0", value = "pageNumber") int pageNumber) {
        OrderResponse findAllOrders = orderService.findAllOrders(pageNumber, pageSize);
        return findAllOrders;
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public OrderResponse findAllOrdersByUserId(@RequestParam(defaultValue = "100", value = "pageSize") int pageSize,
                                               @RequestParam(defaultValue = "0", value = "pageNumber") int pageNumber,
                                               Principal principal) {
        OrderResponse findAllOrders = orderService.findAllOrdersByUser(pageNumber, pageSize, principal.getName());
        return findAllOrders;
    }

    // Razorpay

    @GetMapping("/createTransaction/{amount}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public TransactionDetails createTransaction(@PathVariable double amount) {
        return orderService.createTransaction(amount);
    }
}
