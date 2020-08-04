package pl.carrental.io.file;

import pl.carrental.exceptions.DataExportException;
import pl.carrental.exceptions.DataImportException;
import pl.carrental.model.ClientsRented;
import pl.carrental.model.VehiclesToRent;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String VEHICLE_FILE_NAME = "Vehicle.o";
    private static final String CLIENTS_FILE_NAME = "Clients.o";

    @Override
    public VehiclesToRent importVehicles() {
        try(
                FileInputStream fis = new FileInputStream(VEHICLE_FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ){
            VehiclesToRent vr = (VehiclesToRent)ois.readObject();
            return vr;
        } catch (FileNotFoundException e){
            throw new DataImportException("No such a file: " + VEHICLE_FILE_NAME);
        } catch (IOException e){
            throw new DataImportException("A read error occurred " + VEHICLE_FILE_NAME);
        } catch (ClassNotFoundException e){
            throw new DataImportException("Incompatible data type in the file " + VEHICLE_FILE_NAME);
        }
    }

    public ClientsRented importClients() {
        try(
                FileInputStream fis = new FileInputStream(CLIENTS_FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ){
            ClientsRented cr = (ClientsRented) ois.readObject();
            return cr;
        } catch (FileNotFoundException e){
            throw new DataImportException("No such a file: " + CLIENTS_FILE_NAME);
        } catch (IOException e){
            throw new DataImportException("A read error occurred " + CLIENTS_FILE_NAME);
        } catch (ClassNotFoundException e){
            throw new DataImportException("Incompatible data type in the file " + CLIENTS_FILE_NAME);
        }
    }

    @Override
    public void exportData(VehiclesToRent vr, ClientsRented cr) {
        export(vr, VEHICLE_FILE_NAME);
        export(cr, CLIENTS_FILE_NAME);

    }

    private <T> void export(T data, String fileName) {
        try(
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ){
            oos.writeObject(data);
        } catch (FileNotFoundException e){
            throw new DataExportException("No such a file: " + fileName);
        } catch (IOException e){
            throw new DataExportException("A write error occurred " + fileName);
        }
    }
}
