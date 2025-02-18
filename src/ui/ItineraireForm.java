/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import db.Db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author sm
 */
public class ItineraireForm extends javax.swing.JFrame {

    private Db db;

    /**
     * Creates new form ItineraireForm
     */
    public ItineraireForm() {
        try {
            initComponents();
            db = Db.getInstance();
            loadEmployes();
            loadItineraires();
        } catch (Exception ex) {
            Logger.getLogger(EmployeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadEmployes() {
        try {
            String query = "SELECT u.prenom, u.nom "
                    + "FROM utilisateur u "
                    + "JOIN employe e ON u.id = e.utilisateurid "
                    + // Jointure entre les deux tables
                    "WHERE e.roleemid = 1"; // Sélectionner tous les utilisateurs
            ResultSet rs = db.executeSelect(query);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Ajouter un élément vide ou "Sélectionner un utilisateur"
            model.addElement("Sélectionner un employe");

            while (rs.next()) {
                String nomComplet = rs.getString("nom") + " " + rs.getString("prenom");
                model.addElement(nomComplet); // Ajouter chaque utilisateur à la comboBox
            }
            employe_cbx.setModel(model); // Mettre à jour le modèle de la ComboBox

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    private void loadItineraires() {
        try {
            String sql = "SELECT * FROM itineraire"; // Modifier avec votre table des itineraires
            ResultSet rs = null;
            try {
                rs = db.executeSelect(sql); // Utiliser l'instance 'db' pour appeler 'executeSelect'
            } catch (Exception ex) {
                Logger.getLogger(TrashForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (rs.next()) {
                itineraire_cbx.addItem(rs.getString("libelle"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void affecterItineraire() {
        String selectedEmploye = (String) employe_cbx.getSelectedItem();
        String selectedItineraire = (String) itineraire_cbx.getSelectedItem();

        if ("Sélectionner un utilisateur".equals(selectedEmploye)) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un employe.");
            return;
        }

        if ("Sélectionner un itineraire".equals(selectedItineraire)) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un itineraire.");
            return;
        }

        // Extraire le nom et prénom de l'utilisateur sélectionné
        String[] nomPrenom = selectedEmploye.split(" ");
        String nom = nomPrenom[0];
        String prenom = nomPrenom[1];

        try {
            // Récupérer l'ID de l'utilisateur à partir du nom et prénom
            String queryUtilisateur = "SELECT id FROM utilisateur WHERE nom = ? AND prenom = ?";
            db.iniPreparedCmd(queryUtilisateur);
            db.getPstmt().setString(1, nom);
            db.getPstmt().setString(2, prenom);
            ResultSet rsUtilisateur = db.executePreparedSelect();

            if (rsUtilisateur.next()) {
                int utilisateurId = rsUtilisateur.getInt("id");
                System.out.println("ID de l'utilisateur : " + utilisateurId); // Débogage

                // Vérifier si l'utilisateur a déjà un itinéraire affecté
                String checkItineraireQuery = "SELECT COUNT(*) FROM itineraireemploye WHERE employeid = ?";
                db.iniPreparedCmd(checkItineraireQuery);
                db.getPstmt().setInt(1, utilisateurId);
                ResultSet rsItineraireCheck = db.executePreparedSelect();

                if (rsItineraireCheck.next() && rsItineraireCheck.getInt(1) > 0) {
                    // Si l'utilisateur a déjà un itinéraire, afficher un message
                    JOptionPane.showMessageDialog(this, "Cet utilisateur a déjà un itinéraire attribué.");
                    return;
                }

                // Vérifier si l'itinéraire a déjà 24 employés affectés
                String checkItineraireCountQuery = "SELECT COUNT(*) FROM itineraireemploye WHERE itineraireid = ?";
                db.iniPreparedCmd(checkItineraireCountQuery);
                db.getPstmt().setString(1, selectedItineraire);
                ResultSet rsItineraireCount = db.executePreparedSelect();

                if (rsItineraireCount.next() && rsItineraireCount.getInt(1) >= 24) {
                    // Si l'itinéraire a déjà 24 employés, afficher un message
                    JOptionPane.showMessageDialog(this, "Cet itinéraire a déjà 24 employés affectés.");
                    return;
                }

                // Récupérer le libellé de l'itinéraire à partir du nom
                String queryItineraire = "SELECT libelle FROM itineraire WHERE libelle = ?";
                db.iniPreparedCmd(queryItineraire);
                db.getPstmt().setString(1, selectedItineraire);
                ResultSet rsItineraire = db.executePreparedSelect();

                if (rsItineraire.next()) {
                    String itineraireLibelle = rsItineraire.getString("libelle");
                    System.out.println("Libellé de l'itinéraire : " + itineraireLibelle); // Débogage

                    // Insérer un nouvel enregistrement dans la table itineraireemploye
                    String insertQuery = "INSERT INTO itineraireemploye (employeid, itineraireid) VALUES (?, ?)";
                    db.iniPreparedCmd(insertQuery);
                    db.getPstmt().setInt(1, utilisateurId);  // ID de l'employé
                    db.getPstmt().setString(2, itineraireLibelle);  // Libellé de l'itinéraire
                    int rowsInserted = db.executePreparedCUD();

                    if (rowsInserted > 0) {
                        // Affichage du nom et prénom de l'employé dans le message
                        JOptionPane.showMessageDialog(this, "L'itinéraire " + itineraireLibelle + " a été affecté à l'employé " + nom + " " + prenom);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'affectation de l'itinéraire.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Itinéraire non trouvé.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Employé non trouvé.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        employe_cbx = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        itineraire_cbx = new javax.swing.JComboBox<>();
        valider_btn = new javax.swing.JButton();
        home_tf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Employe");

        jLabel2.setText("Itineraire");

        valider_btn.setText("Valider");
        valider_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valider_btnActionPerformed(evt);
            }
        });

        home_tf.setText("Home");
        home_tf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_tfMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(itineraire_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(employe_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(valider_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(home_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(employe_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(itineraire_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(valider_btn)
                .addGap(27, 27, 27)
                .addComponent(home_tf)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void valider_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valider_btnActionPerformed
        affecterItineraire();
    }//GEN-LAST:event_valider_btnActionPerformed

    private void home_tfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_tfMouseClicked
         this.hide();
        new BoardForm().setVisible(true);
    }//GEN-LAST:event_home_tfMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ItineraireForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItineraireForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItineraireForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItineraireForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItineraireForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> employe_cbx;
    private javax.swing.JLabel home_tf;
    private javax.swing.JComboBox<String> itineraire_cbx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton valider_btn;
    // End of variables declaration//GEN-END:variables
}
