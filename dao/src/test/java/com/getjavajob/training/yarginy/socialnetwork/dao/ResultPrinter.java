package com.getjavajob.training.yarginy.socialnetwork.dao;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public class ResultPrinter {
    public static void printPassed(String className, String testName) {
        out.println(className + '.' + testName + "() passed" + lineSeparator());
    }
}
