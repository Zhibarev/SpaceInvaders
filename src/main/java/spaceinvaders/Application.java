package spaceinvaders;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        GameViewManager gameViewManager = new GameViewManager();
        gameViewManager.getStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}