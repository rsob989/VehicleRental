package pl.carrental.model;

import java.util.Objects;

public class Caravan extends Vehicles{
    public final static String TYPE = "Samochód campingowy";

    private int sleepPlaces;
    private int numberOfWindows;

    public Caravan(String brand, String model, int year, String vin, int sleepPlaces, int numberOfWindows) {
        super(brand, model, year, vin);
        this.sleepPlaces = sleepPlaces;
        this.numberOfWindows = numberOfWindows;
    }

    public int getSleepPlaces() {
        return sleepPlaces;
    }

    public void setSleepPlaces(int sleepPlaces) {
        this.sleepPlaces = sleepPlaces;
    }

    public int getNumberOfWindows() {
        return numberOfWindows;
    }

    public void setNumberOfWindows(int numberOfWindows) {
        this.numberOfWindows = numberOfWindows;
    }

    @Override
    public String toString() {
        return super.toString() + " ; miejsca do spania:" + sleepPlaces + " ; liczba okien:" + numberOfWindows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Caravan caravan = (Caravan) o;
        return sleepPlaces == caravan.sleepPlaces &&
                numberOfWindows == caravan.numberOfWindows;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sleepPlaces, numberOfWindows);
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getBrand() + ";" + getModel() + ";" + getYear() + ";" + getVin() + ";" + sleepPlaces + ";"
                + numberOfWindows + "";
    }
}
