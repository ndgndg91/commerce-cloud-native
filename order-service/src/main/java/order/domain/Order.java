package order.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.persistence.Id;
import mongo.BaseEntity;
import order.domain.account.Address;
import order.domain.account.AddressType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order extends BaseEntity {

    @Id
    private String orderId;

    private String accountNumber;

    private OrderStatus orderStatus;

    private List<LineItem> lineItems = new ArrayList<>();

    private Address shippingAddress;

    public Order(String accountNumber, Address shippingAddress) {
        this.accountNumber = accountNumber;
        this.shippingAddress = shippingAddress;
        this.shippingAddress.setAddressType(AddressType.SHIPPING);
        this.orderStatus = OrderStatus.PENDING;
    }


    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("orderId='" + orderId + "'")
                .add("accountNumber='" + accountNumber + "'")
                .add("orderStatus=" + orderStatus)
                .add("lineItems=" + lineItems)
                .add("shippingAddress=" + shippingAddress)
                .toString();
    }
}
