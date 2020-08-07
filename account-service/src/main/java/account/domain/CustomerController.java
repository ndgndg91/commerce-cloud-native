package account.domain;

import account.domain.customer.Customer;
import account.domain.customer.CustomerRepository;
import account.domain.dto.CreateCustomerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> byId(@PathVariable final long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final CreateCustomerRequest request) throws URISyntaxException {
        Customer customer = request.toCustomer();
        Customer save = customerRepository.save(customer);
        return ResponseEntity.created(new URI("/customers/" + save.getId())).build();
    }
}
