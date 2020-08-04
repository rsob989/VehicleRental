package pl.carrental.model.client;

import pl.carrental.model.vehicle.Vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrivateClient extends Client {
    public static final String TYPE = "Private customer";

    private String pesel;

    public PrivateClient(String firstName, String lastName, String pesel) {
        super(firstName, lastName);
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PrivateClient that = (PrivateClient) o;
        return Objects.equals(pesel, that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pesel);
    }

    @Override
    public String toString() {
        return super.toString() + " " + pesel;
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getFirstName() + ";" + getLastName() + ";" + pesel;
    }
}
