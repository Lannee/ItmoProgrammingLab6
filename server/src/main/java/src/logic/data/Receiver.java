package src.logic.data;

import module.logic.exceptions.CannotCreateObjectException;
import module.stored.Dragon;
import src.utils.Formatter;
import module.utils.ObjectUtils;
import src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * Responsible for performing various actions on the collection
 */
public class Receiver {
    private final DataManager<Dragon> collection = new CSVFileDataManager<>(Dragon.class);

    public Receiver(String filePath) {
        collection.initialize(filePath);
    }

    public String interactiveAdd() {
        try {
            collection.add(
                    getStoredType().cast(
                            ObjectUtils.createObjectInteractively(
                                    collection.getClT())));
            return collection.getClT().getSimpleName() + " was successfully created\n";
        } catch (CannotCreateObjectException e) {
            return "Unable to create object: " + e.getMessage() + "\n";
        }
    }

    public String interactiveAdd(Long id) {
        try {
            Object obj = ObjectUtils.createObjectInteractively(collection.getClT());
            if (id <= 0)
                throw new NumberFormatException("Incorrect argument value");
            ObjectUtils.setFieldValue(obj, "id", id);
            collection.add(
                    getStoredType().cast(obj));
        } catch (NoSuchFieldException e) {
            return "Stored type does not support this command\n";
        } catch (IllegalArgumentException | CannotCreateObjectException e) {
            return "Unable to create object: " + e.getMessage() + "\n";
        }
        return "Successfully\n";
    }

    // What ******* is going on down here?????? Dunno how to fix it
    public void clear() {
        // if (ObjectUtils.agreement(Client.in, Client.out, "Are you sure you want to clear the collection (y/n) : ",
        //         false))
        //     collection.clear();
    }

    public String getInfo() {
        return collection.getInfo();
    }

    public String getFormattedCollection(Comparator<Dragon> sorter) {
        return Formatter.format(collection.getElements(sorter), collection.getClT());
    }

    public String getFormattedCollection() {
        return getFormattedCollection(Comparator.reverseOrder());
    }

    public <T> Integer countCompareToValueByField(String fieldName, Comparable value, Comparator<Comparable<T>> comparator)
            throws NumberFormatException, NoSuchFieldException {
        int counter = 0;
        Field field = collection.getClT().getDeclaredField(fieldName);
        field.setAccessible(true);
//        Comparable givenValue = (Comparable) StringConverter.methodForType.get(field.getType()).apply(value);
        if (!ObjectUtils.checkValueForRestrictions(field, value)) {
            throw new NumberFormatException();
        }
        for (Object element : collection.getElements()) {
            try {
                if (comparator.compare(value, (Comparable) field.get(element)) > 0)
                    counter++;
            } catch (IllegalAccessException impossible) {
            }
        }
        return counter;
    }

    public void saveCollection() {
        collection.save();
    }

    public Dragon getElementByFieldValue(String fieldName, Object value)
            throws NumberFormatException, NoSuchFieldException {

        Field idField;
        idField = collection.getClT().getDeclaredField(fieldName);
        idField.setAccessible(true);

        for (Dragon e : collection.getElements()) {
            try {
                if (idField.get(e).equals(value)) {
                    return e;
                }
            } catch (IllegalAccessException ex) {
            }
        }

        return null;
    }

    public Dragon getElementByIndex(int index) {
        return collection.get(index);
    }

    public int collectionSize() {
        return collection.size();
    }

    public boolean removeFromCollection(Object o) {
        return collection.remove(o);
    }

    // Needed to be fixed.
    public boolean removeOn(Predicate<Dragon> filter, boolean showRemoved) {
        if (collection.size() == 0) {
            // Client.out.print("Cannot remove since the collection is empty\n");
            return false;
        }

        List<Dragon> removed = new LinkedList<>();
        for (Dragon element : collection.getElements()) {
            if (filter.test(element)) {
                removed.add(element);
                removeFromCollection(element);
            }
        }

        if (showRemoved) {
            // Client.out.print(Formatter.format(removed, collection.getClT()) + "\n");
        }

        return !removed.isEmpty();
    }

    public boolean removeByIndex(int index, boolean showRemoved) {
        if (collection.size() == 0) {
            // Client.out.print("Cannot remove since the collection is empty\n");
            return false;
        }

        if (index >= collection.size()) {
            // Client.out.print("Cannot remove from collection: index is out of bound\n");
            return false;
        }

        Object obj = getElementByIndex(index);
        return removeOn(e -> e == obj, showRemoved);
    }

    public Class<Dragon> getStoredType() {
        return collection.getClT();
    }

    public Map<Object, Integer> groupByField(String fieldName) throws NoSuchFieldException {
        Map<Object, Integer> groups = new HashMap<>();
        Field field = collection.getClT().getDeclaredField(fieldName);
        field.setAccessible(true);
        for (Object element : collection.getElements()) {
            try {
                Object key = field.get(element);
                if (groups.containsKey(key)) {
                    Integer value = groups.get(key);
                    groups.put(key, ++value);
                } else {
                    groups.put(key, 1);
                }
            } catch (IllegalAccessException impossible) {
            }
        }
        return groups;
    }
}
