package order.domain.account;


import java.util.Objects;
import java.util.StringJoiner;

public class Address  {

    private long id;

    private String street1;

    private String street2;

    private String state;

    private String city;

    private String country;

    private String zipCode;

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

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("street1='" + street1 + "'")
                .add("street2='" + street2 + "'")
                .add("state='" + state + "'")
                .add("city='" + city + "'")
                .add("country='" + country + "'")
                .add("zipCode='" + zipCode + "'")
                .add("addressType=" + addressType)
                .toString();
    }
}