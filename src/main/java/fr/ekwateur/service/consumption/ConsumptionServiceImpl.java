package fr.ekwateur.service.consumption;

import fr.ekwateur.model.EnergyType;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.consumption.Consumption;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.repository.ConsumptionRepository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public class ConsumptionServiceImpl implements ConsumptionService {

    private final ConsumptionRepository consumptionRepository;

    public ConsumptionServiceImpl(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @Override
    public BigDecimal getElectricityConsumption(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException {
        Consumption consumption = consumptionRepository.findByClientReferenceAndYearMonthAndEnergyType(
                client.getClientReference(),
                yearMonth,
                EnergyType.ELECTRICITY
        );
        return consumption.getQuantity();
    }

    @Override
    public BigDecimal getGasConsumption(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException {
        Consumption consumption = consumptionRepository.findByClientReferenceAndYearMonthAndEnergyType(
                client.getClientReference(),
                yearMonth,
                EnergyType.GAS
        );
        return consumption.getQuantity();
    }

    @Override
    public boolean addConsumption(Consumption consumption) {
        return consumptionRepository.add(consumption);
    }

    @Override
    public List<Consumption> findAllByClientReference(String clientReference) throws ClientNotFoundException {
        return consumptionRepository.findAllByClientReference(clientReference);
    }
}
