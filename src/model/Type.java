/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sm
 */
public class Type {
    private int id;
    private String libelletype;

    public Type(int id, String libelletype) {
        this.id = id;
        this.libelletype = libelletype;
    }

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelletype() {
        return libelletype;
    }

    public void setLibelletype(String libelletype) {
        this.libelletype = libelletype;
    }

     
    
}
