package agency.ui;

import agency.service.BillingService;
import agency.service.CustomerService;
import agency.service.DeliveryService;
import agency.service.PaymentService;
import agency.service.ReportService;
import agency.service.SubscriptionService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class MainDashboard extends JFrame {
    private static final String CUSTOMERS = "CUSTOMERS";
    private static final String SUBSCRIPTIONS = "SUBSCRIPTIONS";
    private static final String BILLING = "BILLING";
    private static final String PAYMENTS = "PAYMENTS";
    private static final String REPORTS = "REPORTS";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final Map<String, RefreshablePanel> panels = new HashMap<String, RefreshablePanel>();

    public MainDashboard(CustomerService customerService,
                         SubscriptionService subscriptionService,
                         BillingService billingService,
                         PaymentService paymentService,
                         DeliveryService deliveryService,
                         ReportService reportService) {
        setTitle("Newspaper Agency Automation Software");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 680));
        setLocationRelativeTo(null);

        JPanel navPanel = new JPanel(new java.awt.GridLayout(5, 1, 6, 6));
        JButton customersBtn = new JButton("Manage Customers");
        JButton subscriptionsBtn = new JButton("Manage Subscriptions");
        JButton billsBtn = new JButton("Generate Bills");
        JButton paymentsBtn = new JButton("Record Payments");
        JButton reportsBtn = new JButton("View Reports");

        navPanel.add(customersBtn);
        navPanel.add(subscriptionsBtn);
        navPanel.add(billsBtn);
        navPanel.add(paymentsBtn);
        navPanel.add(reportsBtn);
        navPanel.setPreferredSize(new Dimension(220, 680));

        CustomerPanel customerPanel = new CustomerPanel(this, customerService);
        SubscriptionPanel subscriptionPanel = new SubscriptionPanel(this, customerService, subscriptionService, deliveryService);
        BillingPanel billingPanel = new BillingPanel(this, billingService, customerService);
        PaymentPanel paymentPanel = new PaymentPanel(this, billingService, paymentService, customerService);
        ReportsPanel reportsPanel = new ReportsPanel(this, reportService, deliveryService);

        registerPanel(CUSTOMERS, customerPanel);
        registerPanel(SUBSCRIPTIONS, subscriptionPanel);
        registerPanel(BILLING, billingPanel);
        registerPanel(PAYMENTS, paymentPanel);
        registerPanel(REPORTS, reportsPanel);

        customersBtn.addActionListener(e -> showPanel(CUSTOMERS));
        subscriptionsBtn.addActionListener(e -> showPanel(SUBSCRIPTIONS));
        billsBtn.addActionListener(e -> showPanel(BILLING));
        paymentsBtn.addActionListener(e -> showPanel(PAYMENTS));
        reportsBtn.addActionListener(e -> showPanel(REPORTS));

        add(navPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        showPanel(CUSTOMERS);
    }

    private void registerPanel(String key, RefreshablePanel panel) {
        panels.put(key, panel);
        contentPanel.add((JPanel) panel, key);
    }

    private void showPanel(String key) {
        RefreshablePanel panel = panels.get(key);
        if (panel != null) {
            panel.refreshData();
        }
        cardLayout.show(contentPanel, key);
    }
}
