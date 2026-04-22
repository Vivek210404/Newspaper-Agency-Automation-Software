package agency.service;

import agency.model.Bill;
import agency.model.Customer;
import agency.model.Subscription;
import agency.store.DataStore;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingService {
    private final DataStore store;
    private final CustomerService customerService;
    private final SubscriptionService subscriptionService;

    public BillingService(DataStore store, CustomerService customerService, SubscriptionService subscriptionService) {
        this.store = store;
        this.customerService = customerService;
        this.subscriptionService = subscriptionService;
    }

    // SRS-CORE-04: Monthly Bill Generation
    public List<Bill> generateMonthlyBills(YearMonth month) {
        Map<Integer, Double> customerTotals = new HashMap<Integer, Double>();
        for (Subscription subscription : store.getSubscriptions()) {
            if (!subscription.isActive()) {
                continue;
            }
            Customer customer = customerService.findById(subscription.getCustomerId());
            if (customer == null) {
                continue;
            }
            double price = subscriptionService.findPublicationById(subscription.getPublicationId()).getMonthlyPrice();
            Double current = customerTotals.get(customer.getId());
            customerTotals.put(customer.getId(), (current == null ? 0.0 : current) + price);
        }

        List<Bill> newBills = new ArrayList<Bill>();
        for (Map.Entry<Integer, Double> entry : customerTotals.entrySet()) {
            if (findBill(entry.getKey(), month) != null) {
                continue;
            }
            Bill bill = new Bill(store.nextBillId(), entry.getKey(), month, entry.getValue(), LocalDate.now());
            store.getBills().add(bill);
            newBills.add(bill);
        }
        return newBills;
    }

    public Bill findById(int billId) {
        for (Bill bill : store.getBills()) {
            if (bill.getId() == billId) {
                return bill;
            }
        }
        return null;
    }

    public Bill findBill(int customerId, YearMonth month) {
        for (Bill bill : store.getBills()) {
            if (bill.getCustomerId() == customerId && bill.getBillMonth().equals(month)) {
                return bill;
            }
        }
        return null;
    }

    public List<Bill> getAllBills() {
        return new ArrayList<Bill>(store.getBills());
    }
}
