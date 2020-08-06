package product.request;

public class ChangeProductStatusRequest {
    private long id;
    private String status;

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
