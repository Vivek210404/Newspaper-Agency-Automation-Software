package agency.ui;

import agency.model.Customer;
import agency.model.DeliveryPerson;
import agency.model.Publication;
import agency.model.PublicationType;
import agency.model.Subscription;
import agency.service.CustomerService;
import agency.service.DeliveryService;
import agency.service.SubscriptionService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class SubscriptionPanel extends JPanel implements RefreshablePanel {
    private final JFrame parent;
    private final CustomerService customerService;
    private final SubscriptionService subscriptionService;
    private final DeliveryService deliveryService;
    private final DefaultTableModel model;
    private final JTable table;

    private final JComboBox<Customer> customerCombo = new JComboBox<Customer>();
    private final JComboBox<Publication> publicationCombo = new JComboBox<Publication>();
    private final JComboBox<DeliveryPerson> deliveryCombo = new JComboBox<DeliveryPerson>();
    private final JTextField publicationTitleField = new JTextField(10);
    private final JTextField publicationPriceField = new JTextField(6);
    private final JComboBox<PublicationType> publicationTypeCombo = new JComboBox<PublicationType>(PublicationType.values());

    public SubscriptionPanel(JFrame parent, CustomerService customerService, SubscriptionService subscriptionService, DeliveryService deliveryService) {
        this.parent = parent;
        this.customerService = customerService;
        this.subscriptionService = subscriptionService;
        this.deliveryService = deliveryService;
        this.model = new DefaultTableModel(new Object[]{"Sub ID", "Customer", "Publication", "Delivery Person", "Active"}, 0);
        this.table = new JTable(model);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildActionPanel(), BorderLayout.SOUTH);
        refreshData();
    }

    private JPanel buildActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addPublicationBtn = new JButton("Add Publication");
        JButton addSubscriptionBtn = new JButton("Add Subscription");

        panel.add(new JLabel("Publication"));
        panel.add(publicationTitleField);
        panel.add(publicationTypeCombo);
        panel.add(new JLabel("Price"));
        panel.add(publicationPriceField);
        panel.add(addPublicationBtn);
        panel.add(new JLabel("Customer"));
        panel.add(customerCombo);
        panel.add(new JLabel("Pub"));
        panel.add(publicationCombo);
        panel.add(new JLabel("Delivery"));
        panel.add(deliveryCombo);
        panel.add(addSubscriptionBtn);

        addPublicationBtn.addActionListener(e -> onAddPublication());
        addSubscriptionBtn.addActionListener(e -> onAddSubscription());
        return panel;
    }

    private void onAddPublication() {
        try {
            subscriptionService.addPublication(
                    publicationTitleField.getText().trim(),
                    (PublicationType) publicationTypeCombo.getSelectedItem(),
                    Double.parseDouble(publicationPriceField.getText().trim())
            );
            DialogUtil.info(parent, "Publication added.");
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, ex.getMessage());
        }
    }

    private void onAddSubscription() {
        try {
            Customer customer = (Customer) customerCombo.getSelectedItem();
            Publication publication = (Publication) publicationCombo.getSelectedItem();
            DeliveryPerson person = (DeliveryPerson) deliveryCombo.getSelectedItem();
            if (customer == null || publication == null || person == null) {
                throw new IllegalArgumentException("Select customer, publication and delivery person.");
            }
            subscriptionService.addSubscription(customer.getId(), publication.getId(), person.getId());
            DialogUtil.info(parent, "Subscription added.");
            refreshData();
        } catch (Exception ex) {
            DialogUtil.error(parent, ex.getMessage());
        }
    }

    @Override
    public void refreshData() {
        customerCombo.removeAllItems();
        for (Customer customer : customerService.getAllCustomers()) {
            customerCombo.addItem(customer);
        }
        publicationCombo.removeAllItems();
        for (Publication publication : subscriptionService.getAllPublications()) {
            publicationCombo.addItem(publication);
        }
        deliveryCombo.removeAllItems();
        for (DeliveryPerson person : deliveryService.getAllDeliveryPeople()) {
            deliveryCombo.addItem(person);
        }
        model.setRowCount(0);
        for (Subscription subscription : subscriptionService.getAllSubscriptions()) {
            Customer customer = customerService.findById(subscription.getCustomerId());
            Publication publication = subscriptionService.findPublicationById(subscription.getPublicationId());
            DeliveryPerson person = deliveryService.findDeliveryPerson(subscription.getDeliveryPersonId());
            model.addRow(new Object[]{
                    subscription.getId(),
                    customer != null ? customer.getName() : "N/A",
                    publication != null ? publication.getTitle() : "N/A",
                    person != null ? person.getName() : "N/A",
                    subscription.isActive()
            });
        }
    }
}
