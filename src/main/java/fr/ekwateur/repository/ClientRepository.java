package fr.ekwateur.repository;

import fr.ekwateur.model.client.Civilite;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.client.ParticulierClient;
import fr.ekwateur.model.client.ProfessionnelClient;
import fr.ekwateur.model.exception.ClientNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientRepository {
    private List<Client> clientList = new ArrayList<>(Arrays.asList(
            new ParticulierClient.Builder("EKW11111110")
                    .withCivilite(Civilite.Monsieur)
                    .withPrenom("Jay")
                    .withNom("Conomise").build(),
            new ProfessionnelClient.Builder("EKW22222220")
                    .withSiretNumber("00112233445566")
                    .withBusinessName("A Rich Company")
                    .withRevenue(2_000_000L).build(),
            new ProfessionnelClient.Builder("EKW33333330")
                    .withSiretNumber("66554433221100")
                    .withBusinessName("A Not So Rich Company")
                    .withRevenue(2_000L).build()
    ));

    public List<Client> findAllClients() {
        return this.clientList;
    }

    public Client findClientByClientReference(String clientReference) throws ClientNotFoundException {
        for (Client client : this.clientList) {
            if (client.getClientReference().equals(clientReference)) {
                return client;
            }
        }
        throw new ClientNotFoundException("This client reference is not defined: " + clientReference);
    }

    public boolean addClient(Client client) {
        boolean clientExists = this.clientList.stream().anyMatch(c -> c.getClientReference().equals(client.getClientReference()));

        if (clientExists) return false;
        else return this.clientList.add(client);
    }
}
