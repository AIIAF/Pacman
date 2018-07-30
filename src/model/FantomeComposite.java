/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Model.MementorCreator.Createur;
import control.ChoixTouche;
import control.Type;
import static control.Type.FANTOME;
import static control.Type.FANTOMECOMPOSITE2;
import static control.Type.FANTOMECOMPOSITE3;
import static control.Type.FANTOMECOMPOSITE4;
import static control.Type.FRUIT;

import static control.Type.PACMAN;
import static control.Type.SUPERPACMAN;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author admin
 */
public class FantomeComposite extends Fantome {

    public ChoixTouche ct;
    public List<Fantome> groupeFantome = new ArrayList<>();
    public int taille;

    public FantomeComposite() {

    }

    public FantomeComposite(Fantome f) {
        super(f);

    }

    @Override
    public List<Fantome> getGroupeFantome() {
        return groupeFantome;
    }

    public FantomeComposite(ChoixTouche ct) {
        this.ct = ct;
    }

    public FantomeComposite(FantomeComposite f) {
        this(f.ct);
    }

    @Override
    public int getTaille() {

        int n = 0;
        for (int i = 0; i < nombre(); ++i) {
            n += getListePM().get(i).getTaille();
        }
        taille = n;

        return taille;
    }

    @Override
    public Type getType() {
        Type type = null;
        switch (getTaille()) {
            case 2: {
                type = FANTOMECOMPOSITE2;
                break;
            }
            case 3: {
                type = FANTOMECOMPOSITE3;
                break;
            }
            case 4: {
                type = FANTOMECOMPOSITE4;
                break;
            }

        }
        return type;
    }

    @Override
    public int nombre() {
        return groupeFantome.size();
    }

    public void ajoutFantome(Fantome fantome) {
        groupeFantome.add(fantome);
    }

    @Override
    public List<Fantome> getListePM() {
        return groupeFantome;
    }

    public boolean FantomeDansGroupe(Fantome fantome) {
        return groupeFantome.contains(fantome);
    }

    @Override
    public PersonnageMobile getPM(int i) {
        return groupeFantome.get(i);
    }

    @Override
    public void bouger(int x, int y, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {
        int posX = getCurrentX() + x;
        int posY = getCurrentY() + y;
        Type type1 = labyrinthe.getPlateau()[posX][posY].getType();
        switch (type1) {
            case MUR:
                rencontreMur(labyrinthe, x, y);
                break;
            case PACGOMME:
                rencontrePacGomme(labyrinthe, x, y);
                break;
            case FRUIT:
                rencontreFruit(labyrinthe, x, y);
                break;
            case CHAMPIGNON:
                rencontreChampignon(labyrinthe, x, y, Createur, gardien);
                break;
            case VIDE:
                rencontreVide(labyrinthe, x, y);
                break;
            case FANTOME:
                rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case FANTOMECOMPOSITE2:
                rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case FANTOMECOMPOSITE3:
                rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case FANTOMECOMPOSITE4:
                rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case PACMAN:
                rencontrePacman(labyrinthe, x, y, Createur, gardien);
                break;
            case SUPERPACMAN:
                rencontreSuperPacman(labyrinthe, x, y);
                break;
            default:
                break;
        }

    }

    @Override
    public void avancer(Labyrinthe labyrinthe, int x, int y) {
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(this);
        labyrinthe.getPlateau()[getCurrentX() + x][getCurrentY() + y].getListeElement().add(this);
        mettreAJourPos(x, y);
    }

    @Override
    public void rencontreFantome(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {

        avancer(labyrinthe, x, y);
        fusion(labyrinthe, x, y);

    }

    @Override
    public void rencontrePacman(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {
        avancer(labyrinthe, x, y);
        if (gardien.getMementos().size() > getTaille()) {
            for (int i = 0; i < getTaille(); ++i) {
                gardien.getMementos().remove(gardien.getMementos().size() - 1);
            }
            Createur.restore(gardien.get(gardien.getMementos().size() - 1));
            labyrinthe.niveau1 = Createur.getLabyrinthe().niveau1;
            labyrinthe.pacman = Createur.getLabyrinthe().pacman;
            labyrinthe.listeFantome = Createur.getLabyrinthe().listeFantome;
            labyrinthe.pacGomme = Createur.getLabyrinthe().pacGomme;
            labyrinthe.fruit = Createur.getLabyrinthe().fruit;
            labyrinthe.champignon = Createur.getLabyrinthe().champignon;
            labyrinthe.nombreFantome = Createur.getLabyrinthe().nombreFantome;
            labyrinthe.nbrPacGomme = Createur.getLabyrinthe().nbrPacGomme;
            labyrinthe.finJeu = Createur.getLabyrinthe().finJeu;
            labyrinthe.plateau = Createur.getLabyrinthe().plateau;
        } else {
            Platform.exit();
        }
    }

    @Override
    public void rencontreSuperPacman(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);
        mourir(labyrinthe);
        playSound("sounds/EatingGhost.wav");
    }

    @Override
    public void rencontreFruit(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);

    }

    @Override
    public void rencontrePacGomme(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);

    }

    @Override
    public void rencontreChampignon(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {
        avancer(labyrinthe, x, y);

    }

    @Override
    public void rencontreVide(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);

    }

    @Override
    public void rencontreMur(Labyrinthe labyrinthe, int x, int y) {
        for (int i = 0; i < labyrinthe.getListeFantome().size(); ++i) {
            if (this.equals(labyrinthe.getListeFantome().get(i))) {
                setCt(null);
            }
        }

    }

    @Override
    public void mourir(Labyrinthe labyrinthe) {
        for (int j = 0; j < groupeFantome.size(); ++j) {
            if (groupeFantome.get(j).getType() == FANTOME) {
                groupeFantome.get(j).mourir(labyrinthe);
                labyrinthe.getListeFantome().add(groupeFantome.get(j));
            } else if (groupeFantome.get(j).getType() == FANTOMECOMPOSITE2 || groupeFantome.get(j).getType() == FANTOMECOMPOSITE3 || groupeFantome.get(j).getType() == FANTOMECOMPOSITE4) {
                for (int i = 0; i < groupeFantome.get(j).getListePM().size(); ++i) {;
                    groupeFantome.get(j).getListePM().get(i).mourir(labyrinthe);
                    labyrinthe.getListeFantome().add(groupeFantome.get(j).getListePM().get(i));

                }
            }
        }
        labyrinthe.getPacman().addPointDePacman(20 * taille);
        groupeFantome.clear();
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(this);
        labyrinthe.getListeFantome().remove(this);

    }

    @Override
    public void fantomeDansCaseAleatoire(Labyrinthe labyrinthe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupeFantome(List<Fantome> groupeFantome) {
        this.groupeFantome = groupeFantome;
    }

    @Override
    public void setTaille(int taille) {
        this.taille = taille;
    }

    @Override
    public ChoixTouche getCt() {
        return ct;
    }

    @Override
    public void setCt(ChoixTouche ct) {
        this.ct = ct;
    }

}
