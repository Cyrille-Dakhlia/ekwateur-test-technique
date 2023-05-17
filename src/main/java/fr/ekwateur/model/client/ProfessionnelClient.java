package fr.ekwateur.model.client;

public class ProfessionnelClient extends Client {
    private String siretNumber;
    private String businessName;
    private long revenue;

    private ProfessionnelClient() {
        super(ClientType.PROFESSIONNEL);
    }

    public String getSiretNumber() {
        return siretNumber;
    }
    public void setSiretNumber(String siretNumber) {
        this.siretNumber = siretNumber;
    }

    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public long getRevenue() {
        return revenue;
    }
    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "ProfessionnelClient{" +
                "clientReference='" + super.getClientReference() + '\'' +
                ", clientType=" + super.getClientType() +
                ", siretNumber='" + siretNumber + '\'' +
                ", businessName='" + businessName + '\'' +
                ", revenue=" + revenue +
                '}';
    }

    public static class Builder {
        private final String clientReference;
        private String siretNumber;
        private String businessName;
        private long revenue;

        public Builder(String clientReference) {
            this.clientReference = clientReference;
        }

        public Builder withSiretNumber(String siretNumber) {
            this.siretNumber = siretNumber;
            return this;
        }

        public Builder withBusinessName(String businessName) {
            this.businessName = businessName;
            return this;
        }

        public Builder withRevenue(long revenue) {
            this.revenue = revenue;
            return this;
        }

        public ProfessionnelClient build() {
            ProfessionnelClient client = new ProfessionnelClient();
            client.setClientReference(this.clientReference);
            client.setSiretNumber(this.siretNumber);
            client.setBusinessName(this.businessName);
            client.setRevenue(this.revenue);
            return client;
        }
    }
}
