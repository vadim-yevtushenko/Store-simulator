public abstract class Drink {
    private String name;
    private double purchasePrice;
    private String classification;
    private String volume;
    private int availability;

    public Drink(String name, double purchasePrice, String classification, String volume, int availability) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.classification = classification;
        this.volume = volume;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
