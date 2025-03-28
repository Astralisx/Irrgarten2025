/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;


/**
 *
 * @author astralisx
 */
public class Monster {
    private static final int INITIAL_HEALTH=5;
    private String name;
    private float strength;
    private float health;
    private int row;
    private int col;

    public Monster(String name, float strength){
        this.name = name;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
    }

    public boolean dead(){
        return health <= 0 ? true : false;
    }

    public float attack(){
        return Dice.intensity(this.strength);
    }

    @Override
    public String toString(){
        return String.format("M[n: %s,s: %f,h: %f ,r: %d,c: %d]", this.name, this.strength, this.health, this.row, this.col);
    }

    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public boolean defend(float receivedAttack){
        throw new UnsupportedOperationException();
    }

//-------Private--------

    private void gotWounded() {
        this.health--;
    }
}
