/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;
import static control.Type.VIDE;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 0201sosay
 */
public class Vide extends Case {

    List<Element> listeElement = new ArrayList<>();

    public Vide(int i, int j) {
        super(i, j);
    }

    public Vide(List<Element> listeElement, int i, int j) {
        super(i, j);
        this.listeElement = listeElement;

    }

    @Override
    public List<Element> getListeElement() {
        return listeElement;
    }

    @Override
    public Type getType() {
        if (listeElement.isEmpty()) {
            return VIDE;
        } else {
            return listeElement.get(listeElement.size() - 1).getType();
        }
    }

}
