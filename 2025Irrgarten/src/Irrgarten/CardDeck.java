/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;
import java.util.Collections;
import java.util.ArrayList;
/**
 *
 * @author astralisx
 */
abstract class CardDeck < T extends CombatElement>{
    private ArrayList<T> cardDeck;
    // Tamaño máximo de la baraja de cartas
    protected static final int TAM_MAX = 50;
    
    public CardDeck(){
        this.cardDeck = new ArrayList<>();
    }
    
    protected abstract void addCards();
    
    protected void addCard(T card){
        this.cardDeck.add(card);
    }
    
    public T nextCard(){
        if(this.cardDeck.size()<=0) {
            this.addCards();
            Collections.shuffle(this.cardDeck); // Baraja el array pasado
        }
        T seleccionada=this.cardDeck.get(0);
        this.cardDeck.remove(0);
        
        return seleccionada;
        
    }
}
