package pl.tobynartowski.model;

import java.util.UUID;

public class Contact {

    public enum Type {

        UNKNOWN, EMAIL, PHONE, JABBER
    }

    private UUID id;
    private UUID customerId;
    private String contact;
    private Type type;

    public Contact(UUID id, UUID customerId, String contact, Type type) {
        this.id = id;
        this.customerId = customerId;
        this.contact = contact;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
