package pl.carrental.app.options;

import pl.carrental.exceptions.NoSuchOptionException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.model.ClientsRented;
import pl.carrental.model.VehiclesToRent;
import pl.carrental.model.client.Client;
import pl.carrental.model.vehicle.Vehicles;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

public class RentOptions {

    ConsolePrinter cp;
    DataReader dr;
    ClientsRented cr;
    VehiclesToRent vr;

    public RentOptions(ConsolePrinter cp, DataReader dr, ClientsRented cr, VehiclesToRent vr) {
        this.cp = cp;
        this.dr = dr;
        this.cr = cr;
        this.vr = vr;
    }

    public void rentLoop(){
        RentOption option;

        do{
            printOptions();
            option = getOption();
            switch (option){
                case BACK:
                    break;
                case RENT_VEHICLE:
                    rentVehicle();
                    break;
                case RETURN_VEHICLE:
                    returnVehicle();
                    break;
                case SHOW_HISTORY_OF_RENTED_CARS:
                    showHistoryOfRentedCars();
                    break;
                case SHOW_RENTED_CARS:
                    showRentedCars();
                    break;
                default:
                    cp.printLine("Wybierz poprawną opcję! Spróbuj ponownie!");
            }
        } while (option != RentOption.BACK);
    }

    private void printOptions(){
        cp.printLine("Wybierz jedną z poniższych opcji: ");
        for(RentOption o: RentOption.values()){
            cp.printLine(o.toString());
        }
    }

    private RentOption getOption(){
        boolean optionOk = false;
        RentOption option = null;
        while (!optionOk){
            try{
                option = RentOption.numberToOption(dr.getInt());
                optionOk = true;
            } catch (NoSuchOptionException ex){
                cp.printLine(ex.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException ignored){
                cp.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void rentVehicle(){
        Client client = null;
        Vehicles vehicle = null;
        String lastName = dr.createLastName();
        try{
            client = cr.getClients().get(lastName);
        } catch (NoSuchElementException ex){
            cp.printLine("Brak klienta o podanym nazwisku");
        }
        String vin = dr.createVin();
        try{
            vehicle = vr.getVehicles().get(vin);
        } catch (NoSuchElementException ex){
            cp.printLine("Brak pojazdu o wskazanym numerze VIN/seryjnym");
        }
        client.borrowVehicle(vehicle);
        vr.removeVehicle(vehicle);
        cp.printLine("Pojazd został wypożyczony");
    }

    private void returnVehicle(){
        Client client = null;
        Vehicles vehicle = null;
        String lastName = dr.createLastName();
        try{
            client = cr.getClients().get(lastName);
        } catch (NoSuchElementException ex){
            cp.printLine("Brak klienta o podanym nazwisku");
        }
        String vin = dr.createVin();
        try{
            for(Vehicles v: client.getBorrowedVehicles()){
                if(v.getVin().equals(vin)){
                    vehicle = v;
                }
            }
        } catch (NoSuchElementException ex){
            cp.printLine("Brak pojazdu o wskazanym numerze VIN/seryjnym wśród pożyczonych klientowi");
        }
        if(client.returnVehicle(vehicle)){
            client.addVehicleToHistory(vehicle);
            vr.addVehicles(vehicle);
            cp.printLine("Pojazd został zwrócony");
        } else
            System.out.println("Nie udało się zwrócić pojazdu");

    }

    private void showHistoryOfRentedCars(){
        cp.printLine("Podaj nazwisko klienta, którego historię wypożyczonych pojazdów chcesz zobaczyć: ");
        String lastName = dr.getString();
        Client client = cr.getClients().get(lastName);
        List<Vehicles> list = client.getVehicleRentalHistory();
        for(Vehicles l : list){
            cp.printLine(l.toString());
        }
    }

    private void showRentedCars(){
        cp.printLine("Podaj nazwisko klienta, którego aktualnie wypożyczone pojazdy chcesz zobaczyć: ");
        String lastName = dr.getString();
        Client client = cr.getClients().get(lastName);
        List<Vehicles> list = client.getBorrowedVehicles();
        for(Vehicles l : list){
            cp.printLine(l.toString());
        }
    }

    private enum RentOption {

        BACK(0, "Cofnij do głównego menu"),
        RENT_VEHICLE(1, "Wypożycz pojazd klientowi"),
        RETURN_VEHICLE(2, "Zwróć wypożyczony pojazd"),
        SHOW_HISTORY_OF_RENTED_CARS(3, "Wyświetl historię wypożyczanych przez klienta pojazdów"),
        SHOW_RENTED_CARS(4, "Wyświetl aktualnie wypożyczone przez klienta pojazdy");

        private int value;
        private String description;

        RentOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String toString() {
            return value + " - " + description;
        }

        static RentOption numberToOption(int number) throws NoSuchOptionException {
            try {
                return RentOption.values()[number];
            } catch (ArrayIndexOutOfBoundsException ex){
                throw new NoSuchOptionException("Nie ma takiej opcji w programie: " + number);
            }
        }
    }
}
