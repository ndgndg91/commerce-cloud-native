package order.domain;

import static account.domain.address.AddressType.SHIPPING;

import account.domain.Account;
import account.domain.address.Address;
import account.domain.creditcard.CreditCard;
import account.domain.creditcard.CreditCardType;
import account.domain.customer.Customer;
import order.OrderApplication;
import order.domain.invoice.Invoice;
import order.domain.invoice.InvoiceRepository;
import order.domain.order.LineItem;
import order.domain.order.Order;
import order.domain.order.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderApplication.class)
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @BeforeAll
    @AfterAll
    public void reset() {
        orderRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    @Test
    void orderTest() throws Exception {
        //given
        Account account = new Account("12345");
        Customer customer = new Customer("Dong-Gil", "Nam", "ndgndg91@gmail.com", account);
        CreditCard creditCard = new CreditCard("5388032306460084", CreditCardType.MASTERCARD);
        customer.getAccount().getCreditCards().add(creditCard);

        String street1 = "235, Toegye-ro, Jung-gu, Seoul, Republic of Korea";
        String street2 = "804-B";
        Address shippingAddress = new Address(street1, street2, "SEOUL", "SEOUL", "KOREA", SHIPPING, "04558");
        customer.getAccount().getAddresses().add(shippingAddress);

        //when
        Order order = new Order(account.getAccountNumber(), shippingAddress);
        LineItem bce = new LineItem("Best. Cloud. Ever. (T-Shirt, Men's Large)", "SKU-24642", 1, 21.99, .06);
        order.addLineItem(bce);
        LineItem wgn = new LineItem("We're gonna need a bigger VM (T-Shirt, Women's Small)", "SKU-12464", 4,
            13.99, .06);
        order.addLineItem(wgn);
        LineItem cpa = new LineItem("cf push awesome (Hoodie, Men's Medium)", "SKU-64233", 2, 21.99, .06);
        order.addLineItem(cpa);

        order = orderRepository.save(order);

        //then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getLineItems().size()).isEqualTo(3);

        Assertions.assertThat(order.getLastModifiedAt()).isEqualTo(order.getCreatedAt());
        order = orderRepository.save(order);
        Assertions.assertThat(order.getLastModifiedAt()).isNotEqualTo(order.getCreatedAt());

        Address billingAddress = new Address("875 Howard St", null, "CA", "San Francisco", "United States", "94103");
        String accountNumber = "9111070";

        Invoice invoice = new Invoice(accountNumber, billingAddress);
        invoice.addOrder(order);
        invoice = invoiceRepository.save(invoice);
        Assertions.assertThat(invoice.getOrders().size()).isEqualTo(1);
        Invoice byBillingAddress = invoiceRepository.findByBillingAddress(billingAddress);
        Assertions.assertThat(byBillingAddress).isEqualTo(invoice);

        //verify
    }
}
