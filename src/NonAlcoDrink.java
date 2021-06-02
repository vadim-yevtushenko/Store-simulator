public class NonAlcoDrink extends Drink{
    private String structure;

    public NonAlcoDrink(String name, double purchasePrice, String classification, String volume, String structure, int availability) {
        super(name, purchasePrice, classification, volume, availability);
        this.structure = structure;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%d", super.getName(), super.getPurchasePrice(), super.getClassification(), super.getVolume(), structure, super.getAvailability());
    }
}