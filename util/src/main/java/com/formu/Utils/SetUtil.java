package com.formu.Utils;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by weiqiang
 */
public class SetUtil {

    public static String upGood(String str, int id) {
        Set<String> set = new HashSet<String>(Arrays.asList(str.split("-")));
        int n1 = set.size();

        set.add(String.valueOf(id));
        StringBuilder good = new StringBuilder("");
        for (String s : set) {
            good.append(s).append("-");
        }
        good.deleteCharAt(good.length() - 1);
        return String.valueOf(good);
    }
}
