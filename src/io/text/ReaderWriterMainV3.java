package io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static io.text.TextConst.FILE_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ReaderWriterMainV3 {
    public static void main(String[] args) throws IOException {

        String writeString = "abc";
        System.out.println("writeString = " + writeString);

        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        fw.write(writeString);
        fw.close();

        StringBuilder content = new StringBuilder();
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        int ch;

        while ((ch = fr.read()) != -1) {
            content.append((char) ch);
        }

        fr.close();

        String string = content.toString();
        System.out.println("stringBuilder = " + string);
    }
}
