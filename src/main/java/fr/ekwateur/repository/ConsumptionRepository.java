package fr.ekwateur.repository;

import fr.ekwateur.model.EnergyType;
import fr.ekwateur.model.consumption.Consumption;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

public class ConsumptionRepository {
    private final String PARTICULIER_CLIENT_REF = "EKW11111110";
    private final String PROFESSIONNEL_CLIENT_REF_CA_SUP_1M = "EKW22222220";
    private final String PROFESSIONNEL_CLIENT_REF_CA_INF_1M = "EKW33333330";

    private Map<String, List<Consumption>> mapClientReferenceToConsumption = new HashMap<>() {{
        put(PARTICULIER_CLIENT_REF,
                new ArrayList<>(
                        Arrays.asList(
                                new Consumption(
                                        PARTICULIER_CLIENT_REF,
                                        YearMonth.of(2023, 4),
                                        EnergyType.ELECTRICITY,
                                        BigDecimal.valueOf(182)
                                ),
                                new Consumption(
                                        PARTICULIER_CLIENT_REF,
                                        YearMonth.of(2023, 4),
                                        EnergyType.GAS,
                                        BigDecimal.valueOf(340)
                                ),
                                new Consumption(
                                        PARTICULIER_CLIENT_REF,
                                        YearMonth.of(2023, 5),
                                        EnergyType.ELECTRICITY,
                                        BigDecimal.valueOf(175)
                                ),
                                new Consumption(
                                        PARTICULIER_CLIENT_REF,
                                        YearMonth.of(2023, 5),
                                        EnergyType.GAS,
                                        BigDecimal.valueOf(287)
                                )
                        )
                ));
        put(PROFESSIONNEL_CLIENT_REF_CA_SUP_1M,
                new ArrayList<>(
                        Arrays.asList(
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_SUP_1M,
                                        YearMonth.of(2023, 4),
                                        EnergyType.ELECTRICITY,
                                        BigDecimal.valueOf(2800)
                                ),
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_SUP_1M,
                                        YearMonth.of(2023, 4),
                                        EnergyType.GAS,
                                        BigDecimal.valueOf(2875)
                                ),
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_SUP_1M,
                                        YearMonth.of(2023, 5),
                                        EnergyType.ELECTRICITY,
                                        BigDecimal.valueOf(2758)
                                ),
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_SUP_1M,
                                        YearMonth.of(2023, 5),
                                        EnergyType.GAS,
                                        BigDecimal.valueOf(2689)
                                )
                        )
                ));
        put(PROFESSIONNEL_CLIENT_REF_CA_INF_1M,
                new ArrayList<>(
                        Arrays.asList(
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_INF_1M,
                                        YearMonth.of(2023, 4),
                                        EnergyType.ELECTRICITY,
                                        BigDecimal.valueOf(375)
                                ),
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_INF_1M,
                                        YearMonth.of(2023, 4),
                                        EnergyType.GAS,
                                        BigDecimal.valueOf(350)
                                ),
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_INF_1M,
                                        YearMonth.of(2023, 5),
                                        EnergyType.ELECTRICITY,
                                        BigDecimal.valueOf(372)
                                ),
                                new Consumption(
                                        PROFESSIONNEL_CLIENT_REF_CA_INF_1M,
                                        YearMonth.of(2023, 5),
                                        EnergyType.GAS,
                                        BigDecimal.valueOf(350)
                                )
                        )
                ));
    }};


    public List<Consumption> findAllByClientReference(String clientReference) throws ClientNotFoundException {
        List<Consumption> consumptions = this.mapClientReferenceToConsumption.get(clientReference);
        if (consumptions == null) {
            throw new ClientNotFoundException("This client reference is not defined, or this client does not have any consumption for this period: " + clientReference);
        }
        return consumptions;
    }

    public Consumption findByClientReferenceAndYearMonthAndEnergyType(String clientReference, YearMonth yearMonth, EnergyType energyType) throws ClientNotFoundException, ConsumptionNotFoundException {
        List<Consumption> consumptions = this.mapClientReferenceToConsumption.get(clientReference);
        if (consumptions == null) {
            throw new ClientNotFoundException("This client reference is not defined: " + clientReference);
        }
        for (Consumption consumption : consumptions) {
            if (consumption.getYearMonth().equals(yearMonth) && consumption.getEnergyType().equals(energyType)) {
                return consumption;
            }
        }

        // If no data is found, we throw an exception instead of returning a consumption of 0, in the case where the consumption data hasn't been persisted in database
        throw new ConsumptionNotFoundException(String.format("No %s consumption data has been found for client %s for the period of %s.",
                energyType.name(), clientReference, yearMonth.toString()));
    }

    public boolean add(Consumption consumption) {
        List<Consumption> consumptions = this.mapClientReferenceToConsumption.get(consumption.getClientReference());

        if (consumptions == null) {
            this.mapClientReferenceToConsumption.put(consumption.getClientReference(), new ArrayList<>(List.of(consumption)));
            return true;
        } else {
            if (consumptions.stream()
                    .anyMatch(c -> c.getYearMonth().equals(consumption.getYearMonth()) && c.getEnergyType().equals(consumption.getEnergyType()))) {
                return false;
            } else {
                return consumptions.add(consumption);
            }
        }
    }
}
