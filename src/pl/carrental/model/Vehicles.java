package pl.carrental.model;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

public abstract class Vehicles implements Serializable, Comparable<Vehicles>, CsvConvertible {
    private String brand;
    private String model;
    private Year year;
    private String vin;

    public Vehicles(String brand, String model, int year, String vin) {
        this.brand = brand;
        this.model = model;
        this.year = Year.of(year);
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    String getBrand() {
        return brand;
    }

    void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    void setModel(String model) {
        this.model = model;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "marka:" + brand + " ; model:" +  model + " ; rocznik:" + year + " ; numer seryjny/vin:" + vin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicles vehicles = (Vehicles) o;
        return year == vehicles.year &&
                Objects.equals(brand, vehicles.brand) &&
                Objects.equals(model, vehicles.model) &&
                Objects.equals(vin, vehicles.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, vin);
    }

    @Override
    public int compareTo(Vehicles o) {
        return brand.compareToIgnoreCase(o.brand);
    }
}
