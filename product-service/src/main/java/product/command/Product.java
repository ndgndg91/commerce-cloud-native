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
import product.api.*;

@Aggregate
public class Product {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private Long id;
    private String name;
    private Double price;
    private Long quantity;
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

    @CommandHandler
    public void handle(ProductQuantityMinusCommand cmd) {
        if (cmd.getQuantity() < 0) {
            throw new IllegalArgumentException("negative parameter is illegal");
        }

        apply(new ProductQuantityMinusCommand(cmd.getId(), cmd.getQuantity()));
    }

    @CommandHandler
    public void handle(ProductQuantityPlusCommand cmd) {
        if (cmd.getQuantity() < 0) {
            throw new IllegalArgumentException("negative parameter is illegal");
        }

        apply(new ProductQuantityPlusCommand(cmd.getId(), cmd.getQuantity()));
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

    @EventSourcingHandler
    public void on(ProductQuantityDeductedEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("ProductQuantityDeductedEvent {}", evt);
        }

        long finalQuantity = quantity - evt.getQuantity();
        finalQuantity = finalQuantity < 0 ? 0 : finalQuantity;
        quantity = finalQuantity;
    }

    @EventSourcingHandler
    public void on(ProductQuantityIncreasedEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("ProductQuantityIncreasedEvent {}", evt);
        }

        quantity = quantity + evt.getQuantity();
    }

    public Product() {
        // Required by Axon
        log.debug("Empty constructor invoked");
    }
}
