/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;
import static control.Type.FRUIT;

/**
 *
 * @author SONA
 */
public class Fruit extends Aliments {

    public Fruit(Fruit fruit) {
        this();
    }

    public Fruit() {

    }

    @Override
    public Type getType() {
        return FRUIT;
    }

}
