package fr.ekwateur.service.client;

import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.repository.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAllClients();
    }

    @Override
    public Client findClientByClientReference(String clientReference) throws ClientNotFoundException {
        return clientRepository.findClientByClientReference(clientReference);
    }

    @Override
    public boolean addClient(Client client) {
        return clientRepository.addClient(client);
    }
}
