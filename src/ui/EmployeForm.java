/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import db.Db;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author sm
 */
public class EmployeForm extends javax.swing.JFrame {

    private Db db;

    /**
     * Creates new form EmployeForm
     */
    public EmployeForm() {
        try {
            initComponents();
            db = Db.getInstance();
            chargerUtilisateurs(); // Charger les utilisateurs dans la ComboBox
            chargerRoles(); // Charger les rôles dans la ComboBox
        } catch (Exception ex) {
            Logger.getLogger(EmployeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void chargerUtilisateurs() {
        try {
            String query = "SELECT id, nom, prenom FROM utilisateur"; // Sélectionner tous les utilisateurs
            ResultSet rs = db.executeSelect(query);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Ajouter un élément vide ou "Sélectionner un utilisateur"
            model.addElement("Sélectionner un utilisateur");

            while (rs.next()) {
                String nomComplet = rs.getString("nom") + " " + rs.getString("prenom");
                model.addElement(nomComplet); // Ajouter chaque utilisateur à la comboBox
            }
            utilisateur_cbx.setModel(model); // Mettre à jour le modèle de la ComboBox

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    /**
     * Charger les rôles dans la ComboBox
     */
    private void chargerRoles() {
        try {
            // Requête pour récupérer les rôles depuis la base de données
            String query = "SELECT DISTINCT libelle FROM roleem"; // Vous pouvez ajuster cette requête selon votre structure
            ResultSet rs = db.executeSelect(query);

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Ajouter un élément vide ou "Sélectionner un rôle"
            model.addElement("Sélectionner un rôle");

            // Ajouter les rôles récupérés depuis la base de données à la ComboBox
            while (rs.next()) {
                String role = rs.getString("libelle");
                model.addElement(role); // Ajouter chaque rôle à la ComboBox
            }

            role_cbx.setModel(model); // Mettre à jour le modèle de la ComboBox

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des rôles : " + e.getMessage());
        }
    }

    /**
     * Cette méthode est appelée lorsqu'un utilisateur sélectionne un rôle et un
     * utilisateur
     */
    private void affecterRole() {
        String selectedUtilisateur = (String) utilisateur_cbx.getSelectedItem();
        String selectedRole = (String) role_cbx.getSelectedItem();

        if ("Sélectionner un utilisateur".equals(selectedUtilisateur)) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur.");
            return;
        }

        if ("Sélectionner un rôle".equals(selectedRole)) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un rôle.");
            return;
        }

        // Extraire le nom et prénom de l'utilisateur sélectionné
        String[] nomPrenom = selectedUtilisateur.split(" ");
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

                // Vérifier si l'utilisateur a déjà un rôle
                String checkRoleQuery = "SELECT roleid FROM employe WHERE utilisateurid = ?";
                db.iniPreparedCmd(checkRoleQuery);
                db.getPstmt().setInt(1, utilisateurId);
                ResultSet rsRoleCheck = db.executePreparedSelect();

                if (rsRoleCheck.next() && rsRoleCheck.getInt("roleid") != 0) {
                    // Si un rôle est déjà assigné à l'utilisateur, afficher un message
                    JOptionPane.showMessageDialog(this, "Cet utilisateur a déjà un rôle attribué.");
                    return;
                }

                // Récupérer l'ID du rôle à partir du libellé
                String queryRole = "SELECT id FROM roleem WHERE libelle = ?";
                db.iniPreparedCmd(queryRole);
                db.getPstmt().setString(1, selectedRole);
                ResultSet rsRole = db.executePreparedSelect();

                if (rsRole.next()) {
                    int roleId = rsRole.getInt("id");
                    System.out.println("ID du rôle : " + roleId); // Débogage

                    // Insérer un nouvel enregistrement dans la table employe
                    String insertQuery = "INSERT INTO employe (utilisateurid, role, roleid) VALUES (?, ?, ?)";
                    db.iniPreparedCmd(insertQuery);
                    db.getPstmt().setInt(1, utilisateurId);
                    db.getPstmt().setString(2, selectedRole);
                    db.getPstmt().setInt(3, roleId);
                    int rowsInserted = db.executePreparedCUD();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "Le rôle " + selectedRole + " a été affecté à l'utilisateur " + nom + " " + prenom);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'affectation du rôle.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Rôle non trouvé.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Utilisateur non trouvé.");
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        utilisateur_cbx = new javax.swing.JComboBox<>();
        role_cbx = new javax.swing.JComboBox<>();
        valider_cbx = new javax.swing.JButton();
        home_tf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Assignation des roles par l'admin");

        jLabel2.setText("Utilisateur");

        jLabel3.setText("role");

        valider_cbx.setText("Valider");
        valider_cbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valider_cbxActionPerformed(evt);
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
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(valider_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(role_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(65, 65, 65)
                            .addComponent(utilisateur_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(home_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(utilisateur_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(role_cbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(valider_cbx)
                .addGap(18, 18, 18)
                .addComponent(home_tf)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void valider_cbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valider_cbxActionPerformed
        affecterRole(); // Affecter le rôle à l'utilisateur
    }//GEN-LAST:event_valider_cbxActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel home_tf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox<String> role_cbx;
    private javax.swing.JComboBox<String> utilisateur_cbx;
    private javax.swing.JButton valider_cbx;
    // End of variables declaration//GEN-END:variables
}
