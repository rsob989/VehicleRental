package pl.carrental.io.file;

import pl.carrental.model.VehicleRental;

public interface FileManager {

    VehicleRental importData();
    void exportData(VehicleRental vr);

}
