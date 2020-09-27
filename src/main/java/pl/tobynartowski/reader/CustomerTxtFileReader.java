package pl.tobynartowski.reader;

import pl.tobynartowski.model.Contact;
import pl.tobynartowski.model.Customer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class CustomerTxtFileReader implements FileReader<Customer> {

    private enum Element {
        NAME,
        SURNAME,
        AGE,
        CITY,
        CONTACT;

        public int getPosition() {
            return ordinal();
        }
    }

    private final static String numberRegex = "[0-9 ]+";
    private final static String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final static String jabberRegex = "^(?:([^@/<>'\\\"]+)@)?([^@/<>'\\\"]+)(?:/([^<>'\\\"]*))?$";

    private FileInputStream inputStream;
    private Scanner reader;
    private final String path;

    public CustomerTxtFileReader(final String path) {
        this.path = path;
    }

    @Override
    public void openStream() {
        try {
            inputStream = new FileInputStream(path);
            reader = new Scanner(inputStream, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            System.err.println("Error while reading file: " + path);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeStream() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                System.err.println("Error while closing file");
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean hasNextElement() {
        return reader.hasNextLine();
    }

    @Override
    public Customer getNextElement() {
        final String rawData = reader.nextLine();
        final Customer customer = new Customer();
        customer.setId(UUID.randomUUID());

        final String[] data = rawData.split(",");

        customer.setName(data[Element.NAME.getPosition()]);
        customer.setSurname(data[Element.SURNAME.getPosition()]);
        if (data[Element.AGE.getPosition()] != null && !data[Element.AGE.getPosition()].isBlank()) {
            customer.setAge(Integer.parseInt(data[Element.AGE.getPosition()]));
        }

        for (int i = Element.CONTACT.getPosition(); i < data.length; i++) {
            customer.addContact(new Contact(UUID.randomUUID(), customer.getId(), data[i], recognizeContactType(data[i])));
        }

        return customer;
    }

    private Contact.Type recognizeContactType(final String data) {
        if (Pattern.compile(numberRegex).matcher(data).matches()) {
            return Contact.Type.PHONE;
        } else if (Pattern.compile(emailRegex).matcher(data).matches()) {
            return Contact.Type.EMAIL;
        } else if (Pattern.compile(jabberRegex).matcher(data).matches()) {
            return Contact.Type.JABBER;
        } else {
            return Contact.Type.UNKNOWN;
        }
    }
}
