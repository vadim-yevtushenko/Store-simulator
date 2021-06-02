import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {

        DrinkStore drinkStore = new DrinkStore();

        for (int i = 1; i < 31; i++) {
            for (int j = 0; j < 24; j++) {
                drinkStore.startWork(new GregorianCalendar(2021, Calendar.APRIL, i, j, 0));
            }
        }
        drinkStore.getReport().makeReport();
        drinkStore.saveDrinks();
    }
}
