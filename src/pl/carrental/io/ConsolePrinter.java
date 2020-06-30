package pl.carrental.io;

import pl.carrental.model.*;

import java.util.Collection;

public class ConsolePrinter {

    public void printCars(Collection<Vehicles> vehicles) {
        long count = vehicles.stream()
                .filter(p->p instanceof Car)
                .map(Vehicles::toString)
                .peek(this::printLine)
                .count();
        if(count == 0)
            printLine("Brak samochodów w wypożyczalni");
    }

    public void printCaravans(Collection<Vehicles> vehicles) {
        long count = vehicles.stream()
                .filter(p->p instanceof Caravan)
                .map(Vehicles::toString)
                .peek(this::printLine)
                .count();
        if (count == 0) {
            System.out.println("Brak samochodów campingowych w wypożyczalni");
        }
    }

    public void printBikes(Collection<Vehicles> vehicles) {
        long count = vehicles.stream()
                .filter(p->p instanceof Bike)
                .map(Vehicles::toString)
                .peek(this::printLine)
                .count();
        if (count == 0) {
            System.out.println("Brak rowerów w wypożyczalni");
        }
    }

    public void printPrivateClients(Collection<PrivateClient> clients){
        clients.stream()
                .map(Client::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text){
        System.out.println(text);
    }

}