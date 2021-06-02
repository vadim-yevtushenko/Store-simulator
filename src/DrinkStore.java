import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DrinkStore {
    private List<Drink> drinks;
    private List<Client> clients;
    private Report report;
    private Calendar calendar;
    private String filePath = ".\\src\\saves\\drinks.csv";

    public DrinkStore() {
        clients = new ArrayList<>();
        drinks = new ArrayList<>();
        loadDrinks();
        report = new Report(drinks);
    }

    public void startWork(Calendar calendar) {
        this.calendar = calendar;
        System.out.println(calendar.getTime());
        if ((calendar.get(Calendar.HOUR) > 7 && calendar.get(Calendar.AM_PM) == Calendar.AM)
                || (calendar.get(Calendar.HOUR) < 9 && calendar.get(Calendar.AM_PM) == Calendar.PM)) {
            int random = (int) (Math.random() * 10) + 1;
            for (int i = 0; i < random; i++) {
                clients.add(new Client());
            }
            for (Client client : clients) {
                sell(client);
            }
        }else {
            System.out.println("Work time: 8:00 - 21:00");
        }
        if (calendar.get(Calendar.HOUR) == 9 && calendar.get(Calendar.AM_PM) == Calendar.PM) {
            additionalPurchase();
        }
        clients.clear();
        System.out.println();

    }

    public void saveDrinks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Drink drink : drinks) {
                writer.write(drink.toString());
                writer.write("\n");
            }
        } catch (Exception e) {
        }
    }

    public void loadDrinks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.ready()) {
                drinks.add(fromStringToDrink(reader.readLine()));
            }
        } catch (Exception e) {
        }
    }

    private void sell(Client client) {
        for (int i = 0; i < client.getNumberProducts(); i++) {
            int randomDrink = (int) (Math.random() * drinks.size());
            if (i == 0 && drinks.get(randomDrink).getAvailability() == 0){
                do {
                    randomDrink = (int) (Math.random() * drinks.size());
                } while (drinks.get(randomDrink).getAvailability() == 0);
            }
            if (drinks.get(randomDrink).getAvailability() > 0) {
                double price;
                if (i == 0) {

                    price = getPrice(drinks.get(randomDrink));
                    double percent = (price - drinks.get(randomDrink).getPurchasePrice()) / drinks.get(randomDrink).getPurchasePrice() * 100;
                    System.out.printf("%s bought %s, price: %.2f, add: %d percent", client.getName(), drinks.get(randomDrink).getName(), price, Math.round(percent));
                    report.setSumSells(report.getSumSells() + price);
                    for (Map.Entry<Drink, Integer> pair : report.getNumSells().entrySet()) {
                        if (pair.getKey().equals(drinks.get(randomDrink))){
                            pair.setValue(pair.getValue() + 1);
                        }
                    }
                    System.out.println();
                } else {
                    price = drinks.get(randomDrink).getPurchasePrice() + (drinks.get(randomDrink).getPurchasePrice() / 100 * 7);
                    System.out.printf("%s bought %s, price: %.2f, add: 7 percent!", client.getName(), drinks.get(randomDrink).getName(), price);
                    report.setSumSells(report.getSumSells() + price);
                    for (Map.Entry<Drink, Integer> pair : report.getNumSells().entrySet()) {
                        if (pair.getKey().equals(drinks.get(randomDrink))){
                            pair.setValue(pair.getValue() + 1);
                        }
                    }
                    System.out.println();
                }
                drinks.get(randomDrink).setAvailability(drinks.get(randomDrink).getAvailability() - 1);
            } else {
                System.out.println(client.getName() + " " + drinks.get(randomDrink).getName() + " is not available");
            }
        }
    }

    private double getPrice(Drink drink) {
        double price;
        if (calendar.get(Calendar.HOUR) > 5 && calendar.get(Calendar.HOUR) < 8 && calendar.get(Calendar.AM_PM) == Calendar.PM) {
            price = drink.getPurchasePrice() + (drink.getPurchasePrice() / 100 * 8);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            price = drink.getPurchasePrice() + (drink.getPurchasePrice() / 100 * 15);
        } else {
            price = drink.getPurchasePrice() + (drink.getPurchasePrice() / 100 * 10);
        }
        return price;
    }

    private void additionalPurchase() {
        System.out.println("additional purchase");
        for (Drink drink : drinks) {
            if (drink.getAvailability() < 10) {
                drink.setAvailability(drink.getAvailability() + 150);
                for (Map.Entry<Drink, Integer> pair : report.getNumAddPurchase().entrySet()){
                    if (pair.getKey().equals(drink)){
                        pair.setValue(pair.getValue() + 150);
                    }
                }
            }
        }
    }

    private Drink fromStringToDrink(String str) {
        Drink drink;
        String[] strings = toArray(str);
        try {
            String name = strings[0];
            double purchasePrice = Double.parseDouble(strings[1]);
            String classification = strings[2];
            String volume = strings[3];
            String alcoholDegree = strings[4];
            int availability = Integer.parseInt(strings[5]);
            drink = new AlcoDrink(name, purchasePrice, classification, volume, alcoholDegree, availability);
        } catch (NumberFormatException e) {
            String name = strings[0];
            double purchasePrice = Double.parseDouble(strings[1]);
            String classification = strings[2];
            String volume = strings[3];
            String structure = strings[4];
            int availability = Integer.parseInt(strings[5]);
            drink = new NonAlcoDrink(name, purchasePrice, classification, volume, structure, availability);
        }
        return drink;
    }

    private String[] toArray(String str) {
        boolean concat = false;
        String[] strings = str.split(",");
        String[] result = new String[6];
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            if (string.startsWith("\"") && string.endsWith("\"")) {
                result[index++] = string;
            } else if (string.startsWith("\"")) {
                stringBuilder.append(string);
                concat = !concat;
            } else if (string.endsWith("\"")) {
                stringBuilder.append(",");
                stringBuilder.append(string);
                result[index++] = stringBuilder.toString();
                concat = !concat;
            } else if (concat) {
                stringBuilder.append(",");
                stringBuilder.append(string);
            } else {
                result[index++] = string;
            }
        }
        return result;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Report getReport() {
        return report;
    }
}