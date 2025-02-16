/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sm
 */
public class Zone {
    private  int id;
    private String nomzone;

    public Zone(int id, String nomzone) {
        this.id = id;
        this.nomzone = nomzone;
    }

    public Zone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomzone() {
        return nomzone;
    }

    public void setNomzone(String nomzone) {
        this.nomzone = nomzone;
    }
    
    
}
