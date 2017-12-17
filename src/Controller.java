import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML TextField uid;
    // start.fxml의 닉네임 입력 박스

    public void goMain() throws Exception {
        String name = uid.getText();
        // 닉네임 박스에 적은 문자열 받아오기

        Stage primaryStage = (Stage)uid.getScene().getWindow();
        // 닉네임 입력 박스로부터 Stage를 얻어냄
        // https://blog.naver.com/qkrghdud0/220708425556
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        // 끝말잇기 메인 화면을 담은 main.fxml파일 불러오기
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.name = name;
        // 닉네임을 다음 Scene으로 보내기

        primaryStage.setTitle("Word Chain ID: " + name);
        // Title 이름 바꾸기
        // http://city7310.blog.me/220872445792
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }
}
