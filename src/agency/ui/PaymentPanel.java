package agency.ui;

import agency.model.Bill;
import agency.model.Customer;
import agency.model.Payment;
import agency.model.PaymentMode;
import agency.service.BillingService;
import agency.service.CustomerService;
import agency.service.PaymentService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PaymentPanel extends JPanel implements RefreshablePanel {
    private final JFrame parent;
    private final BillingService billingService;
    private final PaymentService paymentService;
    private final CustomerService customerService;

    private final DefaultTableModel model;
    private final JComboBox<Bill> billCombo = new JComboBox<Bill>();
    private final JTextField amountField = new JTextField(7);
    private final JComboBox<PaymentMode> modeCombo = new JComboBox<PaymentMode>(PaymentMode.values());
    private final JTextField referenceField = new JTextField(8);

    public PaymentPanel(JFrame parent, BillingService billingService, PaymentService paymentService, CustomerService customerService) {
        this.parent = parent;
        this.billingService = billingService;
        this.paymentService = paymentService;
        this.customerService = customerService;
        this.model = new DefaultTableModel(new Object[]{"Payment ID", "Bill ID", "Customer", "Amount", "Mode", "Date"}, 0);

        setLayout(new BorderLayout());
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        add(buildControlPanel(), BorderLayout.SOUTH);
        refreshData();
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton recordBtn = new JButton("Record Payment");
        panel.add(new JLabel("Bill"));
        panel.add(billCombo);
        panel.add(new JLabel("Amount"));
        panel.add(amountField);
        panel.add(modeCombo);
        panel.add(new JLabel("Ref"));
        panel.add(referenceField);
        panel.add(recordBtn);
        recordBtn.addActionListener(e -> onRecordPayment());
        return panel;
    }

    private void onRecordPayment() {
        try {
            Bill bill = (Bill) billCombo.getSelectedItem();
            if (bill == null) {
                throw new IllegalArgumentException("No unpaid bill selected.");
            }
            double amount = Double.parseDouble(amountField.getText().trim());
            Payment payment = paymentService.recordPayment(
                    bill.getId(),
                    amount,
                    (PaymentMode) modeCombo.getSelectedItem(),
                    referenceField.getText().trim().isEmpty() ? "-" : referenceField.getText().trim()
            );
            JTextArea textArea = new JTextArea(paymentService.generateReceipt(payment));
            textArea.setEditable(false);
            DialogUtil.info(parent, "Payment recorded.");
            javax.swing.JOptionPane.showMessageDialog(parent, new JScrollPane(textArea), "Receipt", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, ex.getMessage());
        }
    }

    @Override
    public void refreshData() {
        billCombo.removeAllItems();
        for (Bill bill : billingService.getAllBills()) {
            if (bill.getOutstanding() > 0.0) {
                billCombo.addItem(bill);
            }
        }

        model.setRowCount(0);
        for (Payment payment : paymentService.getAllPayments()) {
            Customer customer = customerService.findById(payment.getCustomerId());
            model.addRow(new Object[]{
                    payment.getId(),
                    payment.getBillId(),
                    customer != null ? customer.getName() : "N/A",
                    String.format("%.2f", payment.getAmount()),
                    payment.getMode(),
                    payment.getPaymentDate()
            });
        }
    }
}
