package pl.carrental.model;

import java.util.Objects;

public class Car extends Vehicles {
    public static final String TYPE = "Samochód";

    private int vehicleMileage;
    private boolean accidentFree;

    public int getVehicleMileage() {
        return vehicleMileage;
    }

    public void setVehicleMileage(int vehicleMileage) {
        this.vehicleMileage = vehicleMileage;
    }

    public boolean isAccidentFree() {
        return accidentFree;
    }

    public void setAccidentFree(boolean accidentFree) {
        this.accidentFree = accidentFree;
    }

    public Car(String brand, String model, int year, String vin, int vehicleMileage, boolean accidentFree) {
        super(brand, model, year, vin);
        this.vehicleMileage = vehicleMileage;
        this.accidentFree = accidentFree;
    }

    @Override
    public String toString() {
        return super.toString() + " ; przebieg:" + vehicleMileage + " ; bezwypadowy:" + accidentFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return vehicleMileage == car.vehicleMileage &&
                accidentFree == car.accidentFree;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vehicleMileage, accidentFree);
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getBrand() + ";" + getModel() + ";" + getYear() + ";" + getVin() +  ";" + vehicleMileage +
                ";" + accidentFree + "";
    }
}
