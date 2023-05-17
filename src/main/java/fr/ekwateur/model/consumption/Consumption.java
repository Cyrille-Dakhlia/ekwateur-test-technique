package fr.ekwateur.model.consumption;

import fr.ekwateur.model.EnergyType;

import java.math.BigDecimal;
import java.time.YearMonth;

public class Consumption {
    private final String clientReference;
    private final YearMonth yearMonth;
    private final EnergyType energyType;
    private final BigDecimal quantity;

    public Consumption(String clientReference, YearMonth yearMonth, EnergyType energyType, BigDecimal quantity) {
        this.clientReference = clientReference;
        this.yearMonth = yearMonth;
        this.energyType = energyType;
        this.quantity = quantity;
    }

    public String getClientReference() {
        return clientReference;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "clientReference='" + clientReference + '\'' +
                ", yearMonth=" + yearMonth +
                ", energyType=" + energyType +
                ", quantity=" + quantity +
                '}';
    }
}
