package fr.ekwateur.model.client;

public class ParticulierClient extends Client {
    private Civilite civilite;
    private String prenom;
    private String nom;

    private ParticulierClient() {
        super(ClientType.PARTICULIER);
    }

    public Civilite getCivilite() {
        return civilite;
    }
    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public String toString() {
        return "ParticulierClient{" +
                "clientReference='" + super.getClientReference() + '\'' +
                ", clientType=" + super.getClientType() +
                ", civilite=" + civilite +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }

    public static class Builder {
        private final String clientReference;
        private Civilite civilite;
        private String prenom;
        private String nom;

        public Builder(String clientReference) {
            this.clientReference = clientReference;
        }

        public Builder withCivilite(Civilite civilite) {
            this.civilite = civilite;
            return this;
        }

        public Builder withPrenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public Builder withNom(String nom) {
            this.nom = nom;
            return this;
        }

        public ParticulierClient build() {
            ParticulierClient client = new ParticulierClient();
            client.setClientReference(this.clientReference);
            client.setCivilite(this.civilite);
            client.setPrenom(this.prenom);
            client.setNom(this.nom);
            return client;
        }
    }
}
