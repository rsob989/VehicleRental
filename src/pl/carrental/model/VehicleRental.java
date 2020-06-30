package pl.carrental.model;

import pl.carrental.exceptions.ClientAlreadyExistsException;
import pl.carrental.exceptions.VehicleAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class VehicleRental implements Serializable {

    private Map<String, PrivateClient> clients = new HashMap<>();
    private Map<String, Vehicles> vehicles = new HashMap<>();

    public void addVehicles(Vehicles vehicle){
        if(vehicles.containsKey(vehicle.getVin()))
            throw new VehicleAlreadyExistsException("Pojazd o takim numerze seryjnym/vin już istnieje " + vehicle.getVin());
        vehicles.put(vehicle.getVin(), vehicle);
    }

    public boolean removeVehicle(Vehicles vehicle){
        if(vehicles.containsValue(vehicle)){
            vehicles.remove(vehicle.getVin());
            return true;
        } else {
            return false;
        }
    }

    public Map<String, PrivateClient> getClients() {
        return clients;
    }

    public Map<String, Vehicles> getVehicles() {
        return vehicles;
    }

    public void addClient(PrivateClient client){
        if(clients.containsKey(client.getPesel()))
            throw new ClientAlreadyExistsException("Użytkownik ze wskazanym peselem już istnieje " + client.getPesel());
        clients.put(client.getPesel(), client);
    }

    public Collection<Vehicles> getSortedVehicles(Comparator<Vehicles> comparator){
        ArrayList<Vehicles> list = new ArrayList<>(this.vehicles.values());
        list.sort(comparator);
        return list;
    }

    public Collection<PrivateClient> getSortedUsers(Comparator<PrivateClient> comparator){
        ArrayList<PrivateClient> list = new ArrayList<>(this.clients.values());
        list.sort(comparator);
        return list;
    }

    public Optional<Vehicles> findByVin(String vin){
        return Optional.ofNullable(vehicles.get(vin));
    }
}
