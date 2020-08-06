package product.request;

public final class ProductCreateRequest{
    private long id;
    private String name;
    private Double price;
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

    public String getStatus() {
        return status;
    }
}