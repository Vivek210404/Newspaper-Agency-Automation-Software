package agency.service;

import agency.model.Bill;
import agency.model.Payment;
import agency.model.PaymentMode;
import agency.store.DataStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private final DataStore store;
    private final BillingService billingService;
    private final CustomerService customerService;

    public PaymentService(DataStore store, BillingService billingService, CustomerService customerService) {
        this.store = store;
        this.billingService = billingService;
        this.customerService = customerService;
    }

    // SRS-CORE-05: Payment entry (cash/cheque)
    public Payment recordPayment(int billId, double amount, PaymentMode mode, String referenceNo) {
        Bill bill = billingService.findById(billId);
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
        if (amount > bill.getOutstanding()) {
            throw new IllegalArgumentException("Amount exceeds outstanding bill due.");
        }

        bill.addPayment(amount);
        Payment payment = new Payment(
                store.nextPaymentId(),
                bill.getId(),
                bill.getCustomerId(),
                amount,
                mode,
                referenceNo,
                LocalDate.now()
        );
        store.getPayments().add(payment);
        return payment;
    }

    // SRS-CORE-05: Receipt generation
    public String generateReceipt(Payment payment) {
        String customerName = customerService.findById(payment.getCustomerId()).getName();
        StringBuilder builder = new StringBuilder();
        builder.append("===== PAYMENT RECEIPT =====\n");
        builder.append("Receipt ID: ").append(payment.getId()).append('\n');
        builder.append("Customer: ").append(customerName).append('\n');
        builder.append("Bill ID: ").append(payment.getBillId()).append('\n');
        builder.append("Date: ").append(payment.getPaymentDate()).append('\n');
        builder.append("Mode: ").append(payment.getMode()).append('\n');
        builder.append("Reference: ").append(payment.getReferenceNo()).append('\n');
        builder.append("Amount: ").append(String.format("%.2f", payment.getAmount())).append('\n');
        builder.append("===========================");
        return builder.toString();
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<Payment>(store.getPayments());
    }
}
