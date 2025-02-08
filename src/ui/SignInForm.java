/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import db.Db;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Role;
import model.Utilisateur;

/**
 *
 * @author sm
 */
public class SignInForm extends javax.swing.JFrame {

    private List<Role> ListRole = new ArrayList<>();
    private Db db;

    public SignInForm() {
        try {
            initComponents();
            db = Db.getInstance();
            loadRole();
        } catch (Exception ex) {
            Logger.getLogger(SignInForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadRole() {
        try {
            String sql = "SELECT * FROM role";
            ResultSet rs = db.executeSelect(sql);
            while (rs.next()) {
                ListRole.add(new Role(rs.getInt("id"), rs.getString("role")));
            }

        } catch (Exception ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
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

        user = new javax.swing.JLabel();
        password = new javax.swing.JLabel();
        email_tf = new javax.swing.JTextField();
        password_tf = new javax.swing.JTextField();
        inscrire_btn = new javax.swing.JButton();
        connecter_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        user.setText("Email");

        password.setText("Password");

        inscrire_btn.setText("S'inscrire");
        inscrire_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inscrire_btnActionPerformed(evt);
            }
        });

        connecter_btn.setText("Se connecter");
        connecter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connecter_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(inscrire_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(connecter_btn)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(email_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(85, 85, 85)
                        .addComponent(password_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(92, 92, 92))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(user))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(password)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(password_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inscrire_btn)
                    .addComponent(connecter_btn))
                .addGap(84, 84, 84))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inscrire_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inscrire_btnActionPerformed
        this.hide();
        new RegisterForm().setVisible(true);
    }//GEN-LAST:event_inscrire_btnActionPerformed

    private void connecter_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connecter_btnActionPerformed
        try {
            String email = email_tf.getText().trim();
            String password = password_tf.getText().trim();

            // Ajouter un log pour afficher ce que l'utilisateur a saisi
            System.out.println("Nom d'utilisateur saisi: " + email);
            System.out.println("Mot de passe saisi: " + password);

            String sql = "SELECT * FROM utilisateur WHERE email = ?";
            db.iniPreparedCmd(sql);
            db.getPstmt().setString(1, email);
            ResultSet rs = db.executePreparedSelect();

            if (rs.next()) {
                Utilisateur us = new Utilisateur();
                us.setId(rs.getInt("id"));
                us.setNom(rs.getString("nom"));
                us.setPrenom(rs.getString("prenom"));
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setRole_id(rs.getInt("role_id"));
                us.setStatut(rs.getBoolean("statut"));

                // Affichage du mot de passe stocké en base
                System.out.println("Mot de passe de la base de données: " + us.getPassword());

                // Vérification du mot de passe
                if (!us.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login ou password incorrect");
                } else {
                    // Recherche du rôle de l'utilisateur
                    Role roleUser = null;
                    for (Role role : ListRole) {
                        if (role.getId() == us.getRole_id()) {
                            roleUser = role;
                        }
                    }

                    // Redirection en fonction du rôle
                    if (roleUser != null && roleUser.getRole().equalsIgnoreCase("Admin")) {
                        new BoardForm().setVisible(true);  // Formulaire Admin
                    } else {
                        new TaskForm().setVisible(true);  // Formulaire Utilisateur
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login ou password incorrect");
            }
        } catch (Exception ex) {
            Logger.getLogger(SignInForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_connecter_btnActionPerformed

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
            java.util.logging.Logger.getLogger(SignInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignInForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignInForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connecter_btn;
    private javax.swing.JTextField email_tf;
    private javax.swing.JButton inscrire_btn;
    private javax.swing.JLabel password;
    private javax.swing.JTextField password_tf;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}
