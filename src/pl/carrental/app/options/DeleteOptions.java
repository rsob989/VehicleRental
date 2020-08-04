package pl.carrental.app.options;

import pl.carrental.exceptions.NoSuchOptionException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.model.*;
import pl.carrental.model.vehicle.Bike;
import pl.carrental.model.vehicle.Car;
import pl.carrental.model.vehicle.Caravan;

import java.util.InputMismatchException;

public class DeleteOptions {

    VehiclesToRent vr;
    ConsolePrinter cp;
    DataReader dr;

    public DeleteOptions(VehiclesToRent vr, ConsolePrinter cp, DataReader dr) {
        this.vr = vr;
        this.cp = cp;
        this.dr = dr;
    }

    public void deleteLoop(){
        DeleteOption option;

        do{
            printOptions();
            option = getOption();
            switch (option){
                case BACK:
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
                default:
                    cp.printLine("Select correct option. Try again!");
            }
        } while (option != DeleteOption.BACK);
    }

    private DeleteOption getOption(){
        boolean optionOk = false;
        DeleteOption option = null;
        while (!optionOk){
            try{
                option = DeleteOption.numberToOption(dr.getInt());
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
        for(DeleteOption o: DeleteOption.values()){
            cp.printLine(o.toString());
        }
    }

    private void deleteCar(){
        try{
            Car car = dr.addNewCar();
            if(vr.removeVehicle(car))
                cp.printLine("Car deleted");
            else
                cp.printLine("There is no such a car");
        } catch (InputMismatchException e){
            cp.printLine("Car not created, wrong data");
        }
    }

    private void deleteBike(){
        try{
            Bike bike = dr.addNewBike();
            if(vr.removeVehicle(bike))
                cp.printLine("Bicycle deleted");
            else
                cp.printLine("There is no such a bicycle");
        } catch (InputMismatchException e){
            cp.printLine("Bicycle not created, wrong data");
        }
    }

    private void deleteCaravan(){
        try{
            Caravan caravan = dr.addNewCaravan();
            if(vr.removeVehicle(caravan))
                cp.printLine("Caravan deleted");
            else
                cp.printLine("There is no such a caravan");
        } catch (InputMismatchException e){
            cp.printLine("Caravan not created, wrong data");
        }
    }

    private enum DeleteOption {

        BACK(0, "Back to main menu"),
        DELETE_CAR(1, "Delete car"),
        DELETE_BIKE(2, "Delete bicycle"),
        DELETE_CARAVAN(3, "Delete caravan");

        private int value;
        private String description;

        DeleteOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String toString() {
            return value + " - " + description;
        }

        static DeleteOption numberToOption(int number) throws NoSuchOptionException {
            try {
                return DeleteOption.values()[number];
            } catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchOptionException("There is no such an option " + number);
            }
        }
    }

}
