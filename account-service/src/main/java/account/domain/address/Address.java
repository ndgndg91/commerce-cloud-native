package account.domain.address;

import javax.persistence.*;
import java.util.Objects;
import mysql.BaseEntity;

@Entity
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressIdGen")
    @SequenceGenerator(name = "addressIdGen", sequenceName = "ADDRESS_ID_SEQ", allocationSize = 25)
    private long id;

    private String street1;

    private String street2;

    private String state;

    private String city;

    private String country;

    private String zipCode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    public Address() {
    }

    public Address(String street1, String street2, String state, String city, String country, AddressType type,
        String zipCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.state = state;
        this.city = city;
        this.country = country;
        this.addressType = type;
        this.zipCode = zipCode;
    }

    public Address(String street1, String street2, String state, String city, String country, String zipCode) {
        this(street1, street2, state, city, country, null, zipCode);
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
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public long getId() {
        return id;
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

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
}