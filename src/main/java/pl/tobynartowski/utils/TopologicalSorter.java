package pl.tobynartowski.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class TopologicalSorter<T> {

    public List<T> sortData(final List<T> data, final Function<T, List<Class<? extends T>>> dependencyGetter) {
        if (data == null || data.isEmpty()) {
            return new LinkedList<>();
        }

        final Map<Boolean, List<T>> split = data.stream()
                .collect(Collectors.partitioningBy(entry -> dependencyGetter.apply(entry).isEmpty()));
        final List<T> sortedData = split.get(true);
        final List<T> unsortedData = split.get(false);

        while (!unsortedData.isEmpty()) {
            List<T> change = unsortedData.stream()
                    .filter(entry -> sortedData.containsAll(findForObjects(data, dependencyGetter.apply(entry))))
                    .collect(Collectors.toList());
            sortedData.addAll(change);
            unsortedData.removeAll(change);
        }

        return sortedData;
    }

    private List<T> findForObjects(final List<T> findIn, final List<Class<? extends T>> types) {
        return findIn.stream()
                .filter(query -> types.contains(query.getClass()))
                .collect(Collectors.toList());
    }
}