package Irrgarten;
import Irrgarten.Controller.*;
import Irrgarten.UI.*;

public class Juego {
    public static void main(String[] args) {
        Game juego = new Game(2);
        TextUI textUI = new TextUI();
        Controller controlador = new Controller(juego, textUI);
        controlador.play();
    }
}
