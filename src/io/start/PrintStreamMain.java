package io.start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PrintStreamMain {
    public static void main(String[] args) throws IOException {

        PrintStream printStream = System.out;
        byte[] bytes = "Hello\n".getBytes(UTF_8);
        printStream.write(bytes);

        printStream.println("print!");
    }
}
