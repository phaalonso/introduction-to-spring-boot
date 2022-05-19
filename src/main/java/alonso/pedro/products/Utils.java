package alonso.pedro.products;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
