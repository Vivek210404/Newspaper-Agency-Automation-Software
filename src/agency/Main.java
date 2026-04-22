package agency;

import agency.service.BillingService;
import agency.service.CustomerService;
import agency.service.DeliveryService;
import agency.service.PaymentService;
import agency.service.ReportService;
import agency.service.SubscriptionService;
import agency.store.DataStore;
import agency.ui.MainDashboard;
import agency.util.SampleDataLoader;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        final DataStore store = new DataStore();
        SampleDataLoader.load(store);

        final CustomerService customerService = new CustomerService(store);
        final SubscriptionService subscriptionService = new SubscriptionService(store);
        final BillingService billingService = new BillingService(store, customerService, subscriptionService);
        final PaymentService paymentService = new PaymentService(store, billingService, customerService);
        final DeliveryService deliveryService = new DeliveryService(store, subscriptionService);
        final ReportService reportService = new ReportService(billingService, customerService, subscriptionService);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainDashboard dashboard = new MainDashboard(
                        customerService,
                        subscriptionService,
                        billingService,
                        paymentService,
                        deliveryService,
                        reportService
                );
                dashboard.setVisible(true);
            }
        });
    }
}
