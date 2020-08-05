package account.domain;

import account.domain.creditcard.CreditCard;
import account.domain.address.Address;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import mysql.BaseEntity;

@Entity
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountIdGen")
    @SequenceGenerator(name = "accountIdGen", sequenceName = "ACCOUNT_SEQ_SEQ", allocationSize = 25)
    private long id;

    private String accountNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses = new HashSet<>();

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }
}
