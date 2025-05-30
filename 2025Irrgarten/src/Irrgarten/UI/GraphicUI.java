/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Irrgarten.UI;
import Irrgarten.GameState;
import Irrgarten.Directions;

/**
 *
 * @author astralisx
 */
public class GraphicUI extends javax.swing.JFrame implements UI {
    private Cursors cursor;
    /**
     * Creates new form GraphicUI
     */
    public GraphicUI() {
        initComponents();
        this.cursor = new Cursors(this,true);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Labyrinth = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        Players = new javax.swing.JTextArea();
        winner = new javax.swing.JTextField();
        CurrentPlayer = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        Monsters = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Labyrinth.setColumns(20);
        Labyrinth.setRows(5);
        jScrollPane1.setViewportView(Labyrinth);

        Players.setColumns(20);
        Players.setRows(5);
        jScrollPane2.setViewportView(Players);

        winner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                winnerActionPerformed(evt);
            }
        });

        CurrentPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CurrentPlayerActionPerformed(evt);
            }
        });

        Monsters.setColumns(20);
        Monsters.setRows(5);
        jScrollPane3.setViewportView(Monsters);

        log.setColumns(20);
        log.setRows(5);
        jScrollPane4.setViewportView(log);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 49, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(winner, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CurrentPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(106, 106, 106)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(winner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(CurrentPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void winnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_winnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_winnerActionPerformed

    private void CurrentPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CurrentPlayerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CurrentPlayerActionPerformed

    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CurrentPlayer;
    private javax.swing.JTextArea Labyrinth;
    private javax.swing.JTextArea Monsters;
    private javax.swing.JTextArea Players;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea log;
    private javax.swing.JTextField winner;
    // End of variables declaration//GEN-END:variables

   @Override
   public void showGame(GameState gameState){
       this.Players.setText(gameState.getPlayers());
       this.Monsters.setText(gameState.getMonsters());
       this.winner.setText("Winner: " + gameState.getWinner());
       this.log.setText(gameState.getLog());
       this.Labyrinth.setText(gameState.getLabyrinth());
       this.CurrentPlayer.setText("Turn: " + gameState.getCurrentPlayer());
       this.repaint();
       
   }
   
   @Override
   public Directions nextMove(){
      
      return this.cursor.getDirection();
   }

}
