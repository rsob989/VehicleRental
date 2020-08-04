package pl.carrental.model.vehicle;

import java.util.Objects;

public class Bike extends Vehicles {
    public static final String TYPE = "Bicycle";

    private String typeBike;
    private int sizeOfWheels;

    public Bike(String brand, String model, int year, String vin, String typeBike, int sizeOfWheels) {
        super(brand, model, year, vin);
        this.typeBike = typeBike;
        this.sizeOfWheels = sizeOfWheels;
    }

    public String getTypeBike() {
        return typeBike;
    }

    public void setTypeBike(String typeBike) {
        this.typeBike = typeBike;
    }

    public int getSizeOfWheels() {
        return sizeOfWheels;
    }

    public void setSizeOfWheels(int sizeOfWheels) {
        this.sizeOfWheels = sizeOfWheels;
    }

    @Override
    public String toString() {
        return super.toString() + " ; type:" + typeBike + " ; wheel size:" + sizeOfWheels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bike bike = (Bike) o;
        return sizeOfWheels == bike.sizeOfWheels &&
                Objects.equals(typeBike, bike.typeBike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeBike, sizeOfWheels);
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getBrand() + ";" + getModel() + ";" + getYear() + ";" + ";" + getVin() + ";" + typeBike
                + ";" + sizeOfWheels + "";
    }
}
