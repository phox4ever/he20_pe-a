package ch.hfict.blog.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Crypto {
    public static String hash(String string) {
        return Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
    }
}
