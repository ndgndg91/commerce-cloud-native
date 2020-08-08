package product;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.web.bind.annotation.*;
import product.api.*;
import product.request.ChangeProductStatusRequest;
import product.request.ProductCreateRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductController(
            CommandGateway commandGateway,
            QueryGateway queryGateway
    ) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/products")
    public Mono<Void> createProduct(@RequestBody ProductCreateRequest request) {
        commandGateway.send(
                new ProductCreateCommand(
                        request.getId(),
                        request.getName(),
                        request.getPrice(),
                        request.getQuantity(),
                        ProductStatus.valueOf(request.getStatus())
                )
        );

        return Mono.empty();
    }

    @PutMapping("/products")
    public Mono<Void> changeProductStatus(@RequestBody ChangeProductStatusRequest request){
        commandGateway.send(
                new ProductStatusChangeCommand(
                        request.getId(),
                        ProductStatus.valueOf(request.getStatus())
                )
        );
        return Mono.empty();
    }

    @GetMapping("/products/{id}")
    public Mono<ProductSummary> findProductById(
            @PathVariable long id
    ){

        ByIdProductQuery byIdProductQuery = new ByIdProductQuery(id);

        SubscriptionQueryResult<Optional<ProductSummary>, ProductSummary> byIdQueryResult = queryGateway.subscriptionQuery(byIdProductQuery,
                ResponseTypes.optionalInstanceOf(ProductSummary.class),
                ResponseTypes.instanceOf(ProductSummary.class)
        );

        Mono<Optional<ProductSummary>> optionalMono = byIdQueryResult.initialResult();
        return optionalMono.map(Optional::orElseThrow);
    }

    @GetMapping("/products")
    public Mono<List<ProductSummary>> findProducts(
            @RequestParam int offset,
            @RequestParam int limit
    ) {
        FetchProductsQuery fetchProductsQuery = new FetchProductsQuery(offset, limit);
        SubscriptionQueryResult<List<ProductSummary>, ProductSummary> fetchQueryResult =
                queryGateway.subscriptionQuery(fetchProductsQuery,
                        ResponseTypes.multipleInstancesOf(ProductSummary.class),
                        ResponseTypes.instanceOf(ProductSummary.class));
        return fetchQueryResult.initialResult();
    }
}
