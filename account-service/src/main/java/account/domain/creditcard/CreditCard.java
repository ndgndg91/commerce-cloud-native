package account.domain.creditcard;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import mysql.BaseEntity;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditCardIdGen")
    @SequenceGenerator(name = "creditCardIdGen", sequenceName = "CREDIT_CARD_SEQ", allocationSize = 25)
    private long id;

    private String number;

    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType;

    public CreditCard(String number, CreditCardType type) {
        this.number = number;
        this.creditCardType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditCard that = (CreditCard) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
