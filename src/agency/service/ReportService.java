package agency.service;

import agency.model.Bill;
import agency.model.Customer;
import agency.model.CustomerStatus;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ReportService {
    private final BillingService billingService;
    private final CustomerService customerService;
    private final SubscriptionService subscriptionService;

    public ReportService(BillingService billingService, CustomerService customerService, SubscriptionService subscriptionService) {
        this.billingService = billingService;
        this.customerService = customerService;
        this.subscriptionService = subscriptionService;
    }

    // SRS-CORE-06: Due tracking (1 month reminder, 2 months deactivate)
    public List<String[]> applyDueRulesAndGetStatusRows(YearMonth currentMonth) {
        List<String[]> rows = new ArrayList<String[]>();
        for (Customer customer : customerService.getAllCustomers()) {
            int overdueMonths = calculateOverdueMonths(customer.getId(), currentMonth);
            String action = "None";

            if (overdueMonths >= 2) {
                customerService.setCustomerStatus(customer.getId(), CustomerStatus.INACTIVE);
                subscriptionService.deactivateSubscriptionsByCustomer(customer.getId());
                action = "Customer Deactivated";
            } else if (overdueMonths >= 1) {
                markCustomerBillsReminder(customer.getId());
                action = "Reminder Sent";
            }

            rows.add(new String[]{
                    String.valueOf(customer.getId()),
                    customer.getName(),
                    String.valueOf(overdueMonths),
                    customer.getStatus().name(),
                    action
            });
        }
        return rows;
    }

    private void markCustomerBillsReminder(int customerId) {
        for (Bill bill : billingService.getAllBills()) {
            if (bill.getCustomerId() == customerId && bill.getOutstanding() > 0) {
                bill.setReminderSent(true);
            }
        }
    }

    private int calculateOverdueMonths(int customerId, YearMonth currentMonth) {
        int maxMonths = 0;
        for (Bill bill : billingService.getAllBills()) {
            if (bill.getCustomerId() != customerId || bill.getOutstanding() <= 0) {
                continue;
            }
            int months = Math.max(0, (currentMonth.getYear() - bill.getBillMonth().getYear()) * 12
                    + currentMonth.getMonthValue() - bill.getBillMonth().getMonthValue());
            if (months > maxMonths) {
                maxMonths = months;
            }
        }
        return maxMonths;
    }
}
