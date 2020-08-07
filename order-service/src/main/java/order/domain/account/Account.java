package order.domain.account;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class Account {
    private long id;

    private String accountNumber;

    private Set<CreditCard> creditCards = new HashSet<>();

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

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("accountNumber='" + accountNumber + "'")
                .add("creditCards=" + creditCards)
                .add("addresses=" + addresses)
                .toString();
    }
}
