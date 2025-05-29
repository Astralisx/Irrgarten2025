package Irrgarten.Controller;

import Irrgarten.Directions;
import Irrgarten.Game;
import Irrgarten.UI.*;


public class Controller {
    //Antes estaba TextUI en vez de la interfaz UI
    private Game game;
    private UI view;
    
    public Controller(Game game, UI view) {
        this.game = game;
        this.view = view;
    }
    
    public void play() {
        boolean endOfGame = false;
        while (!endOfGame) {
            view.showGame(game.getGameState()); 
            Directions direction = view.nextMove(); 
            endOfGame = game.nextStep(direction);
        }
      view.showGame(game.getGameState());        
    }
    
}