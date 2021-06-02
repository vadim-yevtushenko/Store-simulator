import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Report {
    private List<Drink> drinkList;
    private Map<Drink, Integer> numSells;
    private Map<Drink, Integer> numAddPurchase;
    private double sumSells;
    private String filePath = ".\\src\\may\\simstore\\saves\\report.txt";

    public Report(List<Drink> drinkList) {
        this.drinkList = drinkList;
        numSells = new HashMap<>();
        numAddPurchase = new HashMap<>();
        fillMaps();
    }

    private void fillMaps(){
        for (Drink drink : drinkList) {
            numSells.put(drink,0);
            numAddPurchase.put(drink,0);
        }
    }

    public void makeReport(){
        List<String> report = new ArrayList<>();
        String separator1 = "__________________________________________";
        String separator2 = "##########################################";
        report.add("Number of items sold for each item:".toUpperCase(Locale.ROOT));
        report.add(separator1);
        for (Map.Entry<Drink, Integer> pair : numSells.entrySet()) {
            report.add(String.format("%-25s %4s : %d", pair.getKey().getName(), pair.getKey().getVolume(), pair.getValue()));
        }
        report.add(separator2);
        report.add("Quantity of additional purchased goods for each item:".toUpperCase(Locale.ROOT));
        report.add(separator1);
        for (Map.Entry<Drink, Integer> pair : numAddPurchase.entrySet()) {
            report.add(String.format("%-25s %4s : %d", pair.getKey().getName(), pair.getKey().getVolume(), pair.getValue()));
        }
        report.add(separator2);
        double sumPurchase = getSumPurchase();
        report.add(String.format("Shop profit from sales: %.2f",(sumSells - sumPurchase)));
        report.add(separator2);
        report.add(String.format("Funds spent on additional purchases of goods: %.2f", getSumAddPurchase()));
        report.add(separator2);
        saveToFile(report);
    }

    private double getSumPurchase(){
        double sum = 0;
        for (Map.Entry<Drink, Integer> pair : numSells.entrySet()) {
            for (int i = 0; i < pair.getValue(); i++) {
                sum += pair.getKey().getPurchasePrice();
            }
        }
        return sum;
    }

    private double getSumAddPurchase(){
        double sum = 0;
        for (Map.Entry<Drink, Integer> pair : numAddPurchase.entrySet()) {
            for (int i = 0; i < pair.getValue(); i++) {
                sum += pair.getKey().getPurchasePrice();
            }
        }
        return sum;
    }

    public void saveToFile(List<String> report){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String s : report) {
                writer.write(s);
                writer.write("\n");
            }
        }catch (IOException e){

        }
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }

    public Map<Drink, Integer> getNumSells() {
        return numSells;
    }

    public void setNumSells(Map<Drink, Integer> numSells) {
        this.numSells = numSells;
    }

    public Map<Drink, Integer> getNumAddPurchase() {
        return numAddPurchase;
    }

    public void setNumAddPurchase(Map<Drink, Integer> numAddPurchase) {
        this.numAddPurchase = numAddPurchase;
    }

    public double getSumSells() {
        return sumSells;
    }

    public void setSumSells(double sumSells) {
        this.sumSells = sumSells;
    }
}
