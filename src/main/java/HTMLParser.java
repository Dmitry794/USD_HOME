import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Dmitry on 27.07.2016.
 */
public class HTMLParser {
    Document docs;
    Elements el;
    Elements banks;
    double kurs;

    HTMLParser() {

    }

    String getTitle() {
        return docs.title();
    }

    double getKurs() {
        try {
            docs = Jsoup.connect("http://finance.tut.by/kurs/minsk/dollar/vse-banki/?sortBy=buy&sortDir=down").get();
//            el = docs.getElementsByTag("<td>");
            el = docs.select("b[class]");

        } catch (IOException e) {
            e.printStackTrace();
        }

        kurs = 10;
        double temp = 0;

        for (Element e : el) {
            temp = Double.valueOf(e.text());
            if (temp < kurs) kurs = temp;

        }
        return kurs;
    }

    String getBank() {

       // try {
      //      docs = Jsoup.connect("http://finance.tut.by/kurs/minsk/dollar/vse-banki/?sortBy=buy&sortDir=down").get();

            banks = docs.select("td");
            int id = 0;
//            System.out.println(banks.size());

            for (Element bank : banks) {
                //System.out.println(bank.text());

                try {
                    if (Double.valueOf(bank.text()) == getKurs()) {

                        id = banks.indexOf(bank);
                        break;
                    }
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    // System.err.println(bank.text());
                }
            }

            if (id>0) return banks.get(id-1).text();
            else return "Bank not Found!";

       // } catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
}
