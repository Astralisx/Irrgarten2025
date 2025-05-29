/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;


/**
 *
 * @author astralisx
 */
public class Monster extends LabyrinthCharacter{
    private static final int INITIAL_HEALTH=5;

    public Monster(String name, float strength, float intelligence){
        super(name, intelligence, strength, INITIAL_HEALTH);
    }

    @Override
    public float attack(){
        return Dice.intensity(this.getStrength());
    }
    
    @Override
    public boolean defend(float receivedAttack){
        boolean isDead = dead();
        if(!isDead){
            float defensiveEnergy = Dice.intensity(this.getIntelligence());
            if (defensiveEnergy < receivedAttack){
                gotWounded();
                isDead = dead();
            }
        }
        return isDead;
    }
}
