package fr.ekwateur.service.consumption;

import fr.ekwateur.model.consumption.Consumption;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.model.client.Client;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public interface ConsumptionService {
    BigDecimal getElectricityConsumption(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException;

    BigDecimal getGasConsumption(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException;

    boolean addConsumption(Consumption consumption);

    List<Consumption> findAllByClientReference(String clientReference) throws ClientNotFoundException;
}
