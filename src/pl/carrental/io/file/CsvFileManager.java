package pl.carrental.io.file;

import pl.carrental.exceptions.DataExportException;
import pl.carrental.exceptions.DataImportException;
import pl.carrental.exceptions.InvalidDataException;
import pl.carrental.model.*;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

public class CsvFileManager implements FileManager {
    private static final String VEHICLES_FILE_NAME = "VehicleRental.csv";
    private static final String CLIENTS_FILE_NAME = "VehicleRentalClients.csv";

    @Override
    public void exportData(VehicleRental vr) {
        exportVehicles(vr);
        exportClients(vr);
    }

    private void exportClients(VehicleRental vr){
        Collection<PrivateClient> clients = vr.getClients().values();
        exportToCsv(clients, CLIENTS_FILE_NAME);
    }

    private void exportVehicles(VehicleRental vr) {
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
            throw new DataExportException("Błąd zapisu danych do pliku " + fileName);
        }
    }


    @Override
    public VehicleRental importData() {
        VehicleRental vr = new VehicleRental();
        importVehicles(vr);
        importClients(vr);
        return vr;
    }

    private void importClients(VehicleRental vr) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(CLIENTS_FILE_NAME))
        ){
            bufferedReader.lines()
                    .map(this::createClientFromLine)
                    .forEach(vr::addClient);
        } catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku " + CLIENTS_FILE_NAME);
        } catch (IOException e){
            throw new DataImportException("Błąd odczytu pliku " + CLIENTS_FILE_NAME);
        }
    }

    private void importVehicles(VehicleRental vr) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(VEHICLES_FILE_NAME))
                ){
            bufferedReader.lines()
                    .map(this::createObjectFromLine)
                    .forEach(vr::addVehicles);
            } catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku " + VEHICLES_FILE_NAME);
            } catch (IOException e){
            throw new DataImportException("Błąd odczytu pliku " + VEHICLES_FILE_NAME);
        }
    }

    private PrivateClient createClientFromLine(String csv){
        String[] split = csv.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new PrivateClient(firstName, lastName, pesel);
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
        throw new InvalidDataException("Nieznany typ pojazdu: " + type);
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
