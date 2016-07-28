
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
            // parser.getBank();

            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            g.setColor(new Color(27, 174, 250));
            //  g.drawRect(0,0,16,16);
            //
            g.fillRect(0, 0, 12, 12);
            g.setColor(new Color(100, 228, 77));
            g.fillRect(4, 4, 12, 12);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD, 11));
            g.drawString("$", 8, 14);


            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Закрыть");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(img, "Лучший курс USD", popup);
/*
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("АБСОЛЮТБАНК",
                            "1,99 -> $38 616.9",
                            TrayIcon.MessageType.NONE);
                }
            };
*/
            MouseListener mouseListener = new MouseListener() {

                public void mouseClicked(MouseEvent e) {

                    String message;
                    double k = 1270 * 60.51 / parser.getKurs();

                    message = String.format("\n%1$.4f BYN \n$ %2$.0f1", parser.getKurs(), k);

                    String bank_str = parser.getBank();
                    int end = bank_str.indexOf("Актуально") - 1;
                    String bank_mes = null;

                    if (end > 0) bank_mes = new String(bank_str.substring(0, end));
//                        System.out.println(bank_str.substring(0,end));

                    trayIcon.displayMessage("Лучший курс", bank_mes + message, TrayIcon.MessageType.INFO);

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
//            trayIcon.addActionListener(actionListener);
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
