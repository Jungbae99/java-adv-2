package chatset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.SortedMap;

public class AvailableCharsetsMain {
    public static void main(String[] args) {
        // 이용 가능한 모든 Charset 자바 + OS
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String charsetName : charsets.keySet()) {
            System.out.println("charsetName = " + charsetName);
        }

        System.out.println("=====");
        // 문자로 조회(대소문자 구분X)
        Charset ms949 = Charset.forName("MS949");
        System.out.println("ms949 = " + ms949);

        Set<String> aliases = ms949.aliases();
        for (String alias : aliases) {
            System.out.println("alias = " + alias);
        }

        Charset charset = StandardCharsets.UTF_8;
        System.out.println("charset = " + charset);
        Set<String> aliases1 = charset.aliases();
        for (String utfalias : aliases1) {
            System.out.println("s = " + utfalias);
        }

        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("defaultCharset = " + defaultCharset);

    }

}
