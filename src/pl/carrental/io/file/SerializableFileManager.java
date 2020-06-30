package pl.carrental.io.file;

import pl.carrental.exceptions.DataExportException;
import pl.carrental.exceptions.DataImportException;
import pl.carrental.model.VehicleRental;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "VehicleRental.o";

    @Override
    public VehicleRental importData() {
        try(
                FileInputStream fis = new FileInputStream(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ){
            VehicleRental vr = (VehicleRental)ois.readObject();
            return vr;
        } catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku " + FILE_NAME);
        } catch (IOException e){
            throw new DataImportException("Błąd odczytu pliku " + FILE_NAME);
        } catch (ClassNotFoundException e){
            throw new DataImportException("Niezgodny typ danych w pliku " + FILE_NAME);
        }
    }

    @Override
    public void exportData(VehicleRental vr) {
        try(
                FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ){
            oos.writeObject(vr);
        } catch (FileNotFoundException e){
            throw new DataExportException("Brak pliku " + FILE_NAME);
        } catch (IOException e){
            throw new DataExportException("Błąd zapisu danych do pliku " + FILE_NAME);
        }

    }
}
