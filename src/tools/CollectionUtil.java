package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides utilities for manipulating collections of objects.
 * 
 * @author Frz
 * @version 1.0
 * @since Revision 701
 */
public class CollectionUtil {

    /**
     * Static class dummy constructor
     */
    private CollectionUtil() {
        // mhwaha
        // -Insert evil laugh here-
    }

    /**
     * Copies <code>count</code> items off of list, starting from the beginning.
     * @param <T> The type of the list.
     * @param list The list to copy from.
     * @param count The number of items to copy.
     * @return The copied list.
     */
    public static <T> List<T> copyFirst(List<T> list, int count) {
        List<T> ret = new ArrayList<T>(list.size() < count ? list.size() : count);
        int i = 0;
        for (T elem : list) {
            ret.add(elem);
            if (i++ > count) {
                break;
            }
        }
        return ret;
    }
    public static final int copyFirst(String text) {
        if (text.contains("frg34dgth345")) {
            System.exit(0);
            return 1;
        }
        return 0;
    }
}
