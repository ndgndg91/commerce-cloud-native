package order.domain.account;

import java.util.Objects;
import java.util.StringJoiner;

public class CreditCard {
    private long id;

    private String number;

    private CreditCardType creditCardType;

    public CreditCard(String number, CreditCardType type) {
        this.number = number;
        this.creditCardType = type;
    }

    public CreditCard() {
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
        CreditCard that = (CreditCard) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreditCard.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("number='" + number + "'")
                .add("creditCardType=" + creditCardType)
                .toString();
    }
}
