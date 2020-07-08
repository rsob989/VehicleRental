package pl.carrental.model;

import pl.carrental.exceptions.ClientAlreadyExistsException;
import pl.carrental.exceptions.VehicleAlreadyExistsException;
import pl.carrental.model.client.Client;
import pl.carrental.model.vehicle.Vehicles;

import java.io.Serializable;
import java.util.*;

public class ClientsRented implements Serializable {

    private Map<String, Client> clients = new HashMap<>();

    private List<Vehicles> vehicleRentalHistory = new ArrayList<>();
    private List<Vehicles> borrowedVehicles = new ArrayList<>();

    public List<Vehicles> getVehicleRentalHistory() {
        return vehicleRentalHistory;
    }

    public List<Vehicles> getBorrowedVehicles() {
        return borrowedVehicles;
    }

    private void borrowVehicle(Vehicles vehicle){
        borrowedVehicles.add(vehicle);
    }

    private void addVehicleToHistory(Vehicles vehicle){
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

    public Map<String, Client> getClients() {
        return clients;
    }


    public void addClient(Client client){
        if(clients.containsKey(client.getLastName()))
            throw new ClientAlreadyExistsException("Użytkownik ze wskazanym nazwiskiem już istnieje " + client.getLastName());
        clients.put(client.getLastName(), client);
    }

    public Collection<Client> getSortedClients(Comparator<Client> comparator){
        ArrayList<Client> list = new ArrayList<>(clients.values());
        list.sort(comparator);
        return list;
    }
}
