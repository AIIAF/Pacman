/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;
import static control.Type.CHAMPIGNON;

/**
 *
 * @author SONA
 */
public class Champignon extends Aliments {

    public Champignon(Champignon champignon) {

        this();

    }

    public Champignon() {

    }

    @Override
    public Type getType() {
        return CHAMPIGNON;
    }

}
