package order.domain.order;

public class LineItem {

    private String name;
    private String productId;
    private Integer quantity;
    private Double price;
    private Double tax;

    public LineItem(String name, String productId, int quantity, double price, double tax) {
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getTax() {
        return tax;
    }
}
