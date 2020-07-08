package pl.carrental.app.options;

import pl.carrental.exceptions.NoSuchOptionException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.model.*;
import pl.carrental.model.client.Client;
import pl.carrental.model.vehicle.Vehicles;

import java.util.Comparator;
import java.util.InputMismatchException;

public class ShowOptions {

    VehiclesToRent vr;
    ConsolePrinter cp;
    DataReader dr;
    ClientsRented cr;

    public ShowOptions(VehiclesToRent vr, ConsolePrinter cp, DataReader dr, ClientsRented cr) {
        this.vr = vr;
        this.cp = cp;
        this.dr = dr;
        this.cr = cr;
    }

    public void showLoop(){
        ShowOption option;

        do{
            printOptions();
            option = getOption();
            switch (option){
                case BACK:
                    break;
                case SHOW_CARS:
                    showCars();
                    break;
                case SHOW_BIKES:
                    showBikes();
                    break;
                case SHOW_CARAVANS:
                    showCaravans();
                    break;
                case SHOW_PRIVATE_CLIENTS:
                    printPrivateClients();
                    break;
                case SHOW_BUSINESS_CLIENTS:
                    printBusinessClients();
                    break;
                default:
                    cp.printLine("Wybierz poprawną opcję! Spróbuj ponownie!");
            }
        } while (option != ShowOption.BACK);
    }

    private ShowOption getOption(){
        boolean optionOk = false;
        ShowOption option = null;
        while (!optionOk){
            try{
                option = ShowOption.numberToOption(dr.getInt());
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
        for(ShowOption o: ShowOption.values()){
            cp.printLine(o.toString());
        }
    }

    private void showBikes(){
        cp.printBikes(vr.getSortedVehicles(
                Comparator.comparing(Vehicles::getModel, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void showCaravans(){
        cp.printCaravans(vr.getSortedVehicles(
                Comparator.comparing(Vehicles::getModel, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void showCars(){
        cp.printCars(vr.getSortedVehicles(
                Comparator.comparing(Vehicles::getModel, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void printPrivateClients(){
        cp.printPrivateClients(cr.getSortedClients(
                Comparator.comparing(Client::getLastName, String.CASE_INSENSITIVE_ORDER)));
    }

    private void printBusinessClients(){
        cp.printBusinessClients(cr.getSortedClients(
                Comparator.comparing(Client::getLastName, String.CASE_INSENSITIVE_ORDER)));
    }

    private enum ShowOption {

        BACK(0, "Cofnij do głównego menu"),
        SHOW_CARS(1, "Wyświetlenie wszystkich samochodów dostępnych w wypożyczalni"),
        SHOW_BIKES(2, "Wyświetlenie wszystkich rowerów dostępnych w wypożyczalni"),
        SHOW_CARAVANS(3, "Wyświetlenie wszystkich wozów campingowych dostępnych w wypożyczalni"),
        SHOW_PRIVATE_CLIENTS(4, "Wyświetlenie wszystkich prywatnych klientów"),
        SHOW_BUSINESS_CLIENTS(5, "Wyświetlenie wszystkich klientów bizesowych");

        private int value;
        private String description;

        ShowOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String toString() {
            return value + " - " + description;
        }

        static ShowOption numberToOption(int number) throws NoSuchOptionException {
            try {
                return ShowOption.values()[number];
            } catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchOptionException("Nie ma takiej opcji w programie: " + number);
            }
        }
    }

}
