package pl.carrental.io;

import pl.carrental.model.client.BusinessClient;
import pl.carrental.model.client.Client;
import pl.carrental.model.vehicle.Bike;
import pl.carrental.model.vehicle.Car;
import pl.carrental.model.vehicle.Caravan;
import pl.carrental.model.client.PrivateClient;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DataReader {

    private Scanner reader = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public String getString(){
        return reader.nextLine();
    }

    public PrivateClient createPrivateClient(){
        printer.printLine("First name: ");
        String firstName = reader.nextLine();
        printer.printLine("Last name: ");
        String lastName = reader.nextLine();
        printer.printLine("ID number: ");
        String pesel = reader.nextLine();
        return new PrivateClient(firstName, lastName, pesel);
    }

    public String createLastName(){
        printer.printLine("Last name: ");
        String lastName = getString();
        return lastName;
    }

    public String createVin(){
        printer.printLine("VIN/Serial number: ");
        String vin = getString();
        return vin;
    }

    public BusinessClient createBusinessClient(){
        printer.printLine("First name: ");
        String firstName = reader.nextLine();
        printer.printLine("Last name: ");
        String lastName = reader.nextLine();
        printer.printLine("ID: ");
        String nip = reader.nextLine();
        return new BusinessClient(firstName, lastName, nip);
    }

    public Car addNewCar(){
        printer.printLine("Brand: ");
        String brand = reader.nextLine();
        printer.printLine("Model: ");
        String model = reader.nextLine();
        printer.printLine("Year: ");
        int year = getInt();
        printer.printLine("Mileage: ");
        int vehicleMileage = getInt();
        printer.printLine("Accident-free: ");
        boolean accidentFree = getBoolean();
        printer.printLine("VIN: ");
        String vin = reader.nextLine();

        return new Car(brand, model, year, vin, vehicleMileage, accidentFree);

    }

    public Bike addNewBike(){
        printer.printLine("Brand: ");
        String brand = reader.nextLine();
        printer.printLine("Model: ");
        String model = reader.nextLine();
        printer.printLine("Year: ");
        int year = getInt();
        printer.printLine("Type: ");
        String typeBike = reader.nextLine();
        printer.printLine("Wheel size: ");
        int sizeOfWheels = getInt();
        printer.printLine("Serial number: ");
        String serial = reader.nextLine();

        return new Bike(brand, model, year, serial, typeBike, sizeOfWheels);

    }

    public Caravan addNewCaravan(){
        printer.printLine("Brand: ");
        String brand = reader.nextLine();
        printer.printLine("Model: ");
        String model = reader.nextLine();
        printer.printLine("Year: ");
        int year = getInt();
        printer.printLine("Number of sleep places: ");
        int sleepPlaces = getInt();
        printer.printLine("Number of windows: ");
        int numberOfWindows = getInt();
        printer.printLine("VIN: ");
        String vin = reader.nextLine();

        return new Caravan(brand, model, year, vin, sleepPlaces, numberOfWindows);

    }

    public int getInt(){
        try {
            return reader.nextInt();
        } finally {
            reader.nextLine();
        }
    }

    private boolean getBoolean(){
        boolean state = reader.hasNext();
        reader.nextLine();
        return state;
    }

    public void close(){
        reader.close();
    }
}
