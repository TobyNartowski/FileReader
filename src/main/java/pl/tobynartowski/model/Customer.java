package pl.tobynartowski.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String surname;
    private Integer age;
    private List<Contact> contactList;

    public Customer() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void addContact(Contact contact) {
        if (contactList == null) {
            contactList = new ArrayList<>();
        }

        contactList.add(contact);
    }
}
