package ToolUtils.Spider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class BasicWebUtils {
    public static Document connUrl(String url) {
        Connection conn = Jsoup.connect(url);
        conn.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:54.0) Gecko/20100101 Firefox/54.0");
        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document == null) {
            return null;
        }
        return document;
    }
}
