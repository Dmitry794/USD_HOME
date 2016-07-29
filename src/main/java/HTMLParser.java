import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Dmitry on 27.07.2016.
 */
public class HTMLParser implements Runnable {
    private Document docs;
    private Elements el;
    private Elements banks;
    private double kurs;
    private String kurs_text;
    private String bank_text;

    HTMLParser() {

    }

    void getData() {
        try {
            docs = Jsoup.connect("http://finance.tut.by/kurs/minsk/dollar/vse-banki/?sortBy=buy&sortDir=down").get();
            el = docs.select("b[class]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        kurs = 10;
        double temp;
        temp = 0;
        kurs_text = null;

        for (Element e : el) {
            temp = Double.valueOf(e.text());
            if (temp < kurs) {
                kurs = temp;
                kurs_text = e.text();
            }
        }
        //-----------------------------------------
        banks = docs.select("td");
        int id = 0;

        for (Element bank : banks) {
            try {
                if (bank.text().equals(kurs_text)) {

                    id = banks.indexOf(bank) - 1;
                    break;
                }
            } catch (NumberFormatException e) {
                //e.printStackTrace();
                // System.err.println(bank.text());
            }
        }

        if (id > 0) bank_text = banks.get(id).text();
        else bank_text = "Bank not Found!";
        //-----------------------------------------
    }


    String getTitle() {
        return docs.title();
    }

    double getKurs() {
        return kurs;
    }

    String getBank() {
        return bank_text;
    }

    public void run() {
        for (; ; ) {
            try {
                getData();
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
