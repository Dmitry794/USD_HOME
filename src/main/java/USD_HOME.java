
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class USD_HOME {


    public static void main(String[] args) {
        // write your code here
        final TrayIcon trayIcon;
        final HTMLParser parser;



        if (SystemTray.isSupported()) {
            parser = new HTMLParser();
            parser.getData();

            Thread threadGetData = new Thread(parser);
            threadGetData.start();

            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            g.setColor(new Color(27, 174, 250));

            g.fillRect(0, 0, 12, 12);
            g.setColor(new Color(220, 250, 68));
            g.fillRect(4, 4, 12, 12);
            g.setColor(new Color(27, 174, 250));
            g.setFont(new Font("TimesRoman", Font.BOLD, 11));
            g.drawString("$", 8, 14);

            trayIcon = new TrayIcon(img, "Лучший курс USD");


            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    System.out.println("Exiting...");
                    System.exit(0);
                }
            };

            final ActionListener listBanksListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    parser.showWindow();
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Закрыть");
            defaultItem.addActionListener(exitListener);

            MenuItem listBanksItem = new MenuItem("Список банков");
            listBanksItem.addActionListener(listBanksListener);

            popup.add(listBanksItem);
            popup.add(defaultItem);

            trayIcon.setPopupMenu(popup);

            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    trayIcon.displayMessage("Лучший курс", parser.getShortMessage(), TrayIcon.MessageType.INFO);
                }
            };

            MouseListener mouseListener = new MouseListener() {

                public void mouseClicked(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse entered!");

                }

                public void mouseExited(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse exited!");
                }

                public void mousePressed(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse pressed!");
                }

                public void mouseReleased(MouseEvent e) {
//                    System.out.println("Tray Icon - Mouse released!");
                }
            };
//            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        } else {
            System.out.println("not supported");
            //  System Tray is not supported

        }

    }
}
