package com.qhrtech.devquiz.digitsquaresum;

import com.qhrtech.devquiz.QuizMainHelper;
import java.io.OutputStreamWriter;

/**
 * Main executable.  Just execute the main class and it will report its result.
 *
 * DO NOT CHANGE ANYTHING IN THIS FILE.
 *
 * @author QHR-RF
 */
public class QuizMain {

    public static void main(String[] args) throws Exception {
        String[] methodNames = QuizMainHelper.getMethodNamesFromArgs(args);
        QuizMainHelper.execute(MySubmission.class,
                methodNames,
                new DigitSquareSumAnswer(),
                new OutputStreamWriter(System.out));
    }
}
