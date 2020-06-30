package pl.carrental.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrivateClient extends Client {

    private List<Vehicles> vehicleRentalHistory = new ArrayList<>();
    private List<Vehicles> borrowedVehicles = new ArrayList<>();

    public List<Vehicles> getVehicleRentalHistory() {
        return vehicleRentalHistory;
    }

    public List<Vehicles> getBorrowedVehicles() {
        return borrowedVehicles;
    }

    public PrivateClient(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    private void borrowVehicle(Vehicles vehicle){
        borrowedVehicles.add(vehicle);
    }

    private void addVehicleToHistory(Vehicles vehicle){
        vehicleRentalHistory.add(vehicle);
    }

    public boolean returnPublication(Vehicles vehicle){
        boolean result = false;
        if(borrowedVehicles.remove(vehicle)){
            result = true;
            addVehicleToHistory(vehicle);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PrivateClient that = (PrivateClient) o;
        return Objects.equals(vehicleRentalHistory, that.vehicleRentalHistory) &&
                Objects.equals(borrowedVehicles, that.borrowedVehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vehicleRentalHistory, borrowedVehicles);
    }

    @Override
    public String toCsv() {
        return getFirstName() + ";" + getLastName() + ";" + getPesel();
    }
}
