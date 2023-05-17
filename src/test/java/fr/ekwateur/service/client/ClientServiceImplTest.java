package fr.ekwateur.service.client;

import fr.ekwateur.model.client.Civilite;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.client.ParticulierClient;
import fr.ekwateur.model.client.ProfessionnelClient;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {
    @Mock
    private ClientRepository repository;
    private ClientServiceImpl clientService;

    @Before
    public void setUp() throws Exception {
        clientService = new ClientServiceImpl(repository);
    }

    @Test
    public void should_find_all_clients() {
        // Given
        List<Client> inputClientList = Arrays.asList(
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
        );
        given(repository.findAllClients())
                .willReturn(inputClientList);

        // When
        List<Client> resultingList = clientService.findAllClients();

        // Then
        assertEquals(inputClientList, resultingList);
    }

    @Test
    public void should_find_client_with_client_reference() throws ClientNotFoundException {
        // Given
        ParticulierClient inputClient = new ParticulierClient.Builder("EKW11111110")
                .withCivilite(Civilite.Monsieur)
                .withPrenom("Jay")
                .withNom("Conomise").build();
        Client mockClient = new ParticulierClient.Builder(inputClient.getClientReference())
                .withCivilite(inputClient.getCivilite())
                .withPrenom(inputClient.getPrenom())
                .withNom(inputClient.getNom()).build();
        given(repository.findClientByClientReference(inputClient.getClientReference()))
                .willReturn(mockClient);

        // When
        Client resultingClient = clientService.findClientByClientReference(inputClient.getClientReference());

        // Then
        assertEquals(inputClient, resultingClient);
    }

    @Test
    public void should_throw_if_client_repository_cannot_find_client() throws ClientNotFoundException {
        // Given
        given(repository.findClientByClientReference(any()))
                .willThrow(ClientNotFoundException.class);

        // When - Then
        assertThrows(ClientNotFoundException.class,
                () -> clientService.findClientByClientReference(""));
    }

    @Test
    public void should_add_new_client() {
        // Given
        Client client = new ParticulierClient.Builder("").build();

        // When
        clientService.addClient(client);

        // Then
        verify(repository).addClient(client);
    }
}