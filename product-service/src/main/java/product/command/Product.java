package product.command;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.lang.invoke.MethodHandles;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import product.api.ProductCreateCommand;
import product.api.ProductCreatedEvent;
import product.api.ProductDeleteCommand;
import product.api.ProductDeletedEvent;
import product.api.ProductStatus;
import product.api.ProductStatusChangeCommand;
import product.api.ProductStatusChangedEvent;

@Aggregate
public class Product {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private ProductStatus status;

    @CommandHandler
    public Product(ProductCreateCommand cmd) {
        log.debug("ProductCreateCommand {}", cmd);
        if (cmd.getPrice() <= 0) {
            throw new IllegalArgumentException("price <= 0");
        }

        apply(new ProductCreatedEvent(cmd.getId(), cmd.getName(), cmd.getPrice(), cmd.getQuantity(), cmd.getStatus()));
    }

    @CommandHandler
    public void handle(ProductStatusChangeCommand cmd) {
        log.debug("ProductStatusChangeCommand {}", cmd);
        apply(new ProductStatusChangedEvent(id, cmd.getStatus()));
    }

    @CommandHandler
    public void handle(ProductDeleteCommand cmd) {
        log.debug("ProductDeleteCommand {}", cmd);
        apply(new ProductDeletedEvent(id));
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("ProductCreatedEvent {}", evt);
        }

        id = evt.getId();
        name = evt.getName();
        price = evt.getPrice();
        status = evt.getStatus();
    }

    @EventSourcingHandler
    public void on(ProductStatusChangedEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("ProductStatusChangedEvent {}", evt);
        }

        status = evt.getStatus();
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("ProductDeletedEvent {}", evt);
        }

        AggregateLifecycle.markDeleted();
    }

    public Product() {
        // Required by Axon
        log.debug("Empty constructor invoked");
    }
}
