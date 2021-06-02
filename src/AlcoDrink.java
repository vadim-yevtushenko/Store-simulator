public class AlcoDrink extends Drink{
    private String alcoholDegree;

    public AlcoDrink(String name, double purchasePrice, String classification, String volume, String alcoholDegree, int availability) {
        super(name, purchasePrice, classification, volume, availability);
        this.alcoholDegree = alcoholDegree;
    }

    public String getAlcoholDegree() {
        return alcoholDegree;
    }

    public void setAlcoholDegree(String alcoholDegree) {
        this.alcoholDegree = alcoholDegree;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%d", super.getName(), super.getPurchasePrice(), super.getClassification(), super.getVolume(), alcoholDegree, super.getAvailability());

    }
}