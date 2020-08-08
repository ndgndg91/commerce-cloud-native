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

import javax.transaction.Transactional;


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

    @Transactional
    public void order(){

        Customer customer = getCustomer(1);
        log.info("{}", customer);

        assert customer != null;
        Address address = customer.getAccount().getAddresses().stream().filter(a -> a.getId() == 1).findFirst().orElseThrow();
        String accountNumber = customer.getAccount().getAccountNumber();
        Order order = new Order(accountNumber, address);

        LineItem firstLineItem = getLineItem(1, 5);
        log.info("{}", firstLineItem);
        order.addLineItem(firstLineItem);

        LineItem secondLineItem = getLineItem(2, 10);
        order.addLineItem(secondLineItem);

        LineItem thirdLineItem = getLineItem(3, 20);
        order.addLineItem(thirdLineItem);

        order = orderRepository.save(order);
        log.info("{}", order);

        Invoice invoice = new Invoice(accountNumber, address);
        invoice.addOrder(order);
        invoice = invoiceRepository.save(invoice);

        log.info("{}", invoice);
    }

    private Customer getCustomer(long id) {
        ResponseEntity<Customer> customerResponseEntity = restTemplate.exchange("http://127.0.0.1:1010/customers/" + id,
                HttpMethod.GET, null, Customer.class);

        return customerResponseEntity.getBody();
    }

    private LineItem getLineItem(long id, double taxRate){
        ResponseEntity<LineItem> exchange = restTemplate.exchange("http://127.0.0.1:3030/products/" + id,
                HttpMethod.GET, null, LineItem.class);

        LineItem lineItem = exchange.getBody();
        assert lineItem != null;
        lineItem.calculateTax(taxRate);
        return lineItem;
    }

}
