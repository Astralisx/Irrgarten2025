/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;

/**
 *
 * @author astralisx
 */
public class CombatElement {
    private float effect;
    private int uses;
    
    public CombatElement(float effect, int uses){
        this.effect = effect;
        this.uses = uses;
    }
    
    protected float produceEffect(){
        if (uses>0){
            uses--;
            return effect;
        }
        return 0;
    }
    
    public boolean discard(){
        return Dice.discardElement(this.uses);
    }
    
    @Override
    public String toString(){
        return String.format("[%f,%d]",this.effect,this.uses);
    }
    
}
