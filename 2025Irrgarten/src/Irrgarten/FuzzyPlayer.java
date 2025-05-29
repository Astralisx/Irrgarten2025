/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;
import java.util.ArrayList;

/**
 *
 * @author astralisx
 */
public class FuzzyPlayer extends Player{
    public FuzzyPlayer (Player other){
        super(other);
    }
    
    @Override
    public Directions move(Directions direction,ArrayList<Directions> validMoves){
        Directions preference = super.move(direction, validMoves);
        return Dice.nextStep(preference, validMoves, this.getIntelligence());
    }
    
    @Override
    public float attack(){
        return Dice.intensity(this.getStrength())+this.sumWeapons();
    }
    
    @Override
    protected float defensiveEnergy(){
        return (Dice.intensity(this.getIntelligence())+this.sumShields());
    }
    
    @Override
    public String toString(){
        String toReturn="(Fuzzy) ";
        toReturn+=super.toString();
        
        return toReturn;
    }
}
