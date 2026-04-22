package agency.model;

import java.time.LocalDate;
import java.time.YearMonth;

public class Bill {
    private final int id;
    private final int customerId;
    private final YearMonth billMonth;
    private final double totalAmount;
    private double paidAmount;
    private final LocalDate generatedOn;
    private boolean reminderSent;

    public Bill(int id, int customerId, YearMonth billMonth, double totalAmount, LocalDate generatedOn) {
        this.id = id;
        this.customerId = customerId;
        this.billMonth = billMonth;
        this.totalAmount = totalAmount;
        this.generatedOn = generatedOn;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public YearMonth getBillMonth() {
        return billMonth;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void addPayment(double amount) {
        paidAmount += amount;
    }

    public double getOutstanding() {
        return Math.max(0.0, totalAmount - paidAmount);
    }

    public boolean isPaid() {
        return getOutstanding() == 0.0;
    }

    public LocalDate getGeneratedOn() {
        return generatedOn;
    }

    public boolean isReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }

    @Override
    public String toString() {
        return "Bill#" + id + " [" + billMonth + "] due=" + String.format("%.2f", getOutstanding());
    }
}
