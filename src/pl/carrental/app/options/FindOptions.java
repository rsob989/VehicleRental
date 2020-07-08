package pl.carrental.app.options;

import pl.carrental.exceptions.NoSuchOptionException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.model.ClientsRented;
import pl.carrental.model.VehiclesToRent;
import pl.carrental.model.client.Client;
import pl.carrental.model.vehicle.Vehicles;

import java.util.Comparator;
import java.util.InputMismatchException;

public class FindOptions {

    VehiclesToRent vr;
    ConsolePrinter cp;
    DataReader dr;
    ClientsRented cr;

    public FindOptions(VehiclesToRent vr, ConsolePrinter cp, DataReader dr, ClientsRented cr) {
        this.vr = vr;
        this.cp = cp;
        this.dr = dr;
        this.cr = cr;
    }

    public void findLoop(){
        PrintOption option;

        do{
            printOptions();
            option = getOption();
            switch (option){
                case BACK:
                    break;
                case FIND_CLIENT:
                    findClient();
                    break;
                case FIND_VEHICLE:
                    findVehicle();
                    break;
                default:
                    cp.printLine("Wybierz poprawną opcję! Spróbuj ponownie!");
            }
        } while (option != PrintOption.BACK);
    }

    private void findVehicle(){
        String vin = dr.createVin();
        String notFoundMessage = "Brak pojazdów z takim numerem vin w wypożyczalni";
        vr.findByVin(vin)
                .map(Vehicles::toString)
                .ifPresentOrElse(System.out::println, ()-> System.out.println(notFoundMessage));
    }

    private void findClient(){
        String lastName = dr.createLastName();
        String notFoundMessage = "Brak klientów z takim nazwiskiem w wypożyczalni";
        cr.findByLastName(lastName)
                .map(Client::toString)
                .ifPresentOrElse(System.out::println, ()-> System.out.println(notFoundMessage));
    }

    private PrintOption getOption(){
        boolean optionOk = false;
        PrintOption option = null;
        while (!optionOk){
            try{
                option = PrintOption.numberToOption(dr.getInt());
                optionOk = true;
            } catch (NoSuchOptionException ex){
                cp.printLine(ex.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException ignored){
                cp.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void printOptions(){
        cp.printLine("Wybierz jedną z poniższych opcji: ");
        for(PrintOption o: PrintOption.values()){
            cp.printLine(o.toString());
        }
    }

    private enum PrintOption {

        BACK(0, "Cofnij do głównego menu"),
        FIND_CLIENT(1, "Wyszukaj klienta"),
        FIND_VEHICLE(2, "Wyszukaj pojazd");


        private int value;
        private String description;

        PrintOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String toString() {
            return value + " - " + description;
        }

        static PrintOption numberToOption(int number) throws NoSuchOptionException {
            try {
                return PrintOption.values()[number];
            } catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchOptionException("Nie ma takiej opcji w programie: " + number);
            }
        }
    }

}
