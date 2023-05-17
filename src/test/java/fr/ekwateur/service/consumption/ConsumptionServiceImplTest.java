package fr.ekwateur.service.consumption;

import fr.ekwateur.model.EnergyType;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.client.ParticulierClient;
import fr.ekwateur.model.client.ProfessionnelClient;
import fr.ekwateur.model.consumption.Consumption;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.repository.ConsumptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsumptionServiceImplTest {

    @Mock
    private ConsumptionRepository repository;
    private ConsumptionServiceImpl consumptionService;

    @Before
    public void setUp() throws Exception {
        consumptionService = new ConsumptionServiceImpl(repository);
    }

    @Test
    public void should_get_electricity_consumption() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        Client client = new ParticulierClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        EnergyType energyType = EnergyType.ELECTRICITY;
        BigDecimal inputQuantity = BigDecimal.valueOf(900);
        Consumption inputConsumption = new Consumption(client.getClientReference(), yearMonth, energyType, inputQuantity);
        given(repository.findByClientReferenceAndYearMonthAndEnergyType(client.getClientReference(), yearMonth, energyType))
                .willReturn(inputConsumption);

        // When
        BigDecimal resultingQuantity = consumptionService.getElectricityConsumption(client, yearMonth);

        // Then
        assertEquals(inputQuantity, resultingQuantity);
    }

    @Test
    public void should_get_gas_consumption() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        Client client = new ParticulierClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        EnergyType energyType = EnergyType.GAS;
        BigDecimal inputQuantity = BigDecimal.valueOf(900);
        Consumption inputConsumption = new Consumption(client.getClientReference(), yearMonth, energyType, inputQuantity);
        given(repository.findByClientReferenceAndYearMonthAndEnergyType(client.getClientReference(), yearMonth, energyType))
                .willReturn(inputConsumption);

        // When
        BigDecimal resultingQuantity = consumptionService.getGasConsumption(client, yearMonth);

        // Then
        assertEquals(inputQuantity, resultingQuantity);
    }

    @Test
    public void should_throw_if_consumption_repository_cannot_find_client() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        given(repository.findByClientReferenceAndYearMonthAndEnergyType(any(), any(), any()))
                .willThrow(ClientNotFoundException.class);

        // When - Then
        ProfessionnelClient client = new ProfessionnelClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        assertThrows(ClientNotFoundException.class,
                () -> consumptionService.getElectricityConsumption(client, yearMonth));
        assertThrows(ClientNotFoundException.class,
                () -> consumptionService.getGasConsumption(client, yearMonth));
    }

    @Test
    public void should_throw_if_consumption_repository_cannot_find_consumption() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        given(repository.findByClientReferenceAndYearMonthAndEnergyType(any(), any(), any()))
                .willThrow(ConsumptionNotFoundException.class);

        // When - Then
        ProfessionnelClient client = new ProfessionnelClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        assertThrows(ConsumptionNotFoundException.class,
                () -> consumptionService.getElectricityConsumption(client, yearMonth));
        assertThrows(ConsumptionNotFoundException.class,
                () -> consumptionService.getGasConsumption(client, yearMonth));
    }

}