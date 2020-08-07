package order.domain.invoice;

import order.domain.account.Address;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, String> {

    Invoice findByBillingAddress(Address address);
}
