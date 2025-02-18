/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sm
 */
public class ItineraireEmploye {
    private  int id;
    private String employeid;
    private String itineraireid;

    public ItineraireEmploye(int id, String employeid, String itineraireid) {
        this.id = id;
        this.employeid = employeid;
        this.itineraireid = itineraireid;
    }

    public ItineraireEmploye() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeid() {
        return employeid;
    }

    public void setEmployeid(String employeid) {
        this.employeid = employeid;
    }

    public String getItineraireid() {
        return itineraireid;
    }

    public void setItineraireid(String itineraireid) {
        this.itineraireid = itineraireid;
    }
    
    
}
