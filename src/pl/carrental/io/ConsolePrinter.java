package pl.carrental.io;

import pl.carrental.model.client.BusinessClient;
import pl.carrental.model.client.Client;
import pl.carrental.model.client.PrivateClient;
import pl.carrental.model.vehicle.Bike;
import pl.carrental.model.vehicle.Car;
import pl.carrental.model.vehicle.Caravan;
import pl.carrental.model.vehicle.Vehicles;

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
            printLine("Brak samochodów campingowych w wypożyczalni");
        }
    }

    public void printBikes(Collection<Vehicles> vehicles) {
        long count = vehicles.stream()
                .filter(p->p instanceof Bike)
                .map(Vehicles::toString)
                .peek(this::printLine)
                .count();
        if (count == 0) {
            printLine("Brak rowerów w wypożyczalni");
        }
    }

    public void printPrivateClients(Collection<Client> clients){
        long count = clients.stream()
                .filter(p->p instanceof PrivateClient)
                .map(Client::toString)
                .peek(this::printLine)
                .count();
        if(count == 0) {
            printLine("Brak klientów prywatnych w bazie danych");
        }
    }

    public void printBusinessClients(Collection<Client> clients){
        long count = clients.stream()
                .filter(p->p instanceof BusinessClient)
                .map(Client::toString)
                .peek(this::printLine)
                .count();
        if(count == 0) {
            printLine("Brak klientów biznesowych w bazie danych");
        }
    }

    public void printLine(String text){
        System.out.println(text);
    }

}