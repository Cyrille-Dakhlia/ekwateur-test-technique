package fr.ekwateur.service.invoicing;

import fr.ekwateur.model.client.ClientType;
import fr.ekwateur.service.consumption.ConsumptionService;

public class InvoicingServiceFactory {

    public static InvoicingService createInvoicingService(ClientType clientType, ConsumptionService consumptionService) {
        if (clientType == null) {
            throw new IllegalArgumentException("ClientType cannot be null.");
        }
        switch (clientType) {
            case PROFESSIONNEL:
                return new ProfessionnelInvoicingService(consumptionService);
            case PARTICULIER:
                return new ParticulierInvoicingService(consumptionService);
            default:
                throw new IllegalArgumentException("Client type not covered:" + clientType);
        }
    }
}
