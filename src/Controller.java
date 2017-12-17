import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML TextField uid;

    public void goMain() throws Exception {
        String name = uid.getText();

        Stage primaryStage = (Stage)uid.getScene().getWindow(); // https://blog.naver.com/qkrghdud0/220708425556
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.name = name;

        primaryStage.setTitle("Word Chain ID: " + name); // http://city7310.blog.me/220872445792
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }
}
