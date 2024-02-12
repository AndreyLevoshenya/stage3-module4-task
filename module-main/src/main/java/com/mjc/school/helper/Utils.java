package com.mjc.school.helper;

import java.util.*;

import static com.mjc.school.helper.Constants.*;

public class Utils {
    public static Long getLongFromKeyboard(String idType) {
        Long id = null;
        boolean idIsValid = false;
        while (!idIsValid) {
            try {
                id = new Scanner(System.in).nextLong();
                idIsValid = true;
            } catch (InputMismatchException e) {
                if (idType.equals(NEWS_ID)) {
                    throw new RuntimeException(String.format(ID_SHOULD_BE_A_NUMBER, NEWS_ID));
                }
                if (idType.equals(AUTHOR_ID)) {
                    throw new RuntimeException(String.format(ID_SHOULD_BE_A_NUMBER, AUTHOR_ID));
                }
            }
        }
        return id;
    }

    public static List<Long> getTagIdsFromKeyboard() {
        List<Long> ids = new ArrayList<>();
        while (true) {
            System.out.println(ENTER_TAG_ID_OR_ZERO);
            Long id = getLongFromKeyboard(TAG_ID);
            if(id == 0) {
                break;
            }
            ids.add(id);
        }
        return ids;
    }
}
