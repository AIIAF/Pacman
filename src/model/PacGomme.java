/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;
import static control.Type.PACGOMME;

/**
 *
 * @author SONA
 */
public class PacGomme extends Aliments {

    public PacGomme(PacGomme pacGomme) {
        this();
    }

    public PacGomme() {

    }

    @Override
    public Type getType() {
        return PACGOMME;
    }

}
