package pl.carrental.app.options;

import pl.carrental.exceptions.NoSuchOptionException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

public class DeleteOptions {

    VehicleRental vr;
    ConsolePrinter cp;
    DataReader dr;

    public DeleteOptions(VehicleRental vr, ConsolePrinter cp, DataReader dr) {
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
                    cp.printLine("Wybierz poprawną opcję! Spróbuj ponownie!");
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
                cp.printLine(ex.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException ignored){
                cp.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void printOptions(){
        cp.printLine("Wybierz jedną z poniższych opcji: ");
        for(DeleteOption o: DeleteOption.values()){
            cp.printLine(o.toString());
        }
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

    private enum DeleteOption {

        BACK(0, "Cofnij do głównego menu"),
        DELETE_CAR(1, "Usuń samochód z wypożyczalni"),
        DELETE_BIKE(2, "Usuń rower z wypożyczalni"),
        DELETE_CARAVAN(3, "Usuń samochód campingowy z wypożyczalni");

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
                throw new NoSuchOptionException("Nie ma takiej opcji w programie: " + number);
            }
        }
    }

}
