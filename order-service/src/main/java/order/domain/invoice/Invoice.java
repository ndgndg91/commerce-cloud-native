package order.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import javax.persistence.Id;
import mongo.BaseEntity;
import order.domain.account.Address;
import order.domain.account.AddressType;
import order.domain.Order;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Invoice extends BaseEntity {

    @Id
    private String invoiceId;

    private String customerId;

    private List<Order> orders = new ArrayList<>();

    private Address billingAddress;

    private InvoiceStatus invoiceStatus;

    public Invoice(String customerId, Address billingAddress) {
        this.invoiceId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.billingAddress = billingAddress;
        this.billingAddress.setAddressType(AddressType.BILLING);
        this.invoiceStatus = InvoiceStatus.CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Invoice invoice = (Invoice) o;

        if (!invoiceId.equals(invoice.invoiceId)) {
            return false;
        }
        return customerId.equals(invoice.customerId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + invoiceId.hashCode();
        result = 31 * result + customerId.hashCode();
        return result;
    }

    public void addOrder(Order order) {
        order.setAccountNumber(this.customerId);
        this.orders.add(order);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Invoice.class.getSimpleName() + "[", "]")
                .add("invoiceId='" + invoiceId + "'")
                .add("customerId='" + customerId + "'")
                .add("orders=" + orders)
                .add("billingAddress=" + billingAddress)
                .add("invoiceStatus=" + invoiceStatus)
                .toString();
    }
}