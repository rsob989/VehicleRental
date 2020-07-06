package pl.carrental.model;

import pl.carrental.exceptions.VehicleAlreadyExistsException;
import pl.carrental.model.vehicle.Vehicles;

import java.io.Serializable;
import java.util.*;

public class VehiclesToRent implements Serializable {

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

    public Map<String, Vehicles> getVehicles() {
        return vehicles;
    }

    public Collection<Vehicles> getSortedVehicles(Comparator<Vehicles> comparator){
        ArrayList<Vehicles> list = new ArrayList<>(this.vehicles.values());
        list.sort(comparator);
        return list;
    }

    public Optional<Vehicles> findByVin(String vin){
        return Optional.ofNullable(vehicles.get(vin));
    }
}
