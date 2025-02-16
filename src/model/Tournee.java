/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sm
 */
public class Tournee {
    private int id;
    private int employeid;
    private int zoneid;
    private String datetournee;

    public Tournee(int id, int employeid, int zoneid, String datetournee) {
        this.id = id;
        this.employeid = employeid;
        this.zoneid = zoneid;
        this.datetournee = datetournee;
    }

    public Tournee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeid() {
        return employeid;
    }

    public void setEmployeid(int employeid) {
        this.employeid = employeid;
    }

    public int getZoneid() {
        return zoneid;
    }

    public void setZoneid(int zoneid) {
        this.zoneid = zoneid;
    }

    public String getDatetournee() {
        return datetournee;
    }

    public void setDatetournee(String datetournee) {
        this.datetournee = datetournee;
    }
    
    
}
