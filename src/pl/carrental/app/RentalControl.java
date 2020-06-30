package pl.carrental.app;

import pl.carrental.exceptions.*;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.io.file.FileManager;
import pl.carrental.io.file.FileManagerBuilder;
import pl.carrental.model.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class RentalControl {
    private ConsolePrinter cp = new ConsolePrinter();
    private DataReader dr = new DataReader(cp);
    private FileManager fm;

    private VehicleRental vr;

    public RentalControl() {
        fm = new FileManagerBuilder(cp, dr).build();
        try {
            vr = fm.importData();
            cp.printLine("Zaimportowane dane z pliku");
        } catch (DataImportException | InvalidDataException e){
            cp.printLine(e.getMessage());
            cp.printLine("Zainicjowano nową bazę.");
            vr = new VehicleRental();
        }
    }

    void controlLoop(){
        Option option;

        do{
            printOptions();
            option = getOption();
            switch (option){
                case EXIT:
                    exit();
                    break;
                case ADD_CAR:
                    addCar();
                    break;
                case SHOW_CARS:
                    showCars();
                    break;
                case ADD_BIKE:
                    addBike();
                    break;
                case SHOW_BIKES:
                    showBikes();
                    break;
                case ADD_CARAVAN:
                    addCaravan();
                    break;
                case SHOW_CARAVANS:
                    showCaravans();
                    break;
                case DELETE_CAR:
                    deleteCar();
                    break;
                case DELETE_BIKE:
                    deleteBike();
                    break;
                case DELETE_CARAVAN:
                    deleteCaravan();
                    break;
                case ADD_PRIVATE_CLIENT:
                    addPrivateClient();
                    break;
                case PRINT_PRIVATE_CLIENTS:
                    printPrivateClients();
                    break;
                case FIND_VEHICLE:
                    findVehicle();
                    break;
                default:
                    cp.printLine("Wybierz poprawną opcję! Spróbuj ponownie!");
            }
        } while (option != Option.EXIT);
    }

    private void findVehicle(){
        cp.printLine("Podaj numer vin:");
        String vin = dr.getString();
        String notFoundMessage = "Brak pojazdów z takim numerem vin w wypożyczalni";
        vr.findByVin(vin)
                .map(Vehicles::toString)
                .ifPresentOrElse(System.out::println, ()-> System.out.println(notFoundMessage));
    }

    private void addPrivateClient(){
        PrivateClient pc = dr.createPrivateClient();
        try{
            vr.addClient(pc);
        } catch (ClientAlreadyExistsException e){
            cp.printLine(e.getMessage());
        }
    }

    private void printPrivateClients(){
        cp.printPrivateClients(vr.getSortedUsers(
                Comparator.comparing(PrivateClient::getPesel, String.CASE_INSENSITIVE_ORDER)));
    }

    private void deleteCar(){
        try{
            Car car = dr.addNewCar();
            if(vr.removeVehicle(car))
                cp.printLine("Usunięto samochód z wypożyczalni");
            else
                cp.printLine("Brak wskazanego samochodu w wypożyczalni");
        } catch (InputMismatchException e){
            cp.printLine("Nie udało się utworzyć samochodu, niepoprawne dane");
        }
    }

    private void deleteBike(){
        try{
            Bike bike = dr.addNewBike();
            if(vr.removeVehicle(bike))
                cp.printLine("Usunięto rower z wypożyczalni");
            else
                cp.printLine("Brak wskazanego roweru w wypożyczalni");
        } catch (InputMismatchException e){
            cp.printLine("Nie udało się utworzyć roweru, niepoprawne dane");
        }
    }

    private void deleteCaravan(){
        try{
            Caravan caravan = dr.addNewCaravan();
            if(vr.removeVehicle(caravan))
                cp.printLine("Usunięto samochód campingowy z wypożyczalni");
            else
                cp.printLine("Brak wskazanego samochodu campingowego w wypożyczalni");
        } catch (InputMismatchException e){
            cp.printLine("Nie udało się utworzyć samochodu campingowego, niepoprawne dane");
        }
    }

    private Option getOption(){
        boolean optionOk = false;
        Option option = null;
        while (!optionOk){
            try{
                option = Option.numberToOption(dr.getInt());
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
        for(Option o: Option.values()){
            cp.printLine(o.toString());
        }
    }

    private void addBike(){
        try {
            Bike bike = dr.addNewBike();
            vr.addVehicles(bike);
        } catch (InputMismatchException e){
            cp.printLine("Nie udało utworzyć się roweru, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e){
            cp.printLine("Osiągnięto limit przchowywanych pojazdów");
        }
    }

    private void showBikes(){
        cp.printBikes(vr.getSortedVehicles(
                Comparator.comparing(Vehicles::getModel, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void addCaravan(){
        try {
            Caravan caravan = dr.addNewCaravan();
            vr.addVehicles(caravan);
        } catch (InputMismatchException e){
            cp.printLine("Nie udało się utworzyć samochodu campingowego, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e){
            cp.printLine("Osiągnięto limit przchowywanych pojazdów");
        }
    }

    private void showCaravans(){
        cp.printCaravans(vr.getSortedVehicles(
                Comparator.comparing(Vehicles::getModel, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void addCar(){
        try {
            Car car = dr.addNewCar();
            vr.addVehicles(car);
        } catch (InputMismatchException e){
            cp.printLine("Nie udało utworzyć się samochodu, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e){
            cp.printLine("Osiągnięto limit przchowywanych pojazdów");
        }
    }

    private void showCars(){
        cp.printCars(vr.getSortedVehicles(
                Comparator.comparing(Vehicles::getModel, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void exit(){
        try {
            fm.exportData(vr);
            cp.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e){
            cp.printLine(e.getMessage());
        }
        cp.printLine("Do zobaczenia! ");
        dr.close();
    }

    private enum Option {

        EXIT(0, "Wyjście z programu"),
        ADD_CAR(1, "Dodanie kolejnego samochodu do wypożyczalni"),
        ADD_BIKE(2, "Dodanie kolejnego roweru do wypożyczalni"),
        ADD_CARAVAN(3, "Dodanie kolejnego wozu campingowego do wypożyczalni"),
        SHOW_CARS(4, "Wyświetlenie wszystkich samochodów dostępnych w wypożyczalni"),
        SHOW_BIKES(5, "Wyświetlenie wszystkich rowerów dostępnych w wypożyczalni"),
        SHOW_CARAVANS(6, "Wyświetlenie wszystkich wozów campingowych dostępnych w wypożyczalni"),
        DELETE_CAR(7, "Usuń samochód z wypożyczalni"),
        DELETE_BIKE(8, "Usuń rower z wypożyczalni"),
        DELETE_CARAVAN(9, "Usuń samochód campingowy z wypożyczalni"),
        ADD_PRIVATE_CLIENT(10, "Dodaj prywatnego klienta"),
        PRINT_PRIVATE_CLIENTS(11, "Wyświetl prywatnych klientów"),
        FIND_VEHICLE(12,"Wyszukaj pojazd");

        private int value;
        private String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String toString() {
            return value + " - " + description;
        }

        static Option numberToOption(int number) throws NoSuchOptionException {
            try {
                return Option.values()[number];
            } catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchOptionException("Nie ma takiej opcji w programie: " + number);
            }
        }
    }
}
