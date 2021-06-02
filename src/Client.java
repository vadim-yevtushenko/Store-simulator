public class Client {
    private int numberProducts;
    private static int count = 1;
    private String name;

    public Client() {
        this.name = "Client " + count++;
        this.numberProducts = (int) (Math.random() * 10) + 1;
    }

    public int getNumberProducts() {
        return numberProducts;
    }

    public String getName() {
        return name;
    }
}