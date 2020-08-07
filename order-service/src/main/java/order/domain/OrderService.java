package order.domain;

import order.domain.account.Address;
import order.domain.account.Customer;
import order.domain.invoice.Invoice;
import order.domain.invoice.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final RestTemplate restTemplate;

    private final OrderRepository orderRepository;

    private final InvoiceRepository invoiceRepository;

    public OrderService(
            RestTemplate restTemplate,
            OrderRepository orderRepository,
            InvoiceRepository invoiceRepository
    )
    {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public void order(){
        ResponseEntity<Customer> exchange = restTemplate.exchange("http://127.0.0.1:1010/customers/1",
                HttpMethod.GET, null, Customer.class);

        Customer customer = exchange.getBody();
        log.info("{}", customer);

        assert customer != null;
        Address address = customer.getAccount().getAddresses().stream().filter(a -> a.getId() == 1).findFirst().orElseThrow();
        String accountNumber = customer.getAccount().getAccountNumber();
        Order order = new Order(accountNumber, address);

        LineItem bce = new LineItem("Best. Cloud. Ever. (T-Shirt, Men's Large)", "SKU-24642", 1, 21.99, .06);
        order.addLineItem(bce);
        LineItem wgn = new LineItem("We're gonna need a bigger VM (T-Shirt, Women's Small)", "SKU-12464", 4,
                13.99, .06);
        order.addLineItem(wgn);
        LineItem cpa = new LineItem("cf push awesome (Hoodie, Men's Medium)", "SKU-64233", 2, 21.99, .06);
        order.addLineItem(cpa);

        order = orderRepository.save(order);
        log.info("{}", order);

        Invoice invoice = new Invoice(accountNumber, address);
        invoice.addOrder(order);
        invoice = invoiceRepository.save(invoice);

        log.info("{}", invoice);
    }

}
