package account.domain.dto;

import account.domain.Account;
import account.domain.address.Address;
import account.domain.address.AddressType;
import account.domain.creditcard.CreditCard;
import account.domain.creditcard.CreditCardType;
import account.domain.customer.Customer;

import static account.domain.address.AddressType.SHIPPING;

public final class CreateCustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String accountNumber;

    private String creditCardNumber;
    private String creditCardType;

    private String street1;
    private String street2;
    private String state;
    private String city;
    private String country;
    private String zipCode;
    private String addressType;

    private CreateCustomerRequest(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public Customer toCustomer(){
        CreditCard creditCard = new CreditCard(creditCardNumber, CreditCardType.valueOf(creditCardType));
        Account account = new Account(accountNumber);
        Customer customer = new Customer(firstName, lastName, email, account);
        customer.getAccount().getCreditCards().add(creditCard);
        Address address = new Address(street1, street2, state, city, country, AddressType.valueOf(addressType), zipCode);
        customer.getAccount().getAddresses().add(address);

        return customer;
    }
}
