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
import static control.Type.VIDE;
import java.util.List;
import java.util.Random;
import javafx.application.Platform;

/**
 *
 * @author 0201sosay
 */
public class FantomeSimple extends Fantome {

    public int taille = 1;
    public ChoixTouche ct;

    public FantomeSimple(ChoixTouche ct) {
        this.ct = ct;
        this.taille = 1;
    }

    public FantomeSimple() {

    }

    public FantomeSimple(Fantome f) {
        super(f);

    }

    @Override
    public void fantomeDansCaseAleatoire(Labyrinthe labyrinthe) {

        Random randomInt = new Random();
        int pX = randomInt.nextInt(labyrinthe.getPlateau().length);
        int pY = randomInt.nextInt(labyrinthe.getPlateau()[0].length);
        while (labyrinthe.getPlateau()[pX][pY].getType() != VIDE) {
            pX = randomInt.nextInt(labyrinthe.getPlateau().length);
            pY = randomInt.nextInt(labyrinthe.getPlateau()[0].length);
        }
        labyrinthe.niveau1.tab[pX][pY] = 3;
        Vide vide3 = new Vide(pX, pY);
        vide3.getListeElement().add(this);
        labyrinthe.getPlateau()[pX][pY] = vide3;
        initialiserPos(pX, pY);
    }

    @Override
    public int getTaille() {
        return taille;
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
    public void rencontreFantome(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {
        avancer(labyrinthe, x, y);
        fusion(labyrinthe, x, y);
    }

    @Override
    public void rencontrePacman(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {

        avancer(labyrinthe, x, y);
        if (gardien.getMementos().size() > 1) {

            gardien.getMementos().remove(gardien.getMementos().size() - 1);// on retire le dernier élèment
            Createur.restore(gardien.get(gardien.getMementos().size() - 1));

            System.out.println("");

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
    public void mourir(Labyrinthe labyrinthe) {
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(this);
        setCurrentX(getInitialeX());
        setCurrentY(getInitialeY());
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().add(this);
    }

    @Override
    public void rencontreFruit(Labyrinthe labyrinthe, int x, int y
    ) {
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
    public Type getType() {
        return FANTOME;

    }

    @Override
    public int nombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void ajoutPM(PersonnageMobile pm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonnageMobile getPM(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Fantome> getListePM() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the ct
     */
    @Override
    public ChoixTouche getCt() {
        return ct;
    }

    /**
     * @param ct the ct to set
     */
    @Override
    public void setCt(ChoixTouche ct) {
        this.ct = ct;
    }

    @Override
    public List<Fantome> getGroupeFantome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGroupeFantome(List<Fantome> groupeFantome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTaille(int taille) {
        this.taille = taille;
    }


}
