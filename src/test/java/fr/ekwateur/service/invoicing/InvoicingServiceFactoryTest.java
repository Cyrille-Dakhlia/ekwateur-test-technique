package fr.ekwateur.service.invoicing;

import fr.ekwateur.model.client.ClientType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class InvoicingServiceFactoryTest {

    @Test
    public void should_return_instance_of_professionnel_invoicing_service() {
        // Arrange
        ClientType professional = ClientType.PROFESSIONNEL;

        // Act
        InvoicingService invoicingService = InvoicingServiceFactory.createInvoicingService(professional, null);

        // Assert
        assertEquals(invoicingService.getClass(), ProfessionnelInvoicingService.class);
    }

    @Test
    public void should_return_instance_of_particulier_invoicing_service() {
        // Arrange
        ClientType individual = ClientType.PARTICULIER;

        // Act
        InvoicingService invoicingService = InvoicingServiceFactory.createInvoicingService(individual, null);

        // Assert
        assertEquals(invoicingService.getClass(), ParticulierInvoicingService.class);
    }

    @Test
    public void should_throw_exception_when_client_type_is_null() {
        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> InvoicingServiceFactory.createInvoicingService(null, null));
    }

}