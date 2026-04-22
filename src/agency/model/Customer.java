package agency.model;

public class Customer {
    private final int id;
    private String name;
    private String address;
    private String phone;
    private CustomerStatus status;

    public Customer(int id, String name, String address, String phone, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " (#" + id + ")";
    }
}
