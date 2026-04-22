package agency.model;

import java.time.LocalDate;

public class Payment {
    private final int id;
    private final int billId;
    private final int customerId;
    private final double amount;
    private final PaymentMode mode;
    private final String referenceNo;
    private final LocalDate paymentDate;

    public Payment(int id, int billId, int customerId, double amount, PaymentMode mode, String referenceNo, LocalDate paymentDate) {
        this.id = id;
        this.billId = billId;
        this.customerId = customerId;
        this.amount = amount;
        this.mode = mode;
        this.referenceNo = referenceNo;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public int getBillId() {
        return billId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
}
