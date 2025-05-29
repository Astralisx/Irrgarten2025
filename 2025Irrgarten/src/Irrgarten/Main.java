package Irrgarten;
import Irrgarten.Controller.*;
import Irrgarten.UI.*;

public class Main {
    public static void main(String[] args) {
        Game juego = new Game(2);
        GraphicUI ui = new GraphicUI();
        Controller controlador = new Controller(juego, ui);
        controlador.play();
    }
}
