/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;

/**
 *
 * @author astralisx
 */
public class Weapon {
    private float power;
    private int uses;
    
    public Weapon(float power,int uses){
        this.power=power;
        this.uses=uses;
    }
    
    public float attack(){
        if (this.uses > 0){
            uses--;
            return this.power;
        }
        return 0;
    }

    public boolean discard(){
        return Dice.discardElement(this.uses);
    }

    @Override
    public String toString(){
        return String.format("W[%f,%d]",power,uses);
    }
}
