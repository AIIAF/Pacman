/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.ChoixTouche;
import java.util.List;

/**
 *
 * @author SONA
 */
public abstract class Fantome extends PersonnageMobile {

    ChoixTouche ct;
    int taille;

    public Fantome() {

    }

    public Fantome(Fantome f) {
        this.setCt(f.getCt());
        this.setTaille(f.getTaille());
        this.setCurrentX(f.getCurrentX());
        this.setCurrentY(f.getCurrentY());
        this.setInitialeX(f.getInitialeX());
        this.setInitialeY(f.getInitialeY());

    }

    public void fusion(Labyrinthe labyrinthe, int x, int y) {
        FantomeComposite fc = new FantomeComposite();
        fc.ct = null;
        int px = getCurrentX();
        int py = getCurrentY();
        for (int i = 0; i < labyrinthe.getListeFantome().size(); ++i) {       
            if (getCurrentX() == labyrinthe.getListeFantome().get(i).getCurrentX() && getCurrentY() == labyrinthe.getListeFantome().get(i).getCurrentY()) {
                fc.ajoutFantome(labyrinthe.getListeFantome().get(i));
                labyrinthe.getListeFantome().get(i).setInitialeX(getCurrentX());
                labyrinthe.getListeFantome().get(i).setInitialeY(getCurrentY());
                labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(labyrinthe.getListeFantome().get(i));
            }

        }
        labyrinthe.getListeFantome().add(fc);
        for (int i = 0; i < fc.getListePM().size(); i++) {
            labyrinthe.getListeFantome().remove(fc.getListePM().get(i));
        }

        fc.setCurrentX(px);
        fc.setCurrentY(py);
        fc.setInitialeX(px);
        fc.setInitialeY(py);
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().add(fc);
        

    }

    public abstract int getTaille();

    public abstract ChoixTouche getCt();

    public abstract void setCt(ChoixTouche ct);

    public abstract List<Fantome> getListePM();

    public abstract void fantomeDansCaseAleatoire(Labyrinthe labyrinthe);

    public abstract List<Fantome> getGroupeFantome();

    public abstract void setGroupeFantome(List<Fantome> groupeFantome);

    public abstract void setTaille(int taille);

    public abstract void mourir(Labyrinthe labyrinthe);

}
