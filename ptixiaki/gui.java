/**
 * gui.java
 * @author eva
 * @version Created on Apr 4, 2011, 6:50:17 PM
 */

package ptixiaki;

import java.awt.Cursor;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author eva
 */
public class gui extends javax.swing.JFrame {
    /**
     * Creates new form gui
     * kalei tin Full_Lattice.main() kai sindeete stin basi
     * document listeners otan allazei i timi se kapoia Jtextfields
     */

    public static String s,c;
    public static int k = 0, maxsupp = 0, start = 0;
    static int choise = 1;
    String ipsi[] = new String[6];
    public static int h[] = new int[6];
    public static String Text = new String();
    public static String SText = new String();
    public TreeMap<Integer, Node> komvoi;


    public gui() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            gui.this.setPreferredSize(new Dimension(768,200));
            initComponents();
            attrs.getDocument().addDocumentListener(new DocumentListener() {

                public void changedUpdate(DocumentEvent e) {
                 //   warn();
                }
                public void removeUpdate(DocumentEvent e) {
                 //   warn();
                }
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }
                public void warn() {
                    if (Integer.parseInt(attrs.getText()) > 6 || Integer.parseInt(attrs.getText()) <= 2) {
                        JOptionPane.showMessageDialog(rootPane, "Wrong input", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            k_l.getDocument().addDocumentListener(new DocumentListener() {

                public void changedUpdate(DocumentEvent e) {
                //    warning();
                }
                public void removeUpdate(DocumentEvent e) {
                //    warning();
                }
                public void insertUpdate(DocumentEvent e) {
                    warning();
                }
                public void warning() {
                    if (Integer.parseInt(k_l.getText()) == 2) {
                        klLabel.setText("L");
                    }
                    if (Integer.parseInt(k_l.getText()) != 1 && Integer.parseInt(k_l.getText()) != 2) {
                        JOptionPane.showMessageDialog(rootPane, "1 = k anonymity 2 = l diversity", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
        }catch(Exception e){
            System.out.println("Error setting native LAF: " + e);
        }
            Full_Lattice.main();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        initbut = new javax.swing.JButton();
        attrs = new javax.swing.JTextField();
        k_l = new javax.swing.JTextField();
        textLabel1 = new javax.swing.JLabel();
        textLabel2 = new javax.swing.JLabel();
        textLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        klLabel = new javax.swing.JLabel();
        maxsuppLabel = new javax.swing.JLabel();
        K = new javax.swing.JTextField();
        MAX_SUPP = new javax.swing.JTextField();
        more = new javax.swing.JButton();
        ipsos = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        workclassLabel = new javax.swing.JLabel();
        WC = new javax.swing.JTextField();
        RaceLabel = new javax.swing.JLabel();
        RAC = new javax.swing.JTextField();
        educationLabel = new javax.swing.JLabel();
        EDU = new javax.swing.JTextField();
        OccLabel = new javax.swing.JLabel();
        OCC = new javax.swing.JTextField();
        maritalstatusLabel = new javax.swing.JLabel();
        MST = new javax.swing.JTextField();
        ageLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newP = new javax.swing.JMenuItem();
        neg = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("draw");
        setName("window"); // NOI18N

        initbut.setText("Start");
        initbut.setEnabled(false);
        initbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initbutActionPerformed(evt);
            }
        });

        attrs.setEnabled(false);

        k_l.setEnabled(false);

        textLabel1.setText("The attributes are: Age, Work_class, Race, Occupation, Education, Marital Status");

        textLabel2.setText("Give me the number of attributes you want to create the lattice:");

        textLabel3.setText("Give me 1 for k-anonymity or 2 for l-diversity:");

        jLabel4.setText("H");

        klLabel.setText("K");

        maxsuppLabel.setText("Max Supp");

        K.setEnabled(false);

        MAX_SUPP.setEnabled(false);

        more.setText("Negotiate");
        more.setToolTipText("change H K or Max supp before more");
        more.setEnabled(false);
        more.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreActionPerformed(evt);
            }
        });

        ipsos.setEnabled(false);

        output.setColumns(25);
        output.setEditable(false);
        output.setFont(new java.awt.Font("URW Gothic L", 0, 12));
        output.setRows(3);
        output.setEnabled(false);
        jScrollPane1.setViewportView(output);

        workclassLabel.setText("WorkClass");

        WC.setEnabled(false);

        RaceLabel.setText("Race");

        RAC.setEnabled(false);

        educationLabel.setText("Education");

        EDU.setEnabled(false);

        OccLabel.setText("Occupation");

        OCC.setEnabled(false);

        maritalstatusLabel.setText("Marital Status");

        MST.setEnabled(false);

        ageLabel.setText("Age");

        jMenu1.setText("File");

        newP.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_MASK));
        newP.setText("Offline Creation of Lattice");
        newP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPActionPerformed(evt);
            }
        });
        jMenu1.add(newP);

        neg.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK));
        neg.setText("Negotiate");
        neg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negActionPerformed(evt);
            }
        });
        jMenu1.add(neg);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, 0));
        exit.setIcon(new javax.swing.ImageIcon("/home/eva/UOI/ptixiaki/draw/exit.png")); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textLabel2)
                                        .addComponent(textLabel3))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(attrs)
                                        .addComponent(k_l, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addComponent(initbut))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(more)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(klLabel)
                                            .addComponent(maxsuppLabel))
                                        .addGap(19, 19, 19))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ageLabel)
                                        .addGap(4, 4, 4)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(MAX_SUPP)
                                    .addComponent(K)
                                    .addComponent(ipsos, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                                .addGap(3, 3, 3)
                                .addComponent(workclassLabel)
                                .addGap(3, 3, 3)
                                .addComponent(WC, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(RaceLabel)
                                .addGap(4, 4, 4)
                                .addComponent(RAC, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(educationLabel)
                                .addGap(2, 2, 2)
                                .addComponent(EDU, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(OccLabel)
                                .addGap(2, 2, 2)
                                .addComponent(OCC, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(maritalstatusLabel)
                                .addGap(3, 3, 3)
                                .addComponent(MST, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(textLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textLabel2)
                    .addComponent(attrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textLabel3)
                    .addComponent(k_l, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(initbut)
                    .addComponent(more))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ipsos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workclassLabel)
                    .addComponent(WC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RaceLabel)
                    .addComponent(RAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageLabel)
                    .addComponent(educationLabel)
                    .addComponent(EDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OccLabel)
                    .addComponent(OCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maritalstatusLabel)
                    .addComponent(MST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(klLabel)
                    .addComponent(K, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxsuppLabel)
                    .addComponent(MAX_SUPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

/**
 * Otan patithei to start arxizei i ektelesi
 */
    private void initbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initbutActionPerformed
               
        int maxAttr = 6;
        int sa = 0, ca = 0;
	String TableName="C";
	int size = 1;
	start = 1;
	Suggestion sug;
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        if (Full_Lattice.conn != null){
            s = attrs.getText();
            c = k_l.getText();
            sa = Integer.parseInt(s);
            ca = Integer.parseInt(c);
            try{
                Full_Lattice.user_initialization();
                Full_Lattice.drop_tables(maxAttr);
                long start = System.currentTimeMillis();
                Full_Lattice.incog(sa,size);
                
                if(ca==1){
                    k_anonymity histo_k_anonymity= new k_anonymity(Full_Lattice.conn,sa,TableName);
                    komvoi=histo_k_anonymity.creat_histogramm();
                  //Full_Lattice.importance_of_nodes(komvoi);	//mas xreiazontan gia na metrisoume thn symantikotita ton komvo, allios den to xreiazomaste
                }else{
                    //Kodikas gia l-diversity
                    l_diversity histo_l_diversity= new l_diversity(Full_Lattice.conn,sa,TableName);
                    komvoi=histo_l_diversity.creat_histogramm();
                }
                long end = System.currentTimeMillis();
                long offline_time=end-start;              
                sug = new Suggestion(Full_Lattice.conn,sa,komvoi,choise);
                //Suggestion.check_the_parametres();

                jung.draw();

                if (Integer.parseInt(attrs.getText()) == 3) {
                    ipsos.setEnabled(true);
                    WC.setEnabled(true);
                    RAC.setEnabled(true);
                    EDU.setEnabled(false);
                    OCC.setEnabled(false);
                    MST.setEnabled(false);
                    jLabel9.setText("The attributes are : Age, Workclass, Occupation");
                } else if (Integer.parseInt(attrs.getText()) == 4) {
                    ipsos.setEnabled(true);
                    WC.setEnabled(true);
                    RAC.setEnabled(true);
                    EDU.setEnabled(true);
                    OCC.setEnabled(false);
                    MST.setEnabled(false);
                    jLabel9.setText("The attributes are : Age, Workclass, Occupation, Education");
                } else if (Integer.parseInt(attrs.getText()) == 5) {
                    ipsos.setEnabled(true);
                    WC.setEnabled(true);
                    RAC.setEnabled(true);
                    EDU.setEnabled(true);
                    OCC.setEnabled(true);
                    MST.setEnabled(false);
                    jLabel9.setText("The attributes are : Age, Workclass, Occupation, Education, Race");
                } else if (Integer.parseInt(attrs.getText()) == 6) {
                    ipsos.setEnabled(true);
                    WC.setEnabled(true);
                    RAC.setEnabled(true);
                    EDU.setEnabled(true);
                    OCC.setEnabled(true);
                    MST.setEnabled(true);
                    jLabel9.setText("The attributes are : Age, Workclass, Occupation, Education, Race, Marital Status");
                }
                K.setEnabled(true);
                MAX_SUPP.setEnabled(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                //more.setEnabled(true);
            }catch(Exception e){//Catch exception if any
                System.err.println("try-catch inputbut: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_initbutActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed

        /**
         * i sindesi stin vasi klinei otan klisei kai to gui
         */
        try{          
            Full_Lattice.conn.close();
            System.out.println ("Database connection terminated");
        }catch (Exception e){
            System.err.println("exit " + e.getMessage());
        }
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed


    private void moreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreActionPerformed
        gui.this.repaint();
        int hi = 0;
        start = 0;
        String l1, l2, l3;
        String st1, st2, st3, anon;
        String attributes[] = {"Age","Workclass", "Occupation", "Education", "Race", "Marital Status"};
            try{
                FileWriter fstream1 = new FileWriter("lysh.txt", true);
                Suggestion.out1= new BufferedWriter(fstream1);
                if(Integer.parseInt(c) == 1){
                    anon = "k";
                }
                else{
                    anon = "l";
                }
                ipsi[0] = ipsos.getText(); ipsi[1] = WC.getText(); ipsi[2] = RAC.getText();
                ipsi[3] = EDU.getText(); ipsi[4] = OCC.getText(); ipsi[5] = MST.getText();
                for(hi = 0; hi < get.end(); hi++){
                    h[hi] = Integer.parseInt(ipsi[hi]);
                }                
                k = Integer.parseInt(K.getText());
                maxsupp = Integer.parseInt(MAX_SUPP.getText());

                if(k <= 0 || maxsupp <= 0 || s == null || c == null){
                    JOptionPane.showMessageDialog(rootPane, "Check your arguments !!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                Suggestion.check_the_parametres();
                //Suggestion.check_if_its_ok();               
                SText += "Height:   ";
                for(int j = 0; j < get.end(); j++){
                    SText += attributes[j] + ": "+h[j]+" ";
                }
                //SText = Text;
                String levels = get.level(0);
                l1 = get.level(1); l2 = get.level(2); l3 = get.level(3);
                st1 = get.suppTupp(1); st2 = get.suppTupp(2); st3 = get.suppTupp(3);
                if(Suggestion.relax == false){
                    Text += SText + anon +": "+ k+ " max supp: " +maxsupp +" Suppresed Tuples: " + Suggestion.suppTup + " level: "+ levels +" \n";
                    SText = "";
                }
                else{
                    Text += "Relaxation 1  " + SText +" " +anon +": "+ k + " max supp: " +maxsupp +" Suppresed Tuples: " + st1 + " level: "+ l1 +" \n" +
                            "Relaxation 2  " + SText +" " +anon +": "+ k + " max supp: " +maxsupp +" Suppresed Tuples: " + st2 + " level: "+ l2 +" \n" +
                            "Relaxation 3  " + SText +" " +anon +": "+ k + " max supp: " +maxsupp +" Suppresed Tuples: " + st3 + " k-anonymity: " + Suggestion.k_R3 +" level: "+ l3 +" \n";
                    //Suggestion.Level2 = "";
                    get.setlevel("", 2);
                    SText = "";
                }
                output.setEnabled(true);
                output.setText(Text);
                jung.draw();
                Suggestion.out1.close();
            }catch(Exception e){//Catch exception if any
                System.err.println("try-catch more: " + e.getMessage());
            }
            try{
                Suggestion.out1.close();
            }catch(Exception e){//Catch exception if any
                System.err.println("try-catch more out1: " + e.getMessage());
            }
    }//GEN-LAST:event_moreActionPerformed

    private void newPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPActionPerformed
        attrs.setEnabled(true);
        k_l.setEnabled(true);
        initbut.setEnabled(true);
}//GEN-LAST:event_newPActionPerformed

    private void negActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negActionPerformed
        attrs.setEnabled(true);
        k_l.setEnabled(true);
        initbut.setEnabled(true);
        more.setEnabled(true);
        gui.this.setSize(768, 485);
}//GEN-LAST:event_negActionPerformed
 

    /**
    * @param args the command line arguments
    */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EDU;
    public static javax.swing.JTextField K;
    public static javax.swing.JTextField MAX_SUPP;
    private javax.swing.JTextField MST;
    private javax.swing.JTextField OCC;
    private javax.swing.JLabel OccLabel;
    private javax.swing.JTextField RAC;
    private javax.swing.JLabel RaceLabel;
    private javax.swing.JTextField WC;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField attrs;
    private javax.swing.JLabel educationLabel;
    private javax.swing.JMenuItem exit;
    private javax.swing.JButton initbut;
    private javax.swing.JTextField ipsos;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField k_l;
    private javax.swing.JLabel klLabel;
    private javax.swing.JLabel maritalstatusLabel;
    private javax.swing.JLabel maxsuppLabel;
    public static javax.swing.JButton more;
    private javax.swing.JMenuItem neg;
    private javax.swing.JMenuItem newP;
    private javax.swing.JTextArea output;
    private javax.swing.JLabel textLabel1;
    private javax.swing.JLabel textLabel2;
    private javax.swing.JLabel textLabel3;
    private javax.swing.JLabel workclassLabel;
    // End of variables declaration//GEN-END:variables

}
