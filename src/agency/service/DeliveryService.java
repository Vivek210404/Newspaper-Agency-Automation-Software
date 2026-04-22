package agency.service;

import agency.model.DeliveryPerson;
import agency.model.Publication;
import agency.model.Subscription;
import agency.store.DataStore;

import java.util.ArrayList;
import java.util.List;

public class DeliveryService {
    private static final double COMMISSION_RATE = 0.025;

    private final DataStore store;
    private final SubscriptionService subscriptionService;

    public DeliveryService(DataStore store, SubscriptionService subscriptionService) {
        this.store = store;
        this.subscriptionService = subscriptionService;
    }

    // SRS-CORE-03: Delivery tracking simulation
    // SRS-CORE-07: Delivery boy commission calculation (2.5%)
    public void simulateOneDeliveryCycle() {
        for (Subscription subscription : store.getSubscriptions()) {
            if (!subscription.isActive()) {
                continue;
            }
            DeliveryPerson person = findDeliveryPerson(subscription.getDeliveryPersonId());
            Publication publication = subscriptionService.findPublicationById(subscription.getPublicationId());
            if (person == null || publication == null) {
                continue;
            }
            person.incrementDeliveries();
            double perCycleValue = publication.getMonthlyPrice() / 30.0;
            person.addCommission(perCycleValue * COMMISSION_RATE);
        }
    }

    public DeliveryPerson findDeliveryPerson(int id) {
        for (DeliveryPerson person : store.getDeliveryPeople()) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public List<DeliveryPerson> getAllDeliveryPeople() {
        return new ArrayList<DeliveryPerson>(store.getDeliveryPeople());
    }
}
