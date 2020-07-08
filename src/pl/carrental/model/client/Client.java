package pl.carrental.model.client;

import pl.carrental.model.CsvConvertible;
import pl.carrental.model.vehicle.Vehicles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Client implements Serializable, CsvConvertible, Comparable<Client> {
    private String firstName;
    private String lastName;
    private List<Vehicles> vehicleRentalHistory = new ArrayList<>();
    private List<Vehicles> borrowedVehicles = new ArrayList<>();

    public List<Vehicles> getVehicleRentalHistory() {
        return vehicleRentalHistory;
    }

    public List<Vehicles> getBorrowedVehicles() {
        return borrowedVehicles;
    }

    public void borrowVehicle(Vehicles vehicle){
        borrowedVehicles.add(vehicle);
    }

    public void addVehicleToHistory(Vehicles vehicle){
        vehicleRentalHistory.add(vehicle);
    }

    public boolean returnVehicle(Vehicles vehicle){
        boolean result = false;
        if(borrowedVehicles.remove(vehicle)){
            result = true;
            addVehicleToHistory(vehicle);
        }
        return result;
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Client o) {
        return o.lastName.compareToIgnoreCase(lastName);
    }
}
