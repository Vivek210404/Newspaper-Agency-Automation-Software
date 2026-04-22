package agency.service;

import agency.model.Customer;
import agency.model.CustomerStatus;
import agency.store.DataStore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerService {
    private final DataStore store;

    public CustomerService(DataStore store) {
        this.store = store;
    }

    // SRS-CORE-01: Customer Management Add/Update/Delete
    public Customer addCustomer(String name, String address, String phone) {
        Customer customer = new Customer(store.nextCustomerId(), name, address, phone, CustomerStatus.ACTIVE);
        store.getCustomers().add(customer);
        return customer;
    }

    // SRS-CORE-01: Customer Management Add/Update/Delete
    public void updateCustomer(int id, String name, String address, String phone) {
        Customer customer = findById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found.");
        }
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
    }

    // SRS-CORE-01: Customer Management Add/Update/Delete
    public void deleteCustomer(int id) {
        Iterator<Customer> iterator = store.getCustomers().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<Customer>(store.getCustomers());
    }

    public Customer findById(int id) {
        for (Customer customer : store.getCustomers()) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public void setCustomerStatus(int customerId, CustomerStatus status) {
        Customer customer = findById(customerId);
        if (customer != null) {
            customer.setStatus(status);
        }
    }
}
