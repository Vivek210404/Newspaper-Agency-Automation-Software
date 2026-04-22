package agency.util;

import agency.model.Customer;
import agency.model.CustomerStatus;
import agency.model.DeliveryPerson;
import agency.model.Publication;
import agency.model.PublicationType;
import agency.model.Subscription;
import agency.store.DataStore;

import java.time.LocalDate;

public final class SampleDataLoader {
    private SampleDataLoader() {
    }

    public static void load(DataStore store) {
        DeliveryPerson dp1 = new DeliveryPerson(store.nextDeliveryPersonId(), "Ravi", "North Zone");
        DeliveryPerson dp2 = new DeliveryPerson(store.nextDeliveryPersonId(), "Aman", "South Zone");
        store.getDeliveryPeople().add(dp1);
        store.getDeliveryPeople().add(dp2);

        Customer c1 = new Customer(store.nextCustomerId(), "Anita Sharma", "12 Lake Road", "9988776655", CustomerStatus.ACTIVE);
        Customer c2 = new Customer(store.nextCustomerId(), "Vivek Rao", "44 MG Road", "9876501234", CustomerStatus.ACTIVE);
        store.getCustomers().add(c1);
        store.getCustomers().add(c2);

        Publication p1 = new Publication(store.nextPublicationId(), "Daily Times", PublicationType.NEWSPAPER, 180.0);
        Publication p2 = new Publication(store.nextPublicationId(), "City Chronicle", PublicationType.NEWSPAPER, 150.0);
        Publication p3 = new Publication(store.nextPublicationId(), "Tech Monthly", PublicationType.MAGAZINE, 240.0);
        store.getPublications().add(p1);
        store.getPublications().add(p2);
        store.getPublications().add(p3);

        store.getSubscriptions().add(new Subscription(
                store.nextSubscriptionId(), c1.getId(), p1.getId(), dp1.getId(), LocalDate.now().minusMonths(2), true));
        store.getSubscriptions().add(new Subscription(
                store.nextSubscriptionId(), c1.getId(), p3.getId(), dp2.getId(), LocalDate.now().minusMonths(1), true));
        store.getSubscriptions().add(new Subscription(
                store.nextSubscriptionId(), c2.getId(), p2.getId(), dp1.getId(), LocalDate.now().minusMonths(1), true));
    }
}
