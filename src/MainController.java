import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.*;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    // public void initialize()를 실행할 수 있게 함
    @FXML TextArea txtbox;
    // 채팅 내용을 담는 상자
    @FXML TextField sendbox;
    // 보낼 내용을 담는 상자
    @FXML TextField lastbox;
    // 끝말을 출력하는 상자
    @FXML Button sender;
    // 보내는 버튼
    String name; String ip = "234.5.6.7"; int port = 8901;
    // 노정훈의 멀티캐스트에 대한 언급으로 힌트를 얻음
    MulticastSocket ms; InetAddress ia;

    class Task implements Runnable{
        // Packet 받아오기 전용 class
        @Override
        public void run() {
            try {
                ms = new MulticastSocket(port); ia = InetAddress.getByName(ip);
                // Socket의 포트와 사용할 IP를 설정함
                ms.joinGroup(ia);
                // 설정대로 접속
                while (true) {
                    byte msg[] = new byte[512];
                    // 받아온 Packet의 정보를 저장할 byte 공간 선언
                    String message;
                    // 기존의 텍스트와 Packet의 내용을 같이 저장하는 공간 선언
                    char last;
                    // 받아온 메시지의 마지막 글자 저장하는 공간 선언
                    DatagramPacket dp = new DatagramPacket(msg, msg.length);
                    ms.receive(dp);
                    // Packet 받기

                    message = txtbox.getText() + "\n" + new String(dp.getData()).trim();
                    // 기존의 텍스트와 Packet의 내용을 같이 저장
                    txtbox.setText(message + "\n"); txtbox.appendText("");
                    // 채팅 내용을 업데이트
                    // 한 칸이 스크롤이 안되는 것을 방지하기 위에 appendText
                    // https://stackoverflow.com/questions/17799160/javafx-textarea-and-autoscroll
                    last = message.charAt(message.length()-1);
                    // 메시지의 마지막 글자 얻어내기
                    sendbox.clear();
                    // 보내는 공간 초기화
                    lastbox.setText("" + last);
                    // TextField에 마지막 글자 삽입
                    txtbox.setScrollTop(Double.MAX_VALUE);
                    // https://stackoverflow.com/questions/17799160/javafx-textarea-and-autoscroll
                    Toolkit.getDefaultToolkit().beep();
                    // 메시지 알림음
                    // https://docs.oracle.com/javase/7/docs/api/java/awt/Toolkit.html
                }
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //MainController.java가 실행되었을 때 자동으로 호출됨
        // http://rain483.blog.me/220609097248
        Runnable task = new Task();
        Thread t = new Thread(task);
        t.start();
        // Packet을 수신하는 Thread

        sender.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // 마우스로 버튼을 클릭했을때 실행
            @Override
            public void handle(MouseEvent event) {
                try{
                    DatagramPacket dp = new DatagramPacket((name + ": " + lastbox.getText() + sendbox.getText()).getBytes(),(name + ": " + lastbox.getText() + sendbox.getText()).getBytes().length,ia,port);
                    ms.send(dp);
                    // Packet에 닉네임과 채팅 내용을 담아 전송
                } catch (Exception e){e.printStackTrace();}
            }
        });
    }
}
// http://ddoriya.tistory.com/entry/JAVA-Multicast-Server-Client-Socket