package agency.ui;

import agency.model.Customer;
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
import java.util.List;

public class CustomerPanel extends JPanel implements RefreshablePanel {
    private final JFrame parent;
    private final CustomerService customerService;
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField idField = new JTextField(5);
    private final JTextField nameField = new JTextField(12);
    private final JTextField addressField = new JTextField(12);
    private final JTextField phoneField = new JTextField(10);

    public CustomerPanel(JFrame parent, CustomerService customerService) {
        this.parent = parent;
        this.customerService = customerService;
        this.model = new DefaultTableModel(new Object[]{"ID", "Name", "Address", "Phone", "Status"}, 0);
        this.table = new JTable(model);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFormPanel(), BorderLayout.SOUTH);
        refreshData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        panel.add(new JLabel("ID"));
        panel.add(idField);
        panel.add(new JLabel("Name"));
        panel.add(nameField);
        panel.add(new JLabel("Address"));
        panel.add(addressField);
        panel.add(new JLabel("Phone"));
        panel.add(phoneField);
        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);

        addBtn.addActionListener(e -> onAdd());
        updateBtn.addActionListener(e -> onUpdate());
        deleteBtn.addActionListener(e -> onDelete());
        return panel;
    }

    private void onAdd() {
        try {
            customerService.addCustomer(nameField.getText().trim(), addressField.getText().trim(), phoneField.getText().trim());
            DialogUtil.info(parent, "Customer added successfully.");
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, ex.getMessage());
        }
    }

    private void onUpdate() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            customerService.updateCustomer(id, nameField.getText().trim(), addressField.getText().trim(), phoneField.getText().trim());
            DialogUtil.info(parent, "Customer updated successfully.");
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, ex.getMessage());
        }
    }

    private void onDelete() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            customerService.deleteCustomer(id);
            DialogUtil.info(parent, "Customer deleted successfully.");
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, ex.getMessage());
        }
    }

    @Override
    public void refreshData() {
        model.setRowCount(0);
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            model.addRow(new Object[]{
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getPhone(),
                    customer.getStatus()
            });
        }
    }
}
