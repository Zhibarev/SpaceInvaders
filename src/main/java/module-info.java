module spaceinvaders.spaceinvader {
    requires javafx.controls;
    requires javafx.fxml;


    opens spaceinvaders to javafx.fxml;
    exports spaceinvaders;
    exports spaceinvaders.hud;
    opens spaceinvaders.hud to javafx.fxml;
    exports spaceinvaders.game;
    opens spaceinvaders.game to javafx.fxml;
    exports spaceinvaders.menu;
    opens spaceinvaders.menu to javafx.fxml;
    exports spaceinvaders.background;
    opens spaceinvaders.background to javafx.fxml;
}