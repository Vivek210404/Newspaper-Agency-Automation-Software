package agency.model;

public class DeliveryPerson {
    private final int id;
    private String name;
    private String area;
    private int deliveriesCount;
    private double commissionEarned;

    public DeliveryPerson(int id, String name, String area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }

    public int getDeliveriesCount() {
        return deliveriesCount;
    }

    public void incrementDeliveries() {
        deliveriesCount++;
    }

    public double getCommissionEarned() {
        return commissionEarned;
    }

    public void addCommission(double commission) {
        commissionEarned += commission;
    }

    @Override
    public String toString() {
        return name + " - " + area;
    }
}
