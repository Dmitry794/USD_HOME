import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class HTMLParser implements Runnable {
    private double kurs;
    private double cash;
    private String[] listBanks;
    private JFrame window;
    private JLabel err;
    private JLabel labelKurs;
    private JLabel labelCash;
    private JList<String> list;
    public boolean dataReady;

    HTMLParser() {

        window = new JFrame("Список банков");
        window.setSize(300, 200);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.setLocation(350, 50);
        window.setResizable(false);
        window.setVisible(false);
        window.setLayout(null);
        window.getContentPane().setBackground(new Color(51, 176, 194));

    }

    void getData() {
        try {
            Document docs = Jsoup.connect("http://finance.tut.by/kurs/minsk/dollar/vse-banki/?sortBy=buy&sortDir=down").get();
            Elements el = docs.select("b[class]");
            listBanks = new String[el.size()];


            kurs = 10;
            double temp;
            String kurs_text = null;

            for (Element e : el) {
                temp = Double.valueOf(e.text());
                if (temp < kurs) {
                    kurs = temp;
                    kurs_text = e.text();
                }
            }

            cash = 1270 * 60.51 / kurs;

            Elements banks = docs.select("td");
            int id;
            int i = 0;
            for (Element bank : banks) {
                if (bank.text().equals(kurs_text)) {

                    id = banks.indexOf(bank) - 1;

                    String bank_text;
                    if (id > 0) {
                        bank_text = banks.get(id).text();
                        int end = bank_text.indexOf("Актуально") - 1;
                        if (end > 0) bank_text = bank_text.substring(0, end);
                        listBanks[i++] = bank_text;


                    } else bank_text = "Bank not Found!";

                }

            }
            dataReady = true;
        } catch (IOException e) {
            e.printStackTrace();
            dataReady = false;
        }


    }


    public void run() {
        while (true) {
            try {
                Thread.sleep(5 * 60 * 1000);
                getData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    String getShortMessage() {
        if (dataReady) return listBanks[0] + String.format("\n%1$.4f BYN \n$ %2$,.0f\n", kurs, cash);
        else return "Нет данных";
    }

    void showWindow() {

        if (dataReady) {
            if (err != null) window.remove(err);
            if (labelCash != null) window.remove(labelCash);
            if (labelKurs != null) window.remove(labelCash);
            if (list != null) window.remove(list);

            list = new JList<String>(listBanks);
            list.setBackground(new Color(255, 236, 184));
            window.add(list);

            list.setBounds(5, 5, 385, 18 * listBanks.length);
            window.setSize(400, list.getHeight() + 75);

            labelKurs = new JLabel(String.format("%1$.4f BYN", kurs));
            labelKurs.setOpaque(true);
            labelKurs.setBounds(95, list.getHeight() + 10, 100, 30);
            labelKurs.setHorizontalAlignment(JLabel.CENTER);
            labelKurs.setVerticalAlignment(JLabel.CENTER);
            labelKurs.setBackground(new Color(255, 236, 184));
            window.add(labelKurs);

            labelCash = new JLabel(String.format("$ %1$,.0f ", cash));
            labelCash.setOpaque(true);
            labelCash.setHorizontalAlignment(JLabel.CENTER);
            labelCash.setVerticalAlignment(JLabel.CENTER);
            labelCash.setBounds(200, list.getHeight() + 10, 100, 30);
            labelCash.setBackground(new Color(255, 236, 184));
            window.add(labelCash);
        } else {
            err = new JLabel("Нет данных");
            err.setOpaque(true);
            err.setBounds(window.getWidth() / 2 - 50, window.getHeight() / 2 - 30, 100, 30);
            err.setHorizontalAlignment(JLabel.CENTER);
            err.setVerticalAlignment(JLabel.CENTER);
            window.add(err);

        }
        window.setVisible(true);

    }

}
