package fr.ekwateur.model.client;

import java.util.Objects;

public abstract class Client {
    private String clientReference;
    private final ClientType clientType;

    public Client(String clientReference, ClientType clientType) {
        this.clientReference = clientReference;
        this.clientType = clientType;
    }

    protected Client(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientReference() {
        return clientReference;
    }
    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public ClientType getClientType() {
        return clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientReference, client.clientReference) && clientType == client.clientType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientReference, clientType);
    }
}
