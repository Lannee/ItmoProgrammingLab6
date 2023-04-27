package module.utils;

/**
 * Util class for creating objects instances by their classes
 */
public class ObjectUtils {

    public static <T extends Comparable<T>> int saveCompare(T o1, T o2) {
        if(o1 == null & o2 == null)
            return 0;
        else if(o1 == null & o2 != null)
            return -1;
        else if(o1 != null & o2 == null)
            return 1;
        else
            return o1.compareTo(o2);
    }
}
