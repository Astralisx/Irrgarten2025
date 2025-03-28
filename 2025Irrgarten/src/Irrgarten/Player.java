package Irrgarten;
import java.util.ArrayList;


public class Player {
    private static final int MAX_WEAPONS=2;
    private static final int MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HIT2LOSE=3;
    private String name;
    private char number;  
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits=0;

    private ArrayList<Weapon> weaponArray = new ArrayList<>();
    private ArrayList<Shield> shieldArray = new ArrayList<>();

    public Player(char number, float intelligence, float strength ){
        this.name = String.format("Player #%c",number);
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
    }
    
    public void resurrect(){
        this.consecutiveHits = 0;
        this.health = INITIAL_HEALTH;
        this.shieldArray.clear();
        this.weaponArray.clear();
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public char getNumber(){
        return this.number;
    }

    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean dead(){
        return health <= 0 ? true : false;
    }

    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        throw new UnsupportedOperationException();
    }
    public float attack(){
        return this.strength + sumWeapons();
    }
    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }

    public void receiveReward(){
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
        String cadena = String.format("P[name: %s, number: %c, i: %f, s: %f, h: %f, r: %d, c: %d, c_hits: %d]", this.name,this.number,this.intelligence,this.strength,this.health,this.row,this.col,this.consecutiveHits);
        cadena += "aW[";
        for (int i = 0; i < weaponArray.size(); i++){
            
            cadena += weaponArray.get(i).toString();
            if (i==weaponArray.size() - 1){
                cadena += "] ";
            }
        }
        cadena += "aS[";
        for (int i = 0; i < shieldArray.size(); i++){
            
            cadena += shieldArray.get(i).toString();
            if (i==shieldArray.size() - 1){
                cadena += "]";
            }
        }
         
        return cadena;
    }

  




//--------------------------------------Privados

    private void receiveWeapon(Weapon w){
        throw new UnsupportedOperationException();
    }

    private void receivedShield(Shield s){
        throw new UnsupportedOperationException();
    }

    private Weapon newWeapon(){
        Weapon weapon = new Weapon(Dice.weaponPower(), Dice.usesLeft());
        return weapon;
    }

    private Shield newShield(){
        Shield shield = new Shield(Dice.shieldPower(), Dice.usesLeft())
    }

    private float sumWeapons(){
        float sum = 0;
        for(Weapon weapon : weaponArray){
            sum += weapon.attack();
        }
        return sum;
    }

    private float sumShields(){
        float sum=0;
        for(Shield shield : shieldArray){
            sum += shield.protect();
        }
        return sum;
    }
    
    private float defensiveEnergy(){
        return this.intelligence + sumShields();
    }

    private boolean manageHit(float receivedAttack){
        throw new UnsupportedOperationException();
    }

    private void resetHits(){
        this.consecutiveHits=0;
    }

    private void gotWounded(){
        this.health--;
    }

    private void incConsecutiveHits(){
        this.consecutiveHits ++;
    }

}
