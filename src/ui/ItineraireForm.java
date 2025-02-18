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

        // Validation des champs
        if ("Sélectionner un utilisateur".equals(selectedEmploye)) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un employé.");
            return;
        }

        if ("Sélectionner un itinéraire".equals(selectedItineraire)) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un itinéraire.");
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

                // Récupérer l'ID de l'itinéraire à partir du libellé
                String queryItineraire = "SELECT id FROM itineraire WHERE libelle = ?";
                db.iniPreparedCmd(queryItineraire);
                db.getPstmt().setString(1, selectedItineraire);
                ResultSet rsItineraire = db.executePreparedSelect();

                if (rsItineraire.next()) {
                    int itineraireId = rsItineraire.getInt("id");

                    // Vérifier combien d'employés sont déjà affectés à cet itinéraire
                    String countQuery = "SELECT COUNT(*) FROM itineraireemploye WHERE itineraireid = ?";
                    db.iniPreparedCmd(countQuery);
                    db.getPstmt().setInt(1, itineraireId);
                    ResultSet rsCount = db.executePreparedSelect();

                    if (rsCount.next() && rsCount.getInt(1) >= 24) {
                        // Si le nombre d'employés affectés à cet itinéraire est déjà 24 ou plus
                        JOptionPane.showMessageDialog(this, "Cet itinéraire a déjà 24 employés affectés. Vous ne pouvez pas en ajouter d'autres.");
                        return;
                    }

                    // Vérifier si l'employé est déjà affecté à cet itinéraire
                    String checkIfAssigned = "SELECT COUNT(*) FROM itineraireemploye WHERE employeid = ? AND itineraireid = ?";
                    db.iniPreparedCmd(checkIfAssigned);
                    db.getPstmt().setInt(1, utilisateurId);
                    db.getPstmt().setInt(2, itineraireId);
                    ResultSet rsCheck = db.executePreparedSelect();

                    if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                        // Si l'employé est déjà affecté à cet itinéraire
                        JOptionPane.showMessageDialog(this, "Cet employé est déjà affecté à cet itinéraire.");
                        return;
                    }

                    // Insertion dans la table itineraireemploye
                    String insertQuery = "INSERT INTO itineraireemploye (employeid, itineraireid) VALUES (?, ?)";
                    db.iniPreparedCmd(insertQuery);
                    db.getPstmt().setInt(1, utilisateurId);
                    db.getPstmt().setInt(2, itineraireId);
                    int rowsInserted = db.executePreparedCUD();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "L'itinéraire " + selectedItineraire + " a été affecté à l'employé " + nom + " " + prenom);
                        employe_cbx.setSelectedIndex(0);  // Réinitialiser les sélections
                        itineraire_cbx.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'affectation de l'itinéraire.");
                    }
                } else {
                    // L'itinéraire n'est pas trouvé dans la base
                    JOptionPane.showMessageDialog(this, "L'itinéraire n'a pas été trouvé.");
                }
            } else {
                // L'employé n'est pas trouvé dans la base
                JOptionPane.showMessageDialog(this, "L'employé n'a pas été trouvé.");
            }
        } catch (Exception e) {
            // Gestion d'exception générique
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Employe");

        jLabel2.setText("Itineraire");

        valider_btn.setText("Valider");
        valider_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valider_btnActionPerformed(evt);
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
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void valider_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valider_btnActionPerformed
        affecterItineraire();
    }//GEN-LAST:event_valider_btnActionPerformed

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
    private javax.swing.JComboBox<String> itineraire_cbx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton valider_btn;
    // End of variables declaration//GEN-END:variables
}
