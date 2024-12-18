package com.hikadobushido.ecommerce_java.controller.admin;

import com.hikadobushido.ecommerce_java.common.PageUtil;
import com.hikadobushido.ecommerce_java.model.OrderResponse;
import com.hikadobushido.ecommerce_java.model.PaginatedOrderResponse;
import com.hikadobushido.ecommerce_java.model.UserInfo;
import com.hikadobushido.ecommerce_java.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/orders")
@SecurityRequirement(name = "Bearer")
@RequiredArgsConstructor
public class AdminOrderController {

  private final OrderService orderService;

  @GetMapping("")
  public ResponseEntity<PaginatedOrderResponse> findOrdersByUserId(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "order_id,desc") String[] sort
  ) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserInfo userInfo = (UserInfo) authentication.getPrincipal();

    List<Order> sortOrder = PageUtil.parseSortOrderRequest(sort);

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

    Page<OrderResponse> userOrders = orderService.findOrdersByUserIdAndPageable(userInfo.getUser()
        .getUserId(), pageable);

    PaginatedOrderResponse paginatedOrderResponse = orderService.convertOrderPage(userOrders);
    return ResponseEntity.ok(paginatedOrderResponse);
  }

  @PutMapping("/{orderId}/cancel")
  public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
    orderService.cancelOrder(orderId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> findOrderById(@PathVariable Long orderId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserInfo userInfo = (UserInfo) authentication.getPrincipal();

    return orderService.findOrderById(orderId)
        .map(order -> {
          OrderResponse orderResponse = OrderResponse.fromOrder(order);
          return ResponseEntity.ok(orderResponse);
        })
        .orElse(ResponseEntity.notFound().build());
  }
}