package agency.service;

import agency.model.Publication;
import agency.model.PublicationType;
import agency.model.Subscription;
import agency.store.DataStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionService {
    private final DataStore store;

    public SubscriptionService(DataStore store) {
        this.store = store;
    }

    // SRS-CORE-02: Subscription Management (newspapers/magazines)
    public Publication addPublication(String title, PublicationType type, double monthlyPrice) {
        Publication publication = new Publication(store.nextPublicationId(), title, type, monthlyPrice);
        store.getPublications().add(publication);
        return publication;
    }

    // SRS-CORE-02: Subscription Management (newspapers/magazines)
    public Subscription addSubscription(int customerId, int publicationId, int deliveryPersonId) {
        Subscription subscription = new Subscription(
                store.nextSubscriptionId(),
                customerId,
                publicationId,
                deliveryPersonId,
                LocalDate.now(),
                true
        );
        store.getSubscriptions().add(subscription);
        return subscription;
    }

    public void deactivateSubscriptionsByCustomer(int customerId) {
        for (Subscription subscription : store.getSubscriptions()) {
            if (subscription.getCustomerId() == customerId) {
                subscription.setActive(false);
            }
        }
    }

    public List<Publication> getAllPublications() {
        return new ArrayList<Publication>(store.getPublications());
    }

    public List<Subscription> getAllSubscriptions() {
        return new ArrayList<Subscription>(store.getSubscriptions());
    }

    public Publication findPublicationById(int id) {
        for (Publication publication : store.getPublications()) {
            if (publication.getId() == id) {
                return publication;
            }
        }
        return null;
    }
}
