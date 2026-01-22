package menu;

public class Treatment implements Treatable {
    private String name;
    private double cost;

    @Override
    public void performTreatment() {
        System.out.println("Performing treatment: " + name);
    }

    @Override
    public String getDetails() {
        return "Name: " + name + "\nCost: " + cost;

    }
}
