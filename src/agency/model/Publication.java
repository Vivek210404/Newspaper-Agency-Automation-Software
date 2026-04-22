package agency.model;

public class Publication {
    private final int id;
    private String title;
    private PublicationType type;
    private double monthlyPrice;

    public Publication(int id, String title, PublicationType type, double monthlyPrice) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.monthlyPrice = monthlyPrice;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PublicationType getType() {
        return type;
    }

    public void setType(PublicationType type) {
        this.type = type;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    @Override
    public String toString() {
        return title + " (" + type + ")";
    }
}
