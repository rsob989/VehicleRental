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
                    cp.printLine("Wybierz poprawną opcję! Spróbuj ponownie!");
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
                cp.printLine(ex.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException ignored){
                cp.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void printOptions(){
        cp.printLine("Wybierz jedną z poniższych opcji: ");
        for(AddOption o: AddOption.values()){
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

        BACK(0, "Cofnij do głównego menu"),
        ADD_CAR(1, "Dodanie kolejnego samochodu do wypożyczalni"),
        ADD_BIKE(2, "Dodanie kolejnego roweru do wypożyczalni"),
        ADD_CARAVAN(3, "Dodanie kolejnego wozu campingowego do wypożyczalni"),
        ADD_PRIVATE_CLIENT(4,"Dodanie prywatnego klienta do wypożyczalni"),
        ADD_BUSINESS_CLIENT(5, "Dodanie biznesowego klienta do wypożyczalni");

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
                throw new NoSuchOptionException("Nie ma takiej opcji w programie: " + number);
            }
        }
    }

}
