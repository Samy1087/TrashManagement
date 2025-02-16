/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sm
 */
public class Employe {
    private int id;
    private int utilisateurid;
    private String role;
    private int roleemid;

    public Employe(int id, int utilisateurid, String role, int roleemid) {
        this.id = id;
        this.utilisateurid = utilisateurid;
        this.role = role;
        this.roleemid = roleemid;
    }

    public Employe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurid() {
        return utilisateurid;
    }

    public void setUtilisateurid(int utilisateurid) {
        this.utilisateurid = utilisateurid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRoleemid() {
        return roleemid;
    }

    public void setRoleemid(int roleemid) {
        this.roleemid = roleemid;
    }

   
    
}
