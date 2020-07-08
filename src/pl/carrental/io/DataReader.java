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
        printer.printLine("Imię: ");
        String firstName = reader.nextLine();
        printer.printLine("Nazwisko: ");
        String lastName = reader.nextLine();
        printer.printLine("PESEL: ");
        String pesel = reader.nextLine();
        return new PrivateClient(firstName, lastName, pesel);
    }

    public String createLastName(){
        printer.printLine("Podaj nazwisko klienta: ");
        String lastName = getString();
        return lastName;
    }

    public String createVin(){
        printer.printLine("Podaj numer VIN/seryjny: ");
        String vin = getString();
        return vin;
    }

    public BusinessClient createBusinessClient(){
        printer.printLine("Imię: ");
        String firstName = reader.nextLine();
        printer.printLine("Nazwisko: ");
        String lastName = reader.nextLine();
        printer.printLine("NIP: ");
        String nip = reader.nextLine();
        return new BusinessClient(firstName, lastName, nip);
    }

    public Car addNewCar(){
        printer.printLine("Podaj markę samochodu: ");
        String brand = reader.nextLine();
        printer.printLine("Podaj model samochodu: ");
        String model = reader.nextLine();
        printer.printLine("Podaj rok produkcji samochodu: ");
        int year = getInt();
        printer.printLine("Podaj przebieg samochodu: ");
        int vehicleMileage = getInt();
        printer.printLine("Podaj czy samochód jest bezwypadkowy: ");
        boolean accidentFree = getBoolean();
        printer.printLine("Podaj numer vin: ");
        String vin = reader.nextLine();

        return new Car(brand, model, year, vin, vehicleMileage, accidentFree);

    }

    public Bike addNewBike(){
        printer.printLine("Podaj markę roweru: ");
        String brand = reader.nextLine();
        printer.printLine("Podaj model roweru: ");
        String model = reader.nextLine();
        printer.printLine("Podaj rok produkcji roweru: ");
        int year = getInt();
        printer.printLine("Podaj typ roweru: ");
        String typeBike = reader.nextLine();
        printer.printLine("Podaj wielkość kół: ");
        int sizeOfWheels = getInt();
        printer.printLine("Podaj numer seryjny wygrawerowany na rowerze: ");
        String serial = reader.nextLine();

        return new Bike(brand, model, year, serial, typeBike, sizeOfWheels);

    }

    public Caravan addNewCaravan(){
        printer.printLine("Podaj markę przyczepy campingowej: ");
        String brand = reader.nextLine();
        printer.printLine("Podaj model przyczepy campingowej: ");
        String model = reader.nextLine();
        printer.printLine("Podaj rok produkcji przyczepy campingowej: ");
        int year = getInt();
        printer.printLine("Podaj ilość miejsc do spania: ");
        int sleepPlaces = getInt();
        printer.printLine("Podaj ilość okien: ");
        int numberOfWindows = getInt();
        printer.printLine("Podaj vin: ");
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
