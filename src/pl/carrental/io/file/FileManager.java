package pl.carrental.io.file;

import pl.carrental.model.ClientsRented;
import pl.carrental.model.VehiclesToRent;

public interface FileManager {

    VehiclesToRent importVehicles();
    ClientsRented importClients();
    void exportData(VehiclesToRent vr, ClientsRented cr);

}
