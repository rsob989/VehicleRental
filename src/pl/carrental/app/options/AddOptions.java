package pl.carrental.app.options;

import pl.carrental.exceptions.ClientAlreadyExistsException;
import pl.carrental.exceptions.NoSuchOptionException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.model.ClientsRented;
import pl.carrental.model.client.BusinessClient;
import pl.carrental.model.client.PrivateClient;
import pl.carrental.model.vehicle.Bike;
import pl.carrental.model.vehicle.Car;
import pl.carrental.model.vehicle.Caravan;
import pl.carrental.model.VehiclesToRent;
import java.util.InputMismatchException;

public class AddOptions {

    VehiclesToRent vr;
    ConsolePrinter cp;
    DataReader dr;
    ClientsRented cr;

    public AddOptions(VehiclesToRent vr, ConsolePrinter cp, DataReader dr, ClientsRented cr) {
        this.vr = vr;
        this.cp = cp;
        this.dr = dr;
        this.cr = cr;
    }

    public void addLoop(){
        AddOption option;

        do{
            printOptions();
            option = getOption();
            switch (option){
                case BACK:
                    break;
                case ADD_CAR:
                    addCar();
                    break;
                case ADD_BIKE:
                    addBike();
                    break;
                case ADD_CARAVAN:
                    addCaravan();
                    break;
                case ADD_PRIVATE_CLIENT:
                    addPrivateClient();
                    break;
                case ADD_BUSINESS_CLIENT:
                    addBusinessClient();
                    break;
                default:
                    cp.printLine("Select correct option, try again!");
            }
        } while (option != AddOption.BACK);
    }

    private AddOption getOption(){
        boolean optionOk = false;
        AddOption option = null;
        while (!optionOk){
            try{
                option = AddOption.numberToOption(dr.getInt());
                optionOk = true;
            } catch (NoSuchOptionException ex){
                cp.printLine(ex.getMessage() + ", try again:");
            } catch (InputMismatchException ignored){
                cp.printLine("Entered value is not a number, try again!");
            }
        }
        return option;
    }

    private void printOptions(){
        cp.printLine("Select one of the options:");
        for(AddOption o: AddOption.values()){
            cp.printLine(o.toString());
        }
    }

    private void addBike(){
        try {
            Bike bike = dr.addNewBike();
            vr.addVehicles(bike);
        } catch (InputMismatchException e){
            cp.printLine("Bicycle not created, wrong data");
        } catch (ArrayIndexOutOfBoundsException e){
            cp.printLine("Maximum number of vehicles reached");
        }
    }

    private void addCaravan(){
        try {
            Caravan caravan = dr.addNewCaravan();
            vr.addVehicles(caravan);
        } catch (InputMismatchException e){
            cp.printLine("Caravan not created, wrong data");
        } catch (ArrayIndexOutOfBoundsException e){
            cp.printLine("Maximum number of vehicles reached");
        }
    }

    private void addCar(){
        try {
            Car car = dr.addNewCar();
            vr.addVehicles(car);
        } catch (InputMismatchException e){
            cp.printLine("Car not created, wrong data");
        } catch (ArrayIndexOutOfBoundsException e){
            cp.printLine("Maximum number of vehicles reached");
        }
    }

    private void addPrivateClient(){
        PrivateClient pc = dr.createPrivateClient();
        try{
            cr.addClient(pc);
        } catch (ClientAlreadyExistsException e){
            cp.printLine(e.getMessage());
        }
    }

    private void addBusinessClient(){
        BusinessClient pc = dr.createBusinessClient();
        try{
            cr.addClient(pc);
        } catch (ClientAlreadyExistsException e){
            cp.printLine(e.getMessage());
        }
    }

    private enum AddOption {

        BACK(0, "Back to main menu"),
        ADD_CAR(1, "Add car"),
        ADD_BIKE(2, "Add bicycle"),
        ADD_CARAVAN(3, "Add caravan"),
        ADD_PRIVATE_CLIENT(4,"Add private customer"),
        ADD_BUSINESS_CLIENT(5, "Add business customer");

        private int value;
        private String description;

        AddOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String toString() {
            return value + " - " + description;
        }

        static AddOption numberToOption(int number) throws NoSuchOptionException {
            try {
                return AddOption.values()[number];
            } catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchOptionException("There is no such an option " + number);
            }
        }
    }

}
