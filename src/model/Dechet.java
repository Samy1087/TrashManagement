/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sm
 */
public class Dechet {
    private int id;
    private int typeid;
    private int employeid;
    private int quantite;
    private String datecollecte;
    private int zoneid;
    private String traitement;

    public Dechet(int id, int typeid, int employeid, int quantite, String datecollecte, int zoneid, String traitement) {
        this.id = id;
        this.typeid = typeid;
        this.employeid = employeid;
        this.quantite = quantite;
        this.datecollecte = datecollecte;
        this.zoneid = zoneid;
        this.traitement = traitement;
    }

    public Dechet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getEmployeid() {
        return employeid;
    }

    public void setEmployeid(int employeid) {
        this.employeid = employeid;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDatecollecte() {
        return datecollecte;
    }

    public void setDatecollecte(String datecollecte) {
        this.datecollecte = datecollecte;
    }

    public int getZoneid() {
        return zoneid;
    }

    public void setZoneid(int zoneid) {
        this.zoneid = zoneid;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }
    
    
}
