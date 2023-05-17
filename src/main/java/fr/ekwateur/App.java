package fr.ekwateur;

import fr.ekwateur.model.EnergyType;
import fr.ekwateur.model.client.Civilite;
import fr.ekwateur.model.client.Client;
import fr.ekwateur.model.client.ParticulierClient;
import fr.ekwateur.model.client.ProfessionnelClient;
import fr.ekwateur.model.consumption.Consumption;
import fr.ekwateur.model.exception.ClientNotFoundException;
import fr.ekwateur.model.exception.ConsumptionNotFoundException;
import fr.ekwateur.repository.ClientRepository;
import fr.ekwateur.repository.ConsumptionRepository;
import fr.ekwateur.service.client.ClientService;
import fr.ekwateur.service.client.ClientServiceImpl;
import fr.ekwateur.service.consumption.ConsumptionService;
import fr.ekwateur.service.consumption.ConsumptionServiceImpl;
import fr.ekwateur.service.invoicing.InvoicingService;
import fr.ekwateur.service.invoicing.InvoicingServiceFactory;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

public class App
{

    public static void main( String[] args )
    {
        ClientService clientService = new ClientServiceImpl(new ClientRepository());
        ConsumptionService consumptionService = new ConsumptionServiceImpl(new ConsumptionRepository());

        Scanner sc = new Scanner(System.in);
        boolean continuer = true;

        do {
            printMenu();

            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1: // Lister tous les clients
                    processListAllClientsRequest(clientService);
                    break;
                case 2:
                    processListAllConsumptionsForClient(consumptionService, sc);
                    break;
                case 3: // Ajouter un nouveau client
                    processAddNewClientRequest(clientService, sc);
                    break;
                case 4: // Ajouter une consommation pour un client
                    processAddNewConsumptionRequest(clientService, consumptionService, sc);
                    break;
                case 5: // Calculer le montant de facturation d'un client
                    processCalculateInvoice(clientService, consumptionService, sc);
                    break;
                case 6:
                    continuer = false;
                    break;
            }

        } while (continuer);

        sc.close();
    }

    private static void printMenu() {
        System.out.println("Que désirez-vous faire ?" +
                "\n1. Lister tous les clients." +
                "\n2. Lister toutes les consommations d'un clients." +
                "\n3. Ajouter un nouveau client" +
                "\n4. Ajouter une consommation pour un client" +
                "\n5. Calculer le montant de facturation d'un client" +
                "\n6. Quitter");
    }

    private static void processListAllClientsRequest(ClientService clientService) {
        List<Client> allClients = clientService.findAllClients();
        allClients.forEach(System.out::println);
    }

    private static void processListAllConsumptionsForClient(ConsumptionService consumptionService, Scanner sc) {
        System.out.println("Référence du client (\"EKW12345678\") : ");
        String clientReference = sc.nextLine();

        try {
            List<Consumption> allConsumptions = consumptionService.findAllByClientReference(clientReference);
            allConsumptions.forEach(System.out::println);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void processAddNewClientRequest(ClientService clientService, Scanner sc) {
        System.out.println("Référence du client (\"EKW12345678\") : ");
        String clientReference = sc.nextLine();

        System.out.println("Type du client : \n1. PARTICULIER \n2. PROFESSIONNEL");
        int clientType = sc.nextInt();
        sc.nextLine();

        Client newClient;

        switch (clientType) {
            case 1:
                System.out.println("Civilité : \n1. Monsieur \n2. Madame");
                int civiliteChoice = sc.nextInt();
                sc.nextLine();

                System.out.println("Nom : ");
                String nom = sc.nextLine();

                System.out.println("Prénom : ");
                String prenom = sc.nextLine();

                newClient = new ParticulierClient.Builder(clientReference)
                        .withCivilite(civiliteChoice == 1 ? Civilite.Monsieur : Civilite.Madame)
                        .withNom(nom)
                        .withPrenom(prenom)
                        .build();
                break;
            case 2:
                System.out.println("N° SIRET : ");
                String siret = sc.nextLine();

                System.out.println("Raison Sociale : ");
                String businessName = sc.nextLine();

                System.out.println("Chiffre d'Affaires : ");
                String revenue = sc.nextLine();

                newClient = new ProfessionnelClient.Builder(clientReference)
                        .withSiretNumber(siret)
                        .withBusinessName(businessName)
                        .withRevenue(Long.parseLong(revenue))
                        .build();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clientType);
        }
        boolean success = clientService.addClient(newClient);
        String message = success
                ? "Le client a bien été ajouté."
                : "Ce client n'a pas pu être ajouté, vérifiez les informations que vous avez entrées.";
        System.out.println(message);
    }

    private static void processAddNewConsumptionRequest(ClientService clientService, ConsumptionService consumptionService, Scanner sc) {
        System.out.println("Référence du client (\"EKW12345678\") : ");
        String clientReference = sc.nextLine();

        try {
            clientService.findClientByClientReference(clientReference);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Année : ");
        int year = sc.nextInt();
        sc.nextLine();

        int month = 0;
        do {
            System.out.println("Mois (1 pour janvier) : ");
            month = sc.nextInt();
            sc.nextLine();
        } while(month < 1 || 12 < month);

        System.out.println("Type d'énergie : \n1. ELECTRICITE \n2. GAZ");
        int energyType = sc.nextInt();
        sc.nextLine();

        System.out.println("Quantité consommée : ");
        String quantity = sc.nextLine();

        Consumption newConsumption = new Consumption(
                clientReference,
                YearMonth.of(year, month),
                energyType == 1 ? EnergyType.ELECTRICITY : EnergyType.GAS,
                new BigDecimal(quantity)
        );

        boolean success = consumptionService.addConsumption(newConsumption);
        String message = success
                ? "La consommation pour ce client a bien été ajoutée."
                : "Il existe déjà une consommation pour cette pérdioe et ce type d'énergie.";
        System.out.println(message);
    }

    private static void processCalculateInvoice(ClientService clientService, ConsumptionService consumptionService, Scanner sc) {
        System.out.println("Référence du client (\"EKW12345678\") : ");
        String clientReference = sc.nextLine();

        Client client;
        try {
            client = clientService.findClientByClientReference(clientReference);
        } catch (ClientNotFoundException e) {
            System.out.println("Cette référence client n'existe pas : " + clientReference);
            return;
        }

        System.out.println("Année : ");
        int year = sc.nextInt();
        sc.nextLine();

        System.out.println("Mois (1 pour janvier) : ");
        int month = sc.nextInt();
        sc.nextLine();

        InvoicingService invoicingService = InvoicingServiceFactory.createInvoicingService(client.getClientType(), consumptionService);
        try {
            BigDecimal amount = invoicingService.calculateInvoice(client, YearMonth.of(year, month));
            System.out.printf("La facture du client %s pour la période de %s-%s s'élève à : %s%n",
                    clientReference, year, month, amount.toString());
        } catch (ClientNotFoundException ignored) {
        } catch (ConsumptionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
