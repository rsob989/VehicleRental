package pl.carrental.app;

import pl.carrental.app.options.*;
import pl.carrental.exceptions.*;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;
import pl.carrental.io.file.FileManager;
import pl.carrental.io.file.FileManagerBuilder;
import pl.carrental.model.*;
import java.util.InputMismatchException;

public class RentalControl {
    private ConsolePrinter cp = new ConsolePrinter();
    private DataReader dr = new DataReader(cp);
    private FileManager fm;

    private VehiclesToRent vr;
    private ClientsRented cr;

    public RentalControl() {
        fm = new FileManagerBuilder(cp, dr).build();
        try {
            vr = fm.importVehicles();
            cr = fm.importClients();
            cp.printLine("Data imported from the file");
        } catch (DataImportException | InvalidDataException e){
            cp.printLine(e.getMessage());
            cp.printLine("New database initiated");
            vr = new VehiclesToRent();
            cr = new ClientsRented();
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
                case ADD:
                    add();
                    break;
                case SHOW:
                    show();
                    break;
                case DELETE:
                    delete();
                    break;
                case FIND:
                    find();
                    break;
                case RENT:
                    rent();
                    break;
                default:
                    cp.printLine("Select correct option. Try again!");
            }
        } while (option != Option.EXIT);
    }

    private void find(){
        FindOptions fo = new FindOptions(vr, cp, dr, cr);
        fo.findLoop();
    }

    private void rent(){
        RentOptions ro = new RentOptions(cp, dr, cr, vr);
        ro.rentLoop();
    }

    private void add(){
        AddOptions ao = new AddOptions(vr, cp, dr, cr);
        ao.addLoop();
    }

    private void show(){
        ShowOptions so = new ShowOptions(vr, cp, dr, cr);
        so.showLoop();
    }

    private void delete(){
        DeleteOptions delete = new DeleteOptions(vr, cp, dr);
        delete.deleteLoop();
    }

    private Option getOption(){
        boolean optionOk = false;
        Option option = null;
        while (!optionOk){
            try{
                option = Option.numberToOption(dr.getInt());
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
        for(Option o: Option.values()){
            cp.printLine(o.toString());
        }
    }

    private void exit(){
        try {
            fm.exportData(vr, cr);
            cp.printLine("Data exported successfully!");
        } catch (DataExportException e){
            cp.printLine(e.getMessage());
        }
        cp.printLine("See you soon!");
        dr.close();
    }

    private enum Option {

        EXIT(0, "Exit"),
        ADD(1, "Add options"),
        SHOW(2, "Show options"),
        DELETE(3, "Delete options"),
        FIND(4,"Search options"),
        RENT(5, "Rent options");

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
                throw new NoSuchOptionException("There is no such an option " + number);
            }
        }
    }
}
