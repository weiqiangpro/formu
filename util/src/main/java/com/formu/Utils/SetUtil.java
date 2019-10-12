package com.formu.Utils;


import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by weiqiang
 */
public class SetUtil {

    public static String upGood(String str, int id) {
        if (str == null) {
            str = "";
        }
        Set<String> set = new HashSet<String>(Arrays.asList(str.split("-")));
        int n1 = set.size();
        if (set.contains(String.valueOf(id))){
            set.remove(String.valueOf(id));
        }
        else {
            set.add(String.valueOf(id));
        }
        StringBuilder good = new StringBuilder("");
        for (String s : set) {
            if (StringUtils.isNotBlank(s))
                good.append(s).append("-");
        }
        if (good.length()>1)
        good.deleteCharAt(good.length() - 1);
        return String.valueOf(good);
    }
}
