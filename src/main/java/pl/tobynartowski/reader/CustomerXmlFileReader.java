package pl.tobynartowski.reader;

import pl.tobynartowski.model.Contact;
import pl.tobynartowski.model.Customer;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.UUID;

public class CustomerXmlFileReader implements FileReader<Customer> {

    private enum Element {
        PERSONS,
        PERSON,
        NAME,
        SURNAME,
        AGE,
        CITY,
        CONTACTS,
        EMAIL,
        PHONE,
        JABBER,
        UNKNOWN;

        public String getName() {
            return name().toLowerCase();
        }
    }

    private XMLEventReader reader;

    @Override
    public void openStream(String resourcePath) {
        try {
            reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(resourcePath));
        } catch (XMLStreamException | FileNotFoundException e) {
            System.err.println("Error while reading file: " + resourcePath);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeStream() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                System.err.println("Error while closing file");
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean hasNextElement() {
        try {
            while (reader.hasNext()) {
                if (reader.nextEvent().isStartElement()) {
                    return true;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer getNextElement() {
        if (reader == null || !reader.hasNext()) {
            System.err.println("File stream is closed or empty");
            throw new IllegalStateException();
        }

        final Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    final Element foundElement = Arrays.stream(Element.values())
                            .filter(e -> e.getName().equals(event.asStartElement().getName().getLocalPart()))
                            .findAny()
                            .orElse(Element.UNKNOWN);

                    final XMLEvent dataEvent = reader.nextEvent();
                    switch (foundElement) {
                        case PERSONS:
                        case PERSON:
                        case CITY:
                        case CONTACTS:
                            break;
                        case NAME:
                            customer.setName(dataEvent.asCharacters().getData());
                            break;
                        case SURNAME:
                            customer.setSurname(dataEvent.asCharacters().getData());
                            break;
                        case AGE:
                            customer.setAge(Integer.parseInt(dataEvent.asCharacters().getData()));
                            break;
                        case EMAIL:
                            customer.addContact(new Contact(UUID.randomUUID(), dataEvent.asCharacters().getData(), Contact.Type.EMAIL));
                            break;
                        case PHONE:
                            customer.addContact(new Contact(UUID.randomUUID(), dataEvent.asCharacters().getData(), Contact.Type.PHONE));
                            break;
                        case JABBER:
                            customer.addContact(new Contact(UUID.randomUUID(), dataEvent.asCharacters().getData(), Contact.Type.JABBER));
                            break;
                        case UNKNOWN:
                            if (customer.getContactList() != null) {
                                customer.addContact(new Contact(UUID.randomUUID(), dataEvent.asCharacters().getData(), Contact.Type.UNKNOWN));
                            }
                            break;
                    }
                } else if (event.isEndElement()
                        && event.asEndElement().getName().getLocalPart().equals(Element.PERSON.getName())) {
                    break;
                }
            }
        } catch (XMLStreamException e) {
            System.err.println("Error has occurred while reading a file");
            throw new RuntimeException(e);
        }

        return customer;
    }
}
