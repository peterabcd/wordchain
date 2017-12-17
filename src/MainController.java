import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML TextArea txtbox;
    @FXML TextField sendbox;
    @FXML Button sender;
    String name; String ip = "234.5.6.7"; int port = 8901;
    MulticastSocket ms; InetAddress ia;

    class Task implements Runnable{
        @Override
        public void run() {
            try {
                ms = new MulticastSocket(port); ia = InetAddress.getByName(ip);
                ms.joinGroup(ia);
                while (true) {
                    byte msg[] = new byte[512];
                    String message;
                    char last;
                    DatagramPacket dp = new DatagramPacket(msg, msg.length);
                    ms.receive(dp);

                    message = txtbox.getText() + "\n" + new String(dp.getData()).trim();
                    txtbox.setText(message);
                    last = message.charAt(message.length()-1);
                    sendbox.clear();
                    sendbox.setText("" + last);
                }
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // http://rain483.blog.me/220609097248
        Runnable task = new Task();
        Thread t = new Thread(task);
        t.start();
        sender.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try{
                    DatagramPacket dp = new DatagramPacket((name + ": " + sendbox.getText()).getBytes(),(name + ": " + sendbox.getText()).getBytes().length,ia,port);
                    ms.send(dp);
                } catch (Exception e){e.printStackTrace();}
            }
        });
    }
}
// http://ddoriya.tistory.com/entry/JAVA-Multicast-Server-Client-Socket