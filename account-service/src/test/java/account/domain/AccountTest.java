package account.domain;

import static account.domain.address.AddressType.SHIPPING;

import account.AccountApplication;
import account.domain.creditcard.CreditCard;
import account.domain.creditcard.CreditCardType;
import account.domain.customer.Customer;
import account.domain.customer.CustomerRepository;
import account.domain.address.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AccountApplication.class)
public class AccountTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객_추가")
    void customerTest() throws Exception {
        //given
        Account account = new Account("12345");
        Customer customer = new Customer("Dong-Gil", "Nam", "ndgndg91@gmail.com", account);
        CreditCard creditCard = new CreditCard("5388032306460084", CreditCardType.MASTERCARD);
        customer.getAccount().getCreditCards().add(creditCard);

        String street1 = "235, Toegye-ro, Jung-gu, Seoul, Republic of Korea";
        String street2 = "804-B";
        Address address = new Address(street1, street2, "SEOUL", "SEOUL", "KOREA", SHIPPING, "04558");
        customer.getAccount().getAddresses().add(address);

        //when
        customer = customerRepository.save(customer);
        Customer persistedResult = customerRepository.findById(customer.getId()).orElse(null);

        //then
        Assertions.assertThat(persistedResult).isNotNull();
        boolean exist = persistedResult.getAccount().getAddresses()
            .stream()
            .anyMatch(add -> add.getStreet1().equalsIgnoreCase(street1));
        Assertions.assertThat(exist).isTrue();

        customerRepository.findByEmailContaining(customer.getEmail())
            .orElseThrow(() -> new RuntimeException("there is supposed to be a matching record!"));
    }
}
