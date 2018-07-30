/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Model.MementorCreator.Createur;
import control.ChoixTouche;
import control.Type;
import static control.Type.FANTOMECOMPOSITE2;
import static control.Type.FANTOMECOMPOSITE3;
import static control.Type.FANTOMECOMPOSITE4;
import static control.Type.PACMAN;
import static control.Type.SUPERPACMAN;
import javafx.application.Platform;

/**
 *
 * @author SONA
 */
public class Pacman extends PersonnageMobile {

    private boolean invincibilite;
    private int pointDePacman;
    private int tempsInvincibilite;

    public Pacman(Pacman p) {
        this.setCurrentX(p.getCurrentX());
        this.setCurrentY(p.getCurrentY());
        this.setInitialeX(p.getInitialeX());
        this.setInitialeY(p.getInitialeY());
        this.setNbrVie(p.getNbrVie());
        this.setInvincibilite(p.getInvincibilite());
        this.addPointDePacman(p.getPointDePacman());
        this.setTempsInvincibilite(p.getTempsInvincibilite());
    }

    /**
     * @return the tempsInvincibilite
     */
    public int getTempsInvincibilite() {
        return tempsInvincibilite;
    }

    /**
     * @param aTempsInvincibilite the tempsInvincibilite to set
     */
    public void setTempsInvincibilite(int aTempsInvincibilite) {
        tempsInvincibilite = aTempsInvincibilite;
    }

    public int retirerTempsInvicibilite(double i) {
        this.tempsInvincibilite -= i;
        return tempsInvincibilite;
    }

    public Pacman(boolean invincibilite, int pointDePacman) {
        this.invincibilite = invincibilite;
        this.pointDePacman = pointDePacman;
    }

    public Pacman() {

    }

    public boolean getInvincibilite() {
        return invincibilite;
    }

    public int getPointDePacman() {
        return pointDePacman;
    }

    public void addPointDePacman(int nbre) {
        pointDePacman += nbre;
    }

    public void setInvincibilite(boolean b) {
        this.invincibilite = b;
    }

    public void dance(ChoixTouche ct, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {
        switch (ct) {
            case ENHAUT:
                bouger(-1, 0, labyrinthe, Createur, gardien);

                break;
            case ENBAS:
                bouger(1, 0, labyrinthe, Createur, gardien);
                break;
            case AGAUCHE:
                bouger(0, -1, labyrinthe, Createur, gardien);
                break;
            case ADROITE:
                bouger(0, 1, labyrinthe, Createur, gardien);
                break;
        }
        labyrinthe.setChangedAndNotify();
    }

    @Override
    public void bouger(int x, int y, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {
        Type type = labyrinthe.getPlateau()[getCurrentX() + x][getCurrentY() + y].getType();
        switch (type) {
            case MUR:
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
                this.rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case FANTOMECOMPOSITE2:
                this.rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case FANTOMECOMPOSITE3:
                this.rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            case FANTOMECOMPOSITE4:
                this.rencontreFantome(labyrinthe, x, y, Createur, gardien);
                break;
            default:
                break;
        }

    }

    @Override
    public void rencontrePacGomme(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);
        manger(labyrinthe, x, y);
        addPointDePacman(1);
        labyrinthe.setNbrPacGomme(labyrinthe.getNbrPacGomme() - 1);

    }
 
    @Override
    public void rencontreFruit(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);
        manger(labyrinthe, x, y);
//        this.setInvincibilite(true);
        this.setTempsInvincibilite(50);
      

    }

    @Override
    public void rencontreChampignon(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {
        avancer(labyrinthe, x, y);
        manger(labyrinthe, x, y);
        labyrinthe.playSound("sounds/Extra_Live.wav");
        sauverEtatJeu(labyrinthe, Createur, gardien);

    }

    public void sauverEtatJeu(Labyrinthe lab, Createur Createur, Gardien gardien) {
        Labyrinthe labyrinthe = new Labyrinthe(lab);
        Createur.setLabyrinthe(labyrinthe);
        gardien.demandeMementoP(Createur.save());
        gagneVie(1);

    }

    @Override
    public void rencontreVide(Labyrinthe labyrinthe, int x, int y) {
        avancer(labyrinthe, x, y);
    }

    @Override
    public void rencontreFantome(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {
        avancer(labyrinthe, x, y);
        if (getInvincibilite() == false) {
            labyrinthe.playSound("sounds/death.wav");
            for (int i = 0; i < labyrinthe.getNbrFantome(); ++i) {
                if (labyrinthe.getListeFantome().get(i).getCurrentX() == getCurrentX() && labyrinthe.getListeFantome().get(i).getCurrentY() == getCurrentY()) {
                    Type type = labyrinthe.getListeFantome().get(i).getType();
                    if (null != type) {
                        switch (type) {
                            case FANTOME:
                                if (gardien.getMementos().size() == 1) {
                                    Platform.exit();
                                } else {
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                }
                                break;
                            case FANTOMECOMPOSITE2:
                                if (gardien.getMementos().size() <= 2) {
                                    Platform.exit();
                                } else {
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                }
                                break;
                            case FANTOMECOMPOSITE3:
                                if (gardien.getMementos().size() <= 3) {
                                    Platform.exit();
                                } else {
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                }
                                break;
                            case FANTOMECOMPOSITE4:
                                if (gardien.getMementos().size() <= 4) {
                                    Platform.exit();
                                } else {
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                    gardien.getMementos().remove(gardien.getMementos().size() - 1);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
            Createur.restore(gardien.get(gardien.getMementos().size() - 1));
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
            for (int i = 0; i < labyrinthe.getListeFantome().size(); i++) {
                if (labyrinthe.getListeFantome().get(i).getCurrentX() == getCurrentX() && labyrinthe.getListeFantome().get(i).getCurrentY() == getCurrentY()) {
                    labyrinthe.getListeFantome().get(i).setCurrentX(labyrinthe.getListeFantome().get(i).getInitialeX());
                    labyrinthe.getListeFantome().get(i).setCurrentY(labyrinthe.getListeFantome().get(i).getInitialeY());
                    labyrinthe.getPlateau()[this.getCurrentX()][this.getCurrentY()].getListeElement().remove(labyrinthe.getListeFantome().get(i));
                    this.addPointDePacman(20 * labyrinthe.getListeFantome().get(i).getTaille());

                }

            }

            if (labyrinthe.getPlateau()[this.getCurrentX()][this.getCurrentY()].getListeElement().size() > 1) {
                manger(labyrinthe, getCurrentX(), getCurrentY());
            }
            labyrinthe.playSound("sounds/EatingGhost.wav");

        }

    }

    @Override
    public void rencontrePacman(Labyrinthe labyrinthe, int x, int y, Createur Createur, Gardien gardien) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rencontreSuperPacman(Labyrinthe labyrinthe, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rencontreMur(Labyrinthe labyrinthe, int x, int y) {
        System.out.println("rencontre un mur");
    }

    @Override
    public Type getType() {
        if (invincibilite) {
            return SUPERPACMAN;
        } else {
            return PACMAN;
        }
    }

    @Override
    public int nombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonnageMobile getPM(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
