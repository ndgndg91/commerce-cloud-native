package product.query;

import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import product.api.*;

@Component
public class ProductProjection {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManager entityManager;

    public ProductProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        log.trace("projecting {}", event);
        entityManager
            .persist(new ProductSummary(event.getId(), event.getName(), event.getPrice(), event.getStatus().name()));
    }

    @EventHandler
    public void on(ProductStatusChangedEvent event) {
        log.trace("projecting {}", event);
        ProductSummary summary = entityManager.find(ProductSummary.class, event.getId());
        summary.setStatus(event.getStatus().name());
    }

    @QueryHandler
    public List<ProductSummary> handle(FetchProductsQuery query) {
        log.trace("handling {}", query);
        TypedQuery<ProductSummary> jpaQuery = entityManager.createNamedQuery("ProductSummary.fetch", ProductSummary.class);
        jpaQuery.setFirstResult(query.getOffset());
        jpaQuery.setMaxResults(query.getLimit());
        return jpaQuery.getResultList();
    }

    @QueryHandler
    public ProductSummary handle(ByIdProductQuery query){
        log.trace("handling {}", query);
        TypedQuery<ProductSummary> jpaQuery = entityManager.createNamedQuery("ProductSummary.byId", ProductSummary.class);
        jpaQuery.setParameter("id", query.getId());
        return jpaQuery.getSingleResult();
    }
}
