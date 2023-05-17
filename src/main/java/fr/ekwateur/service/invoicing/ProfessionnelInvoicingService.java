package fr.ekwateur.service.invoicing;

import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.client.ProfessionnelClient;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.service.consumption.ConsumptionService;

import java.math.BigDecimal;
import java.time.YearMonth;

public class ProfessionnelInvoicingService implements InvoicingService {

    private final ConsumptionService consumptionService;

    public ProfessionnelInvoicingService(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @Override
    public BigDecimal calculateInvoice(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException {
        BigDecimal electricityConsumption = consumptionService.getElectricityConsumption(client, yearMonth);
        BigDecimal gasConsumption = consumptionService.getGasConsumption(client, yearMonth);

        BigDecimal electricityPrice = ((ProfessionnelClient) client).getRevenue() >= 1_000_000
                ? PriceCategory.PRIX_PROFESSIONNEL_CA_SUP_1M.getPriceOfElectricityPerKWh()
                : PriceCategory.PRIX_PROFESSIONNEL_CA_INF_1M.getPriceOfElectricityPerKWh();
        BigDecimal gasPrice = ((ProfessionnelClient) client).getRevenue() >= 1_000_000
                ? PriceCategory.PRIX_PROFESSIONNEL_CA_SUP_1M.getPriceOfGasPerKWh()
                : PriceCategory.PRIX_PROFESSIONNEL_CA_INF_1M.getPriceOfGasPerKWh();

        return electricityConsumption.multiply(electricityPrice).add(gasConsumption.multiply(gasPrice));
    }
}
