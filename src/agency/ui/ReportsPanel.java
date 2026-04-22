package agency.ui;

import agency.model.DeliveryPerson;
import agency.service.DeliveryService;
import agency.service.ReportService;

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

public class ReportsPanel extends JPanel implements RefreshablePanel {
    private final JFrame parent;
    private final ReportService reportService;
    private final DeliveryService deliveryService;
    private final DefaultTableModel dueModel;
    private final DefaultTableModel commissionModel;
    private final JTextField monthField = new JTextField(7);

    public ReportsPanel(JFrame parent, ReportService reportService, DeliveryService deliveryService) {
        this.parent = parent;
        this.reportService = reportService;
        this.deliveryService = deliveryService;
        this.dueModel = new DefaultTableModel(new Object[]{"Customer ID", "Name", "Overdue Months", "Status", "Action"}, 0);
        this.commissionModel = new DefaultTableModel(new Object[]{"Delivery Person", "Area", "Deliveries", "Commission"}, 0);

        setLayout(new BorderLayout());
        add(buildTopPanel(), BorderLayout.NORTH);

        JPanel tables = new JPanel(new java.awt.GridLayout(2, 1));
        tables.add(new JScrollPane(new JTable(dueModel)));
        tables.add(new JScrollPane(new JTable(commissionModel)));
        add(tables, BorderLayout.CENTER);
        refreshData();
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton dueBtn = new JButton("Apply Due Tracking");
        JButton deliveryBtn = new JButton("Simulate Delivery Cycle");
        panel.add(new JLabel("Current Month"));
        panel.add(monthField);
        panel.add(dueBtn);
        panel.add(deliveryBtn);
        dueBtn.addActionListener(e -> onDueTracking());
        deliveryBtn.addActionListener(e -> onDeliverySimulation());
        return panel;
    }

    private void onDueTracking() {
        try {
            YearMonth currentMonth = YearMonth.parse(monthField.getText().trim());
            dueModel.setRowCount(0);
            for (String[] row : reportService.applyDueRulesAndGetStatusRows(currentMonth)) {
                dueModel.addRow(row);
            }
            DialogUtil.info(parent, "Due tracking completed.");
        } catch (Exception ex) {
            DialogUtil.error(parent, "Use valid month format YYYY-MM.");
        }
    }

    private void onDeliverySimulation() {
        deliveryService.simulateOneDeliveryCycle();
        refreshCommissionData();
        DialogUtil.info(parent, "Delivery cycle simulated.");
    }

    private void refreshCommissionData() {
        commissionModel.setRowCount(0);
        for (DeliveryPerson person : deliveryService.getAllDeliveryPeople()) {
            commissionModel.addRow(new Object[]{
                    person.getName(),
                    person.getArea(),
                    person.getDeliveriesCount(),
                    String.format("%.2f", person.getCommissionEarned())
            });
        }
    }

    @Override
    public void refreshData() {
        refreshCommissionData();
    }
}
