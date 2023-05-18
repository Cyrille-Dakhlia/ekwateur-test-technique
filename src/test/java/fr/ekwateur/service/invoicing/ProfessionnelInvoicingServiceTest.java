package fr.ekwateur.service.invoicing;

import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.client.ProfessionnelClient;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.service.consumption.ConsumptionService;
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
public class ProfessionnelInvoicingServiceTest {

    @Mock
    private ConsumptionService consumptionService;
    private ProfessionnelInvoicingService invoicingService;

    @Before
    public void setUp() {
        invoicingService = new ProfessionnelInvoicingService(consumptionService);
    }

    @Test
    public void should_accurately_calculate_invoice_for_professional_with_revenue_over_1M() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        Client inputClient = new ProfessionnelClient.Builder("").withRevenue(1_000_000).build();
        YearMonth inputYearMonth = YearMonth.of(2023,5);
        BigDecimal inputElectricityConsumption = BigDecimal.valueOf(1000);
        BigDecimal inputGasConsumption = BigDecimal.valueOf(500);
        given(consumptionService.getElectricityConsumption(inputClient, inputYearMonth))
                .willReturn(inputElectricityConsumption);
        given(consumptionService.getGasConsumption(inputClient, inputYearMonth))
                .willReturn(inputGasConsumption);

        // When
        BigDecimal resultingInvoicePrice = invoicingService.calculateInvoice(inputClient, inputYearMonth);

        // Then
        BigDecimal expectedInvoicePriceForElectricity = inputElectricityConsumption.multiply(PriceCategory.PRIX_PROFESSIONNEL_CA_SUP_1M.getPriceOfElectricityPerKWh());
        BigDecimal expectedInvoicePriceForGas = inputGasConsumption.multiply(PriceCategory.PRIX_PROFESSIONNEL_CA_SUP_1M.getPriceOfGasPerKWh());
        assertEquals(expectedInvoicePriceForElectricity.add(expectedInvoicePriceForGas), resultingInvoicePrice);
    }

    @Test
    public void should_accurately_calculate_invoice_for_professional_with_revenue_under_1M() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        Client inputClient = new ProfessionnelClient.Builder("").withRevenue(1_000).build();
        YearMonth inputYearMonth = YearMonth.of(2023,5);
        BigDecimal inputElectricityConsumption = BigDecimal.valueOf(1000);
        BigDecimal inputGasConsumption = BigDecimal.valueOf(500);
        given(consumptionService.getElectricityConsumption(inputClient, inputYearMonth))
                .willReturn(inputElectricityConsumption);
        given(consumptionService.getGasConsumption(inputClient, inputYearMonth))
                .willReturn(inputGasConsumption);

        // When
        BigDecimal resultingInvoicePrice = invoicingService.calculateInvoice(inputClient, inputYearMonth);

        // Then
        BigDecimal expectedInvoicePriceForElectricity = inputElectricityConsumption.multiply(PriceCategory.PRIX_PROFESSIONNEL_CA_INF_1M.getPriceOfElectricityPerKWh());
        BigDecimal expectedInvoicePriceForGas = inputGasConsumption.multiply(PriceCategory.PRIX_PROFESSIONNEL_CA_INF_1M.getPriceOfGasPerKWh());
        assertEquals(expectedInvoicePriceForElectricity.add(expectedInvoicePriceForGas), resultingInvoicePrice);
    }

    @Test
    public void should_throw_if_client_not_found_for_electricity_consumption() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        given(consumptionService.getElectricityConsumption(any(), any()))
                .willThrow(ClientNotFoundException.class);

        // When - Then
        ProfessionnelClient client = new ProfessionnelClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        assertThrows(ClientNotFoundException.class,
                () -> invoicingService.calculateInvoice(client, yearMonth));
    }

    @Test
    public void should_throw_if_client_not_found_for_gas_consumption() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        given(consumptionService.getGasConsumption(any(), any()))
                .willThrow(ClientNotFoundException.class);

        // When - Then
        ProfessionnelClient client = new ProfessionnelClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        assertThrows(ClientNotFoundException.class,
                () -> invoicingService.calculateInvoice(client, yearMonth));
    }

    @Test
    public void should_throw_if_electricity_consumption_not_found_for_client() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        given(consumptionService.getElectricityConsumption(any(), any()))
                .willThrow(ConsumptionNotFoundException.class);

        // When - Then
        ProfessionnelClient client = new ProfessionnelClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        assertThrows(ConsumptionNotFoundException.class,
                () -> invoicingService.calculateInvoice(client, yearMonth));
    }

    @Test
    public void should_throw_if_gas_consumption_not_found_for_client() throws ClientNotFoundException, ConsumptionNotFoundException {
        // Given
        given(consumptionService.getGasConsumption(any(), any()))
                .willThrow(ConsumptionNotFoundException.class);

        // When - Then
        ProfessionnelClient client = new ProfessionnelClient.Builder("").build();
        YearMonth yearMonth = YearMonth.of(2023, 5);
        assertThrows(ConsumptionNotFoundException.class,
                () -> invoicingService.calculateInvoice(client, yearMonth));
    }
}