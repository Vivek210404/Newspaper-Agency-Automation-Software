package agency.store;

import agency.model.Bill;
import agency.model.Customer;
import agency.model.DeliveryPerson;
import agency.model.Payment;
import agency.model.Publication;
import agency.model.Subscription;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private final List<Customer> customers = new ArrayList<Customer>();
    private final List<Publication> publications = new ArrayList<Publication>();
    private final List<Subscription> subscriptions = new ArrayList<Subscription>();
    private final List<Bill> bills = new ArrayList<Bill>();
    private final List<Payment> payments = new ArrayList<Payment>();
    private final List<DeliveryPerson> deliveryPeople = new ArrayList<DeliveryPerson>();

    private int customerSeq = 1;
    private int publicationSeq = 1;
    private int subscriptionSeq = 1;
    private int billSeq = 1;
    private int paymentSeq = 1;
    private int deliveryPersonSeq = 1;

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<DeliveryPerson> getDeliveryPeople() {
        return deliveryPeople;
    }

    public int nextCustomerId() {
        return customerSeq++;
    }

    public int nextPublicationId() {
        return publicationSeq++;
    }

    public int nextSubscriptionId() {
        return subscriptionSeq++;
    }

    public int nextBillId() {
        return billSeq++;
    }

    public int nextPaymentId() {
        return paymentSeq++;
    }

    public int nextDeliveryPersonId() {
        return deliveryPersonSeq++;
    }
}
