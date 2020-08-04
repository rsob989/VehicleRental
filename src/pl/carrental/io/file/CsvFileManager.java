package pl.carrental.io.file;

import pl.carrental.exceptions.DataExportException;
import pl.carrental.exceptions.DataImportException;
import pl.carrental.exceptions.InvalidDataException;
import pl.carrental.model.*;
import pl.carrental.model.client.BusinessClient;
import pl.carrental.model.client.Client;
import pl.carrental.model.client.PrivateClient;
import pl.carrental.model.vehicle.Bike;
import pl.carrental.model.vehicle.Car;
import pl.carrental.model.vehicle.Caravan;
import pl.carrental.model.vehicle.Vehicles;

import java.io.*;
import java.util.Collection;

public class CsvFileManager implements FileManager {
    private static final String VEHICLES_FILE_NAME = "VehicleRental.csv";
    private static final String CLIENTS_FILE_NAME = "VehicleRentalClients.csv";

    @Override
    public void exportData(VehiclesToRent vr, ClientsRented cr) {
        exportVehicles(vr);
        exportClients(cr);
    }

    private void exportClients(ClientsRented cr){
        Collection<Client> clients = cr.getClients().values();
        exportToCsv(clients, CLIENTS_FILE_NAME);
    }

    private void exportVehicles(VehiclesToRent vr) {
        Collection<Vehicles> vehicles = vr.getVehicles().values();
        exportToCsv(vehicles, VEHICLES_FILE_NAME);
    }

    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try(
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                ){
            for(T element: collection){
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e){
            throw new DataExportException("A write error occurred " + fileName);
        }
    }


    @Override
    public VehiclesToRent importVehicles() {
        VehiclesToRent vr = new VehiclesToRent();
        importVehicles(vr);
        return vr;
    }

    public ClientsRented importClients() {
        ClientsRented cr = new ClientsRented();
        importClients(cr);
        return cr;
    }

    private void importClients(ClientsRented cr) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(CLIENTS_FILE_NAME))
        ){
            bufferedReader.lines()
                    .map(this::createClientFromLine)
                    .forEach(cr::addClient);
        } catch (FileNotFoundException e){
            throw new DataImportException("No such a file " + CLIENTS_FILE_NAME);
        } catch (IOException e){
            throw new DataImportException("A read error occurred " + CLIENTS_FILE_NAME);
        }
    }

    private void importVehicles(VehiclesToRent vr) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(VEHICLES_FILE_NAME))
                ){
            bufferedReader.lines()
                    .map(this::createObjectFromLine)
                    .forEach(vr::addVehicles);
            } catch (FileNotFoundException e){
            throw new DataImportException("No such a file " + VEHICLES_FILE_NAME);
            } catch (IOException e){
            throw new DataImportException("A read error occurred " + VEHICLES_FILE_NAME);
        }
    }

    private Client createClientFromLine(String csv){
        String[] split = csv.split(";");
        String type = split[0];
        if(PrivateClient.TYPE.equals(type)){
            return createPrivateClient(split);
        } else if(BusinessClient.TYPE.equals(type)){
            return createBusinessClient(split);
        }
        throw new InvalidDataException("Customer type unknown: " + type);
    }

    private PrivateClient createPrivateClient(String[] data){
        String firstName = data[1];
        String lastName = data[2];
        String pesel = data[3];
        return new PrivateClient(firstName, lastName, pesel);
    }

    private BusinessClient createBusinessClient(String[] data){
        String firstName = data[1];
        String lastName = data[2];
        String nip = data[3];
        return new BusinessClient(firstName, lastName, nip);
    }

    private Vehicles createObjectFromLine(String csv){
        String[] split = csv.split(";");
        String type = split[0];
        if(Bike.TYPE.equals(type)){
            return createBike(split);
        } else if(Car.TYPE.equals(type)){
            return createCar(split);
        } else if(Caravan.TYPE.equals(type)){
            return createCaravan(split);
        }
        throw new InvalidDataException("Vehicle type unknown: " + type);
    }

    private Bike createBike(String[] data){
        String brand = data[1];
        String model = data[2];
        int year = Integer.parseInt(data[3]);
        String serial = data[4];
        String typeBike = data[5];
        int sizeOfWheels = Integer.parseInt(data[6]);
        return new Bike(brand, model, year, serial, typeBike, sizeOfWheels);
    }

    private Car createCar(String[] data){
        String brand = data[1];
        String model = data[2];
        int year = Integer.parseInt(data[3]);
        String vin = data[4];
        int vehicleMileage = Integer.parseInt(data[5]);
        boolean accidentFree = Boolean.parseBoolean(data[6]);
        return new Car(brand, model, year, vin, vehicleMileage, accidentFree);
    }

    private Caravan createCaravan(String[] data){
        String brand = data[1];
        String model = data[2];
        int year = Integer.parseInt(data[3]);
        String vin = data[4];
        int sleepPlaces = Integer.parseInt(data[5]);
        int numberOfWindows = Integer.parseInt(data[6]);
        return new Caravan(brand, model, year, vin, sleepPlaces, numberOfWindows);
    }
}
