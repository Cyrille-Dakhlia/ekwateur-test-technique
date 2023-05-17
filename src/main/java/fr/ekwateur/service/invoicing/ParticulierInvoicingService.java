package fr.ekwateur.service.invoicing;

import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.service.consumption.ConsumptionService;

import java.math.BigDecimal;
import java.time.YearMonth;

public class ParticulierInvoicingService implements InvoicingService {

    private final ConsumptionService consumptionService;

    public ParticulierInvoicingService(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @Override
    public BigDecimal calculateInvoice(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException {
        BigDecimal electricityConsumption = consumptionService.getElectricityConsumption(client, yearMonth);
        BigDecimal gasConsumption = consumptionService.getGasConsumption(client, yearMonth);

        return electricityConsumption.multiply(PriceCategory.PRIX_PARTICULIER.getPriceOfElectricityPerKWh())
                .add(gasConsumption.multiply(PriceCategory.PRIX_PARTICULIER.getPriceOfGasPerKWh()));
    }
}
