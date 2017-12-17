import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        // 닉네임을 입력하는 fxml 파일 불러오기
        primaryStage.setTitle("Word Chain");
        // 끝말잇기라는 뜻
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);}
}
