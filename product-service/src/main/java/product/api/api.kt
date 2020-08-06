package product.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

data class ProductCreateCommand(@TargetAggregateIdentifier val id: Long, val name: String, val price: Double, var status: ProductStatus)
data class ProductCreatedEvent(val id: Long, val name: String, val price: Double, var status: ProductStatus)
data class ProductStatusChangeCommand(@TargetAggregateIdentifier val id: Long, val status: ProductStatus)
data class ProductStatusChangedEvent(val id: Long, val status: ProductStatus)
data class ProductDeleteCommand(@TargetAggregateIdentifier val id: Long)
data class ProductDeletedEvent(val id: Long)

enum class ProductStatus() { PUBLIC, PRIVATE, SOLD_OUT }

@Entity
@NamedQueries(
        NamedQuery(name = "ProductSummary.fetch",
                query = "SELECT p FROM ProductSummary p")
)
data class ProductSummary(@Id var id: Long, var name: String, var price: Double, var status: String) {
    constructor() : this(0, "", 0.0, "PUBLIC")
}

data class FetchProductsQuery(val offset: Int, val limit: Int)