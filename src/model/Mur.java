/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;
import static control.Type.MUR;
import java.util.List;

/**
 *
 * @author SONA & HAYTHEM
 */
public class Mur extends Case {

    public Mur(int i, int j) {
        super(i, j);
    }

    @Override
    public Type getType() {
        return MUR;
    }

    @Override
    public List<Element> getListeElement() {
        return null;
    }

}
