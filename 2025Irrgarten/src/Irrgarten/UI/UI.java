/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Irrgarten.UI;
import Irrgarten.Directions;
import Irrgarten.GameState;
/**
 *
 * @author astralisx
 */
public interface UI {
    public Directions nextMove();
    public void showGame(GameState gameState);
}
