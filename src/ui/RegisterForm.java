/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import db.Db;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.Role;

/**
 *
 * @author sm
 */
public class RegisterForm extends javax.swing.JFrame {

    private Db db;//variable de type db permettant la connexion avec la base de donnees
    private List<Role> ListRole = new ArrayList<>();//liste de roles permettant de recuperer toutes les roles de la base de données

    /**
     * Creates new form RegisterForm
     */
    public RegisterForm() {
        try {
            initComponents();
            //ettablissons la connexion avec la base
            db = Db.getInstance();
            loadRole();//charge les elts du formulaire
        } catch (Exception ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadRole() {
        try {
            // Vérifier si un admin existe déjà dans la base de données
            String sqlCheckAdmin = "SELECT COUNT(*) FROM utilisateur u JOIN role r ON u.roleid = r.id WHERE r.role = 'admin'";
            ResultSet rsAdmin = db.executeSelect(sqlCheckAdmin);
            rsAdmin.next();
            boolean adminExists = rsAdmin.getInt(1) > 0;

            // Charger tous les rôles
            String sql = "SELECT * FROM role";
            ResultSet rs = db.executeSelect(sql);
            while (rs.next()) {
                Role role = new Role(rs.getInt("id"), rs.getString("role"));

                // Si un admin existe, ne pas ajouter l'option "admin" dans le combobox
                if (adminExists && role.getRole().equals("admin")) {
                    continue; // On saute l'admin
                }
                ListRole.add(role);
            }

            // Ajouter les rôles restants dans le combobox
            for (Role item : ListRole) {
                role_cbx.addItem(item.getRole());
            }

        } catch (Exception ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearField() {
        prenom_tf.setText("");
        nom_tf.setText("");
        email_tf.setText("");
        password_tf.setText("");
    }

    private boolean testField() {
        // Récupération des valeurs des champs
        String nom = nom_tf.getText().trim();
        String prenom = prenom_tf.getText().trim();
        String email = email_tf.getText().trim();
        String password = new String(password_tf.getPassword()).trim(); // Utilisation de getPassword() pour récupérer le mot de passe
        String confirmpassword = new String(confirmpassword_tf.getPassword()).trim(); // Utilisation de getPassword() pour récupérer le mot de passe

        // Vérification des champs vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs!");
            return false;
        }

        // Vérification de la longueur du mot de passe
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit comporter au moins 6 caractères!");
            return false;
        }

        // Vérification que les mots de passe correspondent
        if (!password.equals(confirmpassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne sont pas identiques!");
            return false;
        }

        return true;
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        role = new javax.swing.JLabel();
        prenom_tf = new javax.swing.JTextField();
        nom_tf = new javax.swing.JTextField();
        email_tf = new javax.swing.JTextField();
        password_tf = new javax.swing.JPasswordField();
        confirmpassword_tf = new javax.swing.JPasswordField();
        connecter_btn = new javax.swing.JButton();
        inscrire_btn = new javax.swing.JButton();
        role_cbx = new javax.swing.JComboBox<>();
        seconnecter_btn = new javax.swing.JButton();
        sinscrire_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1200, 800));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Prenom");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 35, -1, -1));

        jLabel2.setText("Nom");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 97, -1, -1));

        jLabel3.setText("Email");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 153, -1, -1));

        jLabel5.setText("Password");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        jLabel6.setText("Confirm Password");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        role.setText("role");
        getContentPane().add(role, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));
        getContentPane().add(prenom_tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 150, -1));
        getContentPane().add(nom_tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 150, -1));

        email_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                email_tfActionPerformed(evt);
            }
        });
        getContentPane().add(email_tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 150, -1));
        getContentPane().add(password_tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 150, -1));
        getContentPane().add(confirmpassword_tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 150, -1));

        connecter_btn.setText("Se connecter");
        connecter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connecter_btnActionPerformed(evt);
            }
        });
        getContentPane().add(connecter_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 738, -1, -1));

        inscrire_btn.setText("S'inscrire");
        inscrire_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inscrire_btnActionPerformed(evt);
            }
        });
        getContentPane().add(inscrire_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1015, 738, -1, -1));

        role_cbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                role_cbxActionPerformed(evt);
            }
        });
        getContentPane().add(role_cbx, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 150, -1));

        seconnecter_btn.setText("se connecter");
        seconnecter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seconnecter_btnActionPerformed(evt);
            }
        });
        getContentPane().add(seconnecter_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        sinscrire_btn.setText("s'inscrire");
        sinscrire_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinscrire_btnActionPerformed(evt);
            }
        });
        getContentPane().add(sinscrire_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void role_cbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_role_cbxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_role_cbxActionPerformed

    private void inscrire_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inscrire_btnActionPerformed

    }//GEN-LAST:event_inscrire_btnActionPerformed

    private void connecter_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connecter_btnActionPerformed

    }//GEN-LAST:event_connecter_btnActionPerformed

    private void email_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email_tfActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void sinscrire_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinscrire_btnActionPerformed
        try {
        // Vérification des champs avant l'insertion
        if (!testField()) {
            return; // Si la validation échoue, on arrête l'insertion
        }

        String nom = nom_tf.getText();
        String prenom = prenom_tf.getText();
        String email = email_tf.getText();
        String password = new String(password_tf.getPassword());

        // Vérifier si l'email existe déjà dans la base de données
        String sqlCheckEmail = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        db.iniPreparedCmd(sqlCheckEmail);
        db.getPstmt().setString(1, email);
        ResultSet rsEmail = db.executePreparedSelect();
        rsEmail.next();
        
        // Si l'email existe déjà, afficher un message d'erreur
        if (rsEmail.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé. Veuillez en choisir un autre.");
            return; // Si l'email est déjà pris, on arrête l'insertion
        }

        // Si l'email est unique, procéder à l'insertion de l'utilisateur
        int positionRole = role_cbx.getSelectedIndex();
        int role_id = ListRole.get(positionRole).getId();

        String sql = "INSERT INTO utilisateur(nom, prenom, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
        db.iniPreparedCmd(sql);
        db.getPstmt().setString(1, nom);
        db.getPstmt().setString(2, prenom);
        db.getPstmt().setString(3, email);
        db.getPstmt().setString(4, password);
        db.getPstmt().setInt(5, role_id);
        
        int row = db.executePreparedCUD();
        if (row > 0) {
            JOptionPane.showMessageDialog(this, "Insertion réussie");
            clearField(); // Optionnel, pour vider les champs après une inscription réussie
        } else {
            JOptionPane.showMessageDialog(this, "Insertion échouée");
        }
    } catch (Exception ex) {
        Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_sinscrire_btnActionPerformed

    private void seconnecter_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seconnecter_btnActionPerformed
        this.hide();
        new SignInForm().setVisible(true);
    }//GEN-LAST:event_seconnecter_btnActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField confirmpassword_tf;
    private javax.swing.JButton connecter_btn;
    private javax.swing.JTextField email_tf;
    private javax.swing.JButton inscrire_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField nom_tf;
    private javax.swing.JPasswordField password_tf;
    private javax.swing.JTextField prenom_tf;
    private javax.swing.JLabel role;
    private javax.swing.JComboBox<String> role_cbx;
    private javax.swing.JButton seconnecter_btn;
    private javax.swing.JButton sinscrire_btn;
    // End of variables declaration//GEN-END:variables
}
