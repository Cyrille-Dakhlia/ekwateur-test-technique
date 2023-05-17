package fr.ekwateur.service.invoicing;

import java.math.BigDecimal;

public enum PriceCategory {
    PRIX_PARTICULIER(BigDecimal.valueOf(0.121), BigDecimal.valueOf(0.115)),
    PRIX_PROFESSIONNEL_CA_SUP_1M(BigDecimal.valueOf(0.114), BigDecimal.valueOf(0.111)),
    PRIX_PROFESSIONNEL_CA_INF_1M(BigDecimal.valueOf(0.118), BigDecimal.valueOf(0.113));

    private BigDecimal priceOfElectricityPerKWh;
    private BigDecimal priceOfGasPerKWh;

    PriceCategory(BigDecimal priceOfElectricityPerKWh, BigDecimal priceOfGasPerKWh) {
        this.priceOfElectricityPerKWh = priceOfElectricityPerKWh;
        this.priceOfGasPerKWh = priceOfGasPerKWh;
    }

    public BigDecimal getPriceOfElectricityPerKWh() {
        return priceOfElectricityPerKWh;
    }

    public void setPriceOfElectricityPerKWh(BigDecimal priceOfElectricityPerKWh) {
        this.priceOfElectricityPerKWh = priceOfElectricityPerKWh;
    }

    public BigDecimal getPriceOfGasPerKWh() {
        return priceOfGasPerKWh;
    }

    public void setPriceOfGasPerKWh(BigDecimal priceOfGasPerKWh) {
        this.priceOfGasPerKWh = priceOfGasPerKWh;
    }
}
