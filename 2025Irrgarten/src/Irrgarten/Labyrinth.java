package Irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    private static final char BLOCK_CHAR='X';
    private static final char EMPTY_CHAR='-';
    private static final char MONSTER_CHAR='M';
    private static final char COMBAT_CHAR='C';
    private static final char EXIT_CHAR='E';
    private static final int ROW=0;
    private static final int COL=1;
    private static final int POS_IMPOSIBLE=-1;
    private final int nRows;
    private final int nCols;
    private final int exitRow;
    private final int exitCol;

    private final Player[][] players;
    private final Monster[][] monsters;
    private final char[][] labyrinth;


    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;

        players = new Player[nRows][nCols];
        monsters = new Monster[nRows][nCols];
        labyrinth = new char[nRows][nCols];

        for (int i = 0; i < nRows; i++){
            for (int j=0; j < nCols; j++){
                this.labyrinth[i][j] = EMPTY_CHAR;
                this.monsters[i][j] = null;
                this.players[i][j] = null;
            }
        }

        labyrinth[exitRow][exitCol] = EXIT_CHAR;

    }

    public void spreadPLayers(ArrayList<Player> players){
        for (Player player : players) {
            int[] pos = randomEmptyPos();
            putPlayer2D(POS_IMPOSIBLE, POS_IMPOSIBLE,pos[ROW], pos[COL], player);
        }
    }

    public boolean haveAWinner(){
        return (players[this.exitRow][this.exitCol] != null);
    }

    @Override
    public String toString(){
        String cadena="";
        for (int i=0; i < nRows; i++){
            for (int j=0; j < nCols; j++){
                cadena += this.labyrinth[i][j] + "\t ";
            }
            cadena += "\n\n";
        }

        return cadena;
    }
    
    public void convertToFuzzy(FuzzyPlayer other){
        int row=other.getRow();
        int col=other.getCol();
        if(this.players[row][col].getNumber() == other.getNumber())
            this.players[row][col]=other;
    }
    public void addMonster(int row, int col, Monster monster){
        if (posOK(row, col) && (emptyPos(row, col))){
            monster.setPos(row,col);
            this.monsters[row][col]=monster;
            this.labyrinth[row][col]=MONSTER_CHAR;
        }
    }

    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
        return monster;
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        int incRow=0, incCol=0; // Incremento de la fila y columna
        if (orientation == Orientation.VERTICAL){
            incRow++;
        }
        else{
            incCol++;
        }

        int row=startRow;
        int col=startCol;

        while (posOK(row, col) && emptyPos(row, col) && length>0){
            this.labyrinth[row][col]=BLOCK_CHAR;
            row+=incRow;
            col+=incCol;
            length--;
        }
    }
    
    public ArrayList<Directions> validMoves(Integer row,Integer col){
        ArrayList<Directions>output = new ArrayList<>();

        if (this.canStepOn(row +1, col)){
            output.add(Directions.DOWN);
        }
        if (this.canStepOn(row-1,col)) {
            output.add(Directions.UP);
        }
        if (this.canStepOn(row,col+1)) {
            output.add(Directions.RIGHT);
        }
        if (this.canStepOn(row,col-1)) {
            output.add(Directions.LEFT);
        }
        return output;
    }
// ------------------------------ Private -----------------------

    private boolean posOK(int row, int col){
        return (0 <= row && row < nRows && 0 <= col && col < nCols);
    }

    private boolean emptyPos(int row, int col){
        return (this.labyrinth[row][col] == EMPTY_CHAR);
    }

    private boolean monsterPos(int row, int col){
        return (this.labyrinth[row][col]==MONSTER_CHAR);

    }

    private boolean exitPos(int row, int col){
        return (this.labyrinth[row][col]==EXIT_CHAR);
    }   

    private boolean combatPos(int row, int col){
        return (this.labyrinth[row][col]==COMBAT_CHAR);

    }

    private boolean canStepOn(int row, int col){
        boolean comprobacion=this.posOK(row, col);
        comprobacion = comprobacion && (this.monsterPos(row, col) || this.exitPos(row, col) ||
                this.emptyPos(row, col));

        return comprobacion;
    }

    private void updateOldPos(int row, int col){
        if (posOK(row, col)){
            if (this.combatPos(row, col)){
                this.labyrinth[row][col] = MONSTER_CHAR;
            }
            else{
                this.labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction){
        int [] newPos = new int[2];
        if (direction == Directions.UP){
            newPos[0]=row-1;
            newPos[1]=col;
        }
        if (direction == Directions.DOWN){
            newPos[0]=row+1;
            newPos[1]=col;
        }
        if (direction == Directions.LEFT){
            newPos[0]=row;
            newPos[1]= col-1;
        }
        if (direction == Directions.RIGHT){
            newPos[0]=row;
            newPos[1]= col+1;
        }

        return newPos;
    
    }

    private int[] randomEmptyPos(){
        int[] pos = new int[2];
        do{
        pos[0]=Dice.randomPos(nRows);
        pos[1]=Dice.randomPos(nCols);
        }while(!this.emptyPos(pos[0], pos[1]));

        return pos;

    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        if (canStepOn(row, col)){
            if(posOK(oldRow, oldCol)){
                Player p = players[oldRow][oldCol];
                if (p == player){
                    updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            boolean monsterPos = monsterPos(row, col);
            if (monsterPos){
                labyrinth[row][col] = COMBAT_CHAR;
                output = monsters[row][col];
    
            }
            else{
                char number = player.getNumber();
                labyrinth[row][col]=number;
            }

            players[row][col] = player;
            player.setPos(row, col);
        }
        return output;
    }
    

}
