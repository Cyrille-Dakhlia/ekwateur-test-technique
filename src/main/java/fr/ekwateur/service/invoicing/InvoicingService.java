package fr.ekwateur.service.invoicing;

import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;

import java.math.BigDecimal;
import java.time.YearMonth;

public interface InvoicingService {

    BigDecimal calculateInvoice(Client client, YearMonth yearMonth) throws ClientNotFoundException, ConsumptionNotFoundException;
}
