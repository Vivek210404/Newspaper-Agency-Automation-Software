package agency.ui;

import agency.model.Bill;
import agency.model.Customer;
import agency.service.BillingService;
import agency.service.CustomerService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.YearMonth;

public class BillingPanel extends JPanel implements RefreshablePanel {
    private final JFrame parent;
    private final BillingService billingService;
    private final CustomerService customerService;
    private final DefaultTableModel model;
    private final JTextField monthField = new JTextField(7);

    public BillingPanel(JFrame parent, BillingService billingService, CustomerService customerService) {
        this.parent = parent;
        this.billingService = billingService;
        this.customerService = customerService;
        this.model = new DefaultTableModel(new Object[]{"Bill ID", "Customer", "Month", "Total", "Paid", "Outstanding", "Reminder"}, 0);

        setLayout(new BorderLayout());
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        add(buildControlPanel(), BorderLayout.SOUTH);
        refreshData();
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton generateBtn = new JButton("Generate Bills (YYYY-MM)");
        panel.add(new JLabel("Month"));
        panel.add(monthField);
        panel.add(generateBtn);
        generateBtn.addActionListener(e -> onGenerate());
        return panel;
    }

    private void onGenerate() {
        try {
            YearMonth ym = YearMonth.parse(monthField.getText().trim());
            int count = billingService.generateMonthlyBills(ym).size();
            DialogUtil.info(parent, count + " bill(s) generated.");
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, "Enter valid month format YYYY-MM.");
        }
    }

    @Override
    public void refreshData() {
        model.setRowCount(0);
        for (Bill bill : billingService.getAllBills()) {
            Customer customer = customerService.findById(bill.getCustomerId());
            model.addRow(new Object[]{
                    bill.getId(),
                    customer != null ? customer.getName() : "N/A",
                    bill.getBillMonth(),
                    String.format("%.2f", bill.getTotalAmount()),
                    String.format("%.2f", bill.getPaidAmount()),
                    String.format("%.2f", bill.getOutstanding()),
                    bill.isReminderSent()
            });
        }
    }
}
