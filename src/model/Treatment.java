package model;

public class Treatment implements Treatable {
    private String name;
    private double cost;

    public Treatment(String name, double cost) {
        setName(name);
        setCost(cost);
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Treatment name cannot be empty");
        }
        this.name = name;
    }

    public void setCost(double cost) {
        if(cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative.");
        }
        if (cost == 0) {
            throw new IllegalArgumentException("Cost cannot be zero.");
        }
        this.cost = cost;
    }

    @Override
    public void performTreatment() {
        System.out.println("Performing treatment: " + name);
    }

    @Override
    public String getDetails() {
        return "Name: " + name + "\nCost: " + cost;

    }

    public void displayInfo() {
        System.out.println("Item: " + name);
        System.out.println("Price: " + cost + " KZT");
    }

    @Override
    public String toString() {
        return name + " - " + cost + " KZT";
    }
}
