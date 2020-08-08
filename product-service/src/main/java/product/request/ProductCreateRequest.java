package product.request;

public final class ProductCreateRequest{
    private long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String status;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}