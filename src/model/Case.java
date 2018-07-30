/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Type;
import java.util.List;

/**
 *
 * @author SONA
 */
public abstract class Case {

    int x;
    int y;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Case(Case Case) {
        this.x = Case.getX();
        this.y = Case.getY();

    }

    public abstract List<Element> getListeElement();

    public abstract Type getType();

}
