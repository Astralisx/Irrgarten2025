/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;
import java.util.Random;
/**
 *
 * @author astralisx
 */
public class Dice {
    static private final int MAX_USES=5;
    static private final float MAX_INTELLIGENCE=10.0f;
    static private final float MAX_STRENGTH=10.0f;
    static private final float RESURRECT_PROB=0.3f;
    static private final int WEAPONS_REWARD = 2;
    static private final int SHIELD_REWARD = 3;
    static private final int HELATH_REWARD=5;
    static private final int MAX_ATTACK = 3;
    static private final int MAX_SHIELD = 2;
    static private final Random generator = new Random();
    
    public static int randomPos(int max){
        return generator.nextInt(0,max);
    }
    
    public static int whoStarts(int players){
        return generator.nextInt(0,players);
    }
    
    public static float randomIntelligence(){
        return generator.nextFloat() * MAX_INTELLIGENCE;
    }
    
    public static float randomStrength(){
        return generator.nextFloat() * MAX_STRENGTH;
    }
    
    public static boolean resurrectPlayer(){
       return generator.nextFloat() <= RESURRECT_PROB;
    }
    
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD+1);
    }

    public static int shieldReward(){
        return generator.nextInt(SHIELD_REWARD+1);
    }

    public static int healthReward(){
        return generator.nextInt(HELATH_REWARD)+1;
    }

    public static float weaponPower(){
        return generator.nextFloat()*MAX_ATTACK;
    }

    public static float shieldPower(){
        return generator.nextFloat()*MAX_SHIELD;
    }

    public static int usesLeft(){
        return generator.nextInt(MAX_USES+1);
    }

    public static float intensity(float competence){
        return generator.nextFloat()*competence;
    }

    public static boolean discardElement(int usesLeft){
        float prob = (float)usesLeft/MAX_USES;
        return generator.nextFloat() >= prob;
    }



}   
