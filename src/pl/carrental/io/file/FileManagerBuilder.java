package pl.carrental.io.file;

import pl.carrental.exceptions.NoSuchFileTypeException;
import pl.carrental.io.ConsolePrinter;
import pl.carrental.io.DataReader;

public class FileManagerBuilder {

    private ConsolePrinter printer;
    private DataReader dataReader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader dataReader) {
        this.printer = printer;
        this.dataReader = dataReader;
    }

    public FileManager build(){
        printer.printLine("Select data format:");
        FileType fileType = getFileType();
        switch (fileType){
            case CSV:
                return new CsvFileManager();
            case SERIAL:
                return new SerializableFileManager();
            default:
                throw new NoSuchFileTypeException("Not supported data type");
        }
    }

    private FileType getFileType(){
        boolean isOk = false;
        FileType result = null;
        do{
            printTypes();
            String type = dataReader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                isOk = true;
            } catch (IllegalArgumentException e){
                printer.printLine("Not supported data type, try again!");
            }
        } while(!isOk);
        return result;
    }

    private void printTypes(){
        for(FileType f: FileType.values()){
            printer.printLine(f.name());
        }
    }
}
