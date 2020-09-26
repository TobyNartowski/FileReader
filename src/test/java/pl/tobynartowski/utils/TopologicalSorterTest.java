package pl.tobynartowski.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopologicalSorterTest {

    private interface TopologicalSorterTestData {

        List<Class<? extends TopologicalSorterTestData>> getDependencies();
    }

    @Test
    public void should_return_sorted_data() {
        // given
        final TopologicalSorter<TopologicalSorterTestData> sorter = new TopologicalSorter<>();
        final List<TopologicalSorterTestData> unsortedData = getTestData();
        Collections.shuffle(unsortedData);

        // when
        List<TopologicalSorterTestData> sortedData = sorter.sortData(unsortedData, TopologicalSorterTestData::getDependencies);

        // then
        assertEquals(unsortedData.size(), sortedData.size());
        assertTrue(checkIfSorted(sortedData));
    }

    @Test
    public void should_return_empty_queue_if_no_elements() {
        // given
        final TopologicalSorter<TopologicalSorterTestData> sorter = new TopologicalSorter<>();
        final List<TopologicalSorterTestData> emptyList = Collections.emptyList();

        // when
        List<TopologicalSorterTestData> sortedData = sorter.sortData(emptyList, TopologicalSorterTestData::getDependencies);

        // then
        assertTrue(sortedData.isEmpty());
    }

    private boolean checkIfSorted(List<TopologicalSorterTestData> data) {
        List<TopologicalSorterTestData> checked = new ArrayList<>();

        for (TopologicalSorterTestData entry : data) {
            if (!entry.getDependencies().isEmpty()
                    && checked.stream().anyMatch(c -> c.getDependencies().contains(entry.getClass()))) {
                return false;
            }

            checked.add(entry);
        }

        return true;
    }

    private List<TopologicalSorterTestData> getTestData() {
        final TopologicalSorterTestData first = Collections::emptyList;
        final TopologicalSorterTestData second = Collections::emptyList;
        final TopologicalSorterTestData third = Collections::emptyList;
        final TopologicalSorterTestData fourth = () -> Arrays.asList(first.getClass(), second.getClass());
        final TopologicalSorterTestData fifth = () -> Collections.singletonList(third.getClass());
        final TopologicalSorterTestData sixth = () -> Arrays.asList(fourth.getClass(), fifth.getClass());
        final TopologicalSorterTestData seventh = Collections::emptyList;

        return Arrays.asList(first, second, third, fourth, fifth, sixth, seventh);
    }
}
