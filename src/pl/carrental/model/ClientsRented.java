package pl.carrental.model;

import pl.carrental.exceptions.ClientAlreadyExistsException;
import pl.carrental.exceptions.VehicleAlreadyExistsException;
import pl.carrental.model.client.Client;
import pl.carrental.model.vehicle.Vehicles;

import java.io.Serializable;
import java.util.*;

public class ClientsRented implements Serializable {

    private Map<String, Client> clients = new HashMap<>();

    public Map<String, Client> getClients() {
        return clients;
    }


    public void addClient(Client client){
        if(clients.containsKey(client.getLastName()))
            throw new ClientAlreadyExistsException("Użytkownik ze wskazanym nazwiskiem już istnieje " + client.getLastName());
        clients.put(client.getLastName(), client);
    }

    public Collection<Client> getSortedClients(Comparator<Client> comparator){
        ArrayList<Client> list = new ArrayList<>(clients.values());
        list.sort(comparator);
        return list;
    }

    public Optional<Client> findByLastName(String lastName){
        return Optional.ofNullable(clients.get(lastName));
    }
}
