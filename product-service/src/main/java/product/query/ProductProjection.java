package product.query;

import java.lang.invoke.MethodHandles;
import javax.persistence.EntityManager;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import product.api.ProductCreatedEvent;
import product.api.ProductStatusChangedEvent;
import product.api.ProductSummary;

@Component
@Profile("query")
public class ProductProjection {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManager entityManager;

    public ProductProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        log.trace("projecting {}", event);
        /*
         * Update our read model by inserting the new card. This is done so that upcoming regular
         * (non-subscription) queries get correct data.
         */
        entityManager
            .persist(new ProductSummary(event.getId(), event.getName(), event.getPrice(), event.getStatus().name()));
    }

    @EventHandler
    public void on(ProductStatusChangedEvent event) {
        log.trace("projecting {}", event);
        /*
         * Update our read model by updating the existing card. This is done so that upcoming regular
         * (non-subscription) queries get correct data.
         */
        ProductSummary summary = entityManager.find(ProductSummary.class, event.getId());
        summary.setStatus(event.getStatus().name());
    }
}
