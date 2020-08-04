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
                    cp.printLine("Select correct option. Try again!");
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
                cp.printLine(ex.getMessage() + ", try again!");
            } catch (InputMismatchException ignored){
                cp.printLine("Entered value is not a number, try again!");
            }
        }
        return option;
    }

    private void printOptions(){
        cp.printLine("Select one of the options:");
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

        BACK(0, "Back to main menu"),
        SHOW_CARS(1, "Show all cars"),
        SHOW_BIKES(2, "Show all bicycles"),
        SHOW_CARAVANS(3, "Show all caravans"),
        SHOW_PRIVATE_CLIENTS(4, "Show all private customers"),
        SHOW_BUSINESS_CLIENTS(5, "Show all business customers");

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
                throw new NoSuchOptionException("There is no such an option " + number);
            }
        }
    }

}
