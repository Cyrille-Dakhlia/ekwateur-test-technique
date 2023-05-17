package fr.ekwateur.service.client;

import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {
    List<Client> findAllClients();
    Client findClientByClientReference(String clientReference) throws ClientNotFoundException;

    boolean addClient(Client client);
}
