package agency.model;

import java.time.LocalDate;

public class Subscription {
    private final int id;
    private final int customerId;
    private final int publicationId;
    private final int deliveryPersonId;
    private final LocalDate startDate;
    private boolean active;

    public Subscription(int id, int customerId, int publicationId, int deliveryPersonId, LocalDate startDate, boolean active) {
        this.id = id;
        this.customerId = customerId;
        this.publicationId = publicationId;
        this.deliveryPersonId = deliveryPersonId;
        this.startDate = startDate;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
