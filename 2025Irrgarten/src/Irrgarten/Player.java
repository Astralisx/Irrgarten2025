package Irrgarten;
import java.util.ArrayList;


public class Player extends LabyrinthCharacter{
    private static final int MAX_WEAPONS=2;
    private static final int MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HIT2LOSE=3;
    private char number;
    private int consecutiveHits=0;
    private static final int INVALID_POS = -1;

    private final ArrayList<Weapon> weaponArray = new ArrayList<>();
    private final ArrayList<Shield> shieldArray = new ArrayList<>();

    private WeaponCardDeck weaponCardDeck = new WeaponCardDeck();
   
    private ShieldCardDeck shieldCardDeck = new ShieldCardDeck();
    

    public Player(char number, float intelligence, float strength ){
        super("Player #"+number, intelligence, strength, INITIAL_HEALTH);
        this.number = number;
        this.consecutiveHits=0;

    }
    
    public Player(Player other){
        super(other);
        this.number=other.number;
        this.consecutiveHits=other.consecutiveHits;
        
        this.weaponCardDeck=other.weaponCardDeck;
        this.shieldCardDeck= other.shieldCardDeck;
    }
    
    public void resurrect(){
        this.resetHits();
        this.setHealth(INITIAL_HEALTH);
        this.shieldArray.clear();
        this.weaponArray.clear();
    }

    public char getNumber(){
        return this.number;
    }

    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);

        Directions toReturn;

        if ((size > 0) && !contained){ // El jugador se puede mover, pero no en la dirección deseada
            toReturn = validMoves.get(0);
        }
        else{
            toReturn = direction;
        }
        // Si size==0, se devuelve la dirección deseada y ya se comprobará en putPlayer2D que no se puede mover

        return toReturn;
    }
    @Override
    public float attack(){
        return this.getStrength() + sumWeapons();
    }
    @Override
    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }

    public void receiveReward(){
        int wReward =Dice.weaponsReward();
        int sReward = Dice.shieldReward();
        for (int i=0; i<wReward;i++){
            this.receiveWeapon(this.newWeapon());
        }
        for (int i=0; i<sReward;i++){
            this.receivedShield(this.newShield());
        }
        this.setHealth(this.getHealth()+Dice.healthReward());
    }

    @Override
    public String toString(){
        final String FORMAT = "%.6f";
        String toReturn= this.getName() +"[i:"+String.format(FORMAT, this.getIntelligence())+", s:"+String.format(FORMAT, this.getStrength());
        toReturn+=", h:"+String.format(FORMAT, this.getHealth())+", ch:"+this.consecutiveHits +", p:("+this.getRow() +", "+this.getCol() +")]";
        
        // Bucles para mostrar con un formato determinado el array de
        // armas y escudos del jugador
        String toWeapons="[";
        int tamWeapons=this.weaponArray.size();
        for(int i=0; i<tamWeapons-1; i++){
            toWeapons+=this.weaponArray.get(i).toString()+", ";
        }
        if (tamWeapons>0)
            toWeapons+=this.weaponArray.get(tamWeapons-1);
        toWeapons+="]";
        
        String toShields="[";
        int tamShields=this.shieldArray.size();
        for(int i=0; i<tamShields-1; i++){
            toShields+=this.shieldArray.get(i).toString()+", ";
        }
        if (tamShields>0)
            toShields+=this.shieldArray.get(tamShields-1);
        toShields+="]";
        
        // Definimos el formato final para el toString
        toReturn+="\nw:" + toWeapons+"\nsh:"+toShields;
        
        return toReturn;
    }

  




//--------------------------------------Privados

    private void receiveWeapon(Weapon w){
        for (int i=0; i<weaponArray.size(); i++){
            if (weaponArray.get(i).discard()){
                weaponArray.remove(i);
                i--;
            }
        }

        // Añadimos el escudo al jugador si cabe
        if (weaponArray.size() < MAX_WEAPONS)
            weaponArray.add(w);
    }

    private void receivedShield(Shield s){
        for (int i=0; i<shieldArray.size(); i++){
            if (shieldArray.get(i).discard()){
                shieldArray.remove(i);
                i--;
            }
        }

        // Añadimos el escudo al jugador si cabe
        if (shieldArray.size() < MAX_SHIELDS)
            shieldArray.add(s);

    }

    private Weapon newWeapon(){
       return this.weaponCardDeck.nextCard();
    }

    private Shield newShield(){
        return this.shieldCardDeck.nextCard();
    }

    protected float sumWeapons(){
        float sum = 0;
        for(Weapon weapon : weaponArray){
            sum += weapon.attack();
        }
        return sum;
    }

    protected float sumShields(){
        float sum=0;
        for(Shield shield : shieldArray){
            sum += shield.protect();
        }
        return sum;
    }
    
    protected float defensiveEnergy(){
        return this.getIntelligence() + sumShields();
    }

    private boolean manageHit(float receivedAttack){
        float defense = defensiveEnergy();
        boolean lose;
        if(defense < receivedAttack){
            gotWounded();
            incConsecutiveHits();
        }
        else{
            resetHits();
        }

        if((consecutiveHits == HIT2LOSE) || dead()){
            resetHits();
            lose=true;
        }
        else{
            lose =false;
        }
        return lose;
    }

    private void resetHits(){
        this.consecutiveHits=0;
    }

    private void incConsecutiveHits(){
        this.consecutiveHits ++;
    }

}
