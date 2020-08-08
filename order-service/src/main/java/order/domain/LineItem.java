package order.domain;

import java.util.StringJoiner;

public class LineItem {

    private Long id;
    private String name;
    private Double price;
    private Double tax;

    private LineItem(){}

    public LineItem(Long productId, String name, double price) {
        this.id = productId;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void calculateTax(double percent){
        this.tax = price * (percent/100);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LineItem.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("id='" + id + "'")
                .add("price=" + price)
                .add("tax=" + tax)
                .toString();
    }
}
