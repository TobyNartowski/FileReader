package pl.tobynartowski.reader;

import org.junit.Test;
import pl.tobynartowski.model.Customer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTxtFileReaderTest {

    private final static String FILENAME = "input/dane-osoby.txt";

    @Test
    public void should_read_proper_customer_data() {
        // given
        final String file = getClass().getClassLoader().getResource(FILENAME).getFile();
        final FileReader<Customer> reader = new CustomerTxtFileReader(file);
        reader.openStream();

        // when
        final Customer customer = reader.getNextElement();

        // then
        assertNotNull(customer.getId());
        assertEquals("Jan", customer.getName());
        assertEquals("Kowalski", customer.getSurname());
        assertEquals(4, customer.getContactList().size());

        // cleanup
        reader.closeStream();
    }

    @Test
    public void should_read_all_data_from_file() {
        // given
        final String file = getClass().getClassLoader().getResource(FILENAME).getFile();
        final List<Customer> customerList = new ArrayList<>();
        final FileReader<Customer> reader = new CustomerTxtFileReader(file);
        reader.openStream();

        // when
        while (reader.hasNextElement()) {
            customerList.add(reader.getNextElement());
        }

        // then
        assertEquals(2, customerList.size());

        // cleanup
        reader.closeStream();
    }
}
