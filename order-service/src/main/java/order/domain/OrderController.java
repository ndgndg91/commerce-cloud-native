package order.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/orders/test")
    public ResponseEntity<Void> orderTest(){
        orderService.order();
        return ResponseEntity.ok().build();
    }
}
