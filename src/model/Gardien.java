/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author SONA
 */
public class Gardien {

    ArrayList<Memento> mementos = new ArrayList<>();

    public void demandeMemento(Memento m) {

        mementos.add(m);

    }

    public void demandeMementoP(Memento m) {

        mementos.add(mementos.size() - 1, m);

    }

    public Memento get(int i) {
        return mementos.get(i);
    }

    public ArrayList<Memento> getMementos() {
        return mementos;
    }

}
