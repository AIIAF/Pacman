/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Model.MementorCreator.Createur;
import static control.ChoixTouche.QUITTER;
import control.Type;
import static control.Type.FANTOME;
import static control.Type.MUR;
import static control.Type.VIDE;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author SONA ET HAYTHEM
 */
public final class Labyrinthe extends Observable {

//0 : rien   ' ' (case centrale)
//1 : mur  xxxxxxxxx
//2 : pacman e 
//3 : fantôme A 
//4 : gomme .
//5 : fruit à
//6 : champigno î
    public Pacman pacman;
    public List<Fantome> listeFantome = new ArrayList<>();
    public PacGomme pacGomme;
    public Fruit fruit;
    public Champignon champignon;
    public int nombreFantome;
    public int nbrPacGomme;
    public boolean finJeu;
    public Case[][] plateau;
    public NiveauLabyrinthe niveau1;

    public Labyrinthe() {

    }

    public List<Fantome> listeFantome() {
        return listeFantome;
    }

    public void fantomeDansList() {
        for (int i = 0; i < nombreFantome; ++i) {
            listeFantome.add(new FantomeSimple(QUITTER));
        }

    }

    public Labyrinthe(Pacman pacman, PacGomme pacGomme, Fruit fruit, Champignon champignon, int nombreFantome, List<Fantome> listeFantome, int nbrPacGomme, boolean finJeu, NiveauLabyrinthe niveau1) {

        this.pacman = pacman;
        this.pacGomme = pacGomme;
        this.fruit = fruit;
        this.champignon = champignon;
        this.nombreFantome = nombreFantome;
        this.listeFantome = listeFantome;
        this.nbrPacGomme = nbrPacGomme;
        this.finJeu = finJeu;
        this.niveau1 = niveau1;
        this.plateau = this.initData(this);
    }

    public Labyrinthe(Labyrinthe lab) {

        this.listeFantome = new ArrayList<>();
        this.nbrPacGomme = lab.nbrPacGomme;
        this.finJeu = lab.finJeu;
        this.plateau = this.copiePlateau(lab.plateau, lab);

    }

    public Case[][] copiePlateau(Case[][] plalab, Labyrinthe lab) {

        int X = lab.getX();
        int Y = lab.getY();

        Case[][] plateaub = new Case[X][Y];
        for (int i = 0; i < X; ++i) {

            for (int j = 0; j < Y; ++j) {

                switch (plalab[i][j].getType()) {
                    case VIDE:
                        Vide vide0 = new Vide(i, j);
                        plateaub[i][j] = vide0;
                        break;
                    case MUR:
                        plateaub[i][j] = new Mur(i, j);

                        break;
                    case PACMAN:
                        Vide vide2 = new Vide(i, j);
                        this.pacman = new Pacman(lab.getPacman());
                        ;
                        vide2.getListeElement().add(this.pacman);
                        plateaub[i][j] = vide2;
                        break;
                    case SUPERPACMAN:
                        Vide vide21 = new Vide(i, j);
                        this.pacman = new Pacman(lab.getPacman());
                        this.pacman.setInvincibilite(true);
                        vide21.getListeElement().add(this.pacman);
                        plateaub[i][j] = vide21;
                        break;
                    case FANTOME:
                        for (Fantome f : lab.listeFantome) {
                            if (f.getCurrentX() == i && f.getCurrentY() == j) {
                                Vide vide3 = new Vide(i, j);
                                FantomeSimple fs = new FantomeSimple(f);
                                vide3.getListeElement().add(fs);
                                this.listeFantome.add(fs);
                                plateaub[i][j] = vide3;

                            }

                        }

                        break;
                    case FANTOMECOMPOSITE2:

                        for (Fantome f : lab.listeFantome) {
                            if (f.getCurrentX() == i && f.getCurrentY() == j) {

                                FantomeComposite f2 = new FantomeComposite(f);
                                FantomeSimple fs1 = new FantomeSimple(f.getGroupeFantome().get(0));
                                FantomeSimple fs2 = new FantomeSimple(f.getGroupeFantome().get(1));
                                f2.getGroupeFantome().add(fs1);
                                f2.getGroupeFantome().add(fs2);
                                Vide vide3 = new Vide(i, j);
                                vide3.getListeElement().add(f2);
                                this.listeFantome.add(f2);
                                plateaub[i][j] = vide3;

                            }

                        }

                        break;
                    case FANTOMECOMPOSITE3:

                        for (Fantome f : lab.listeFantome) {
                            if (f.getCurrentX() == i && f.getCurrentY() == j) {
                                FantomeSimple f31 = new FantomeSimple();
                                FantomeSimple f32 = new FantomeSimple();
                                FantomeSimple f33 = new FantomeSimple();

                                FantomeComposite f34 = new FantomeComposite();
                                FantomeComposite f3 = new FantomeComposite();
                                for (int k = 0; k < f.getGroupeFantome().size(); ++k) {
                                    if (f.getGroupeFantome().get(k).getType() == FANTOME) {
                                        f31 = new FantomeSimple(f.getGroupeFantome().get(k));
                                    } else {
                                        f34 = new FantomeComposite(f.getGroupeFantome().get(k));
                                        f32 = new FantomeSimple(f.getGroupeFantome().get(k).getGroupeFantome().get(0));
                                        f33 = new FantomeSimple(f.getGroupeFantome().get(k).getGroupeFantome().get(1));
                                    }
                                }
                                f3 = new FantomeComposite(f);
                                f3.initialiserPos(i, j);

                                f34.getGroupeFantome().add(f32);
                                f34.getGroupeFantome().add(f33);

                                f3.getGroupeFantome().add(f31);
                                f3.getGroupeFantome().add(f34);
                                f3.initialiserPos(i, j);
                                Vide vide33 = new Vide(i, j);
                                vide33.getListeElement().add(f3);

                                this.listeFantome.add(f3);
                                plateaub[i][j] = vide33;

                            }

                        }

                        break;
                    case FANTOMECOMPOSITE4:

                        for (Fantome f : lab.listeFantome) {
                            if (f.getCurrentX() == i && f.getCurrentY() == j) {
                                Vide vide34 = new Vide(i, j);
                                FantomeSimple f41 = new FantomeSimple();
                                FantomeSimple f42 = new FantomeSimple();
                                FantomeSimple f43 = new FantomeSimple();
                                FantomeSimple f44 = new FantomeSimple();

                                FantomeComposite f45 = new FantomeComposite();
                                FantomeComposite f46 = new FantomeComposite();
                                FantomeComposite f4 = new FantomeComposite();
                                for (int l = 0; l < f.getGroupeFantome().size(); ++l) {
                                    if (f.getGroupeFantome().get(l).getType() == FANTOME) {
                                        f41 = new FantomeSimple(f.getGroupeFantome().get(l));
                                    } else {
                                        f46 = new FantomeComposite(f.getGroupeFantome().get(l));
                                        for (int m = 0; m < f.getGroupeFantome().get(l).getGroupeFantome().size(); ++m) {
                                            if (f.getGroupeFantome().get(l).getGroupeFantome().get(m).getType() == FANTOME) {
                                                f42 = new FantomeSimple(f.getGroupeFantome().get(l).getGroupeFantome().get(m));

                                            } else {
                                                f45 = new FantomeComposite(f.getGroupeFantome().get(l).getGroupeFantome().get(m));
                                                f43 = new FantomeSimple(f.getGroupeFantome().get(l).getGroupeFantome().get(m).getGroupeFantome().get(0));
                                                f44 = new FantomeSimple(f.getGroupeFantome().get(l).getGroupeFantome().get(m).getGroupeFantome().get(1));
                                            }

                                        }
                                    }

                                }
                                f4 = new FantomeComposite(f);
                                f45.getGroupeFantome().add(f41);
                                f45.getGroupeFantome().add(f42);

                                f46.getGroupeFantome().add(f43);
                                f46.getGroupeFantome().add(f44);

                                f4.getGroupeFantome().add(f45);
                                f4.getGroupeFantome().add(f46);

                                vide34.getListeElement().add(f4);
                                f4.initialiserPos(i, j);
                                this.listeFantome.add(f4);
                                plateaub[i][j] = vide34;

                            }

                        }

                        break;
                    case PACGOMME:
                        Vide vide4 = new Vide(i, j); // tu crées une case jouable
                        vide4.getListeElement().add(new PacGomme(new PacGomme())); // tu mets la gomme dans cette case jouable
                        plateaub[i][j] = vide4; //  tu mets la case vide dans tableau
                        break;
                    case FRUIT:
                        Vide vide5 = new Vide(i, j);
                        vide5.getListeElement().add(new Fruit(new Fruit()));
                        plateaub[i][j] = vide5;
                        break;
                    case CHAMPIGNON:
                        Vide vide6 = new Vide(i, j);
                        vide6.getListeElement().add(new Champignon(new Champignon()));
                        plateaub[i][j] = vide6;
                        break;
                }
            }

        }
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (plalab[i][j].getType() != MUR) {
                    if (plalab[i][j].getListeElement().size() > 1) {
                        switch (plalab[i][j].getListeElement().get(0).getType()) {

                            case PACGOMME:

                                plateaub[i][j].getListeElement().add(0, new PacGomme());

                                break;
                            case FRUIT:
                                plateaub[i][j].getListeElement().add(0, new Fruit());
                                break;
                            case CHAMPIGNON:
                                plateaub[i][j].getListeElement().add(0, new Champignon());
                                break;

                            default:
                                break;
                        }

                    }
                }
            }
        }

        return plateaub;
    }

    public Case[][] initData(Labyrinthe labyrinthe) {
        int X = niveau1.getNiveau().length;
        int Y = niveau1.getNiveau()[0].length;
        plateau = new Case[X][Y];
        for (int i = 0; i < X; ++i) {
            for (int j = 0; j < Y; ++j) {
                switch (niveau1.getNiveau()[i][j]) {
                    case 0:
                        Vide vide0 = new Vide(i, j);
                        plateau[i][j] = vide0;
                        break;
                    case 1:
                        plateau[i][j] = new Mur(i, j);

                        break;
                    case 2:
                        Vide vide2 = new Vide(i, j);
                        vide2.getListeElement().add(pacman);
                        plateau[i][j] = vide2;
                        pacman.initialiserPos(i, j);
//                        pacman.gagneVie(1);
                        break;
                    case 4:
                        Vide vide4 = new Vide(i, j); // tu crées une case jouable
                        vide4.getListeElement().add(pacGomme); // tu mets la gomme dans cette case jouable
                        plateau[i][j] = vide4; //  tu mets la case vide dans tableau
                        this.nbrPacGomme++;
                        break;
                    case 5:
                        Vide vide5 = new Vide(i, j);
                        vide5.getListeElement().add(fruit);
                        plateau[i][j] = vide5;
                        break;
                    case 6:
                        Vide vide6 = new Vide(i, j);
                        vide6.getListeElement().add(champignon);
                        plateau[i][j] = vide6;
                        break;
                }
            }

        }

        genereFantome(labyrinthe);
        return plateau;

    }

    private void genereFantome(Labyrinthe labyrinthe) {
        fantomeDansList();
        for (int i = 0; i < nombreFantome; ++i) {
            listeFantome.get(i).fantomeDansCaseAleatoire(labyrinthe);
        }

    }

    public Case[][] getPlateau() {
        return plateau;
    }

    public String stringCase(Labyrinthe labyrinthe, int i, int j) {
        String res = " ";
        Type type = plateau[i][j].getType();
        if (type != null) {
            switch (type) {
                case MUR:
                    res = "x";
                    break;
                case VIDE:
                    res = " ";
                    break;
                case FRUIT:
                    res = "à";
                    break;
                case PACMAN:
                    res = "e";
                    break;
                case SUPERPACMAN:
                    res = "G";
                    break;
                case PACGOMME:
                    res = ".";
                    break;
                case CHAMPIGNON:
                    res = "ô";
                    break;
                case FANTOME:
                    res = "A";
                    break;
            }
        }

        return res;
    }

    public void setChangedAndNotify(Object obj) {
        setChanged();
        notifyObservers(obj);
    }

    public void setChangedAndNotify() {
        setChanged();
        notifyObservers(this);

    }

    public int viePacman() {
        return pacman.getNbrVie();
    }

    public int scorePacman() {
        return pacman.getPointDePacman();
    }

    public void move(PersonnageMobile pm, int x, int y, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {

        pm.bouger(x, y, labyrinthe, Createur, gardien);
        setChangedAndNotify();

    }

    public Pacman getPacman() {
        return pacman;
    }

    public boolean getFinJeu() {
        return finJeu;
    }

    public int getNbrFantome() {
        return listeFantome.size();
    }

    public int getNbrPacGomme() {
        return nbrPacGomme;
    }

    public void setNbrPacGomme(int i) {
        nbrPacGomme = i;
    }

    public int getX() {
        return getPlateau().length;
    }

    public int getY() {
        return getPlateau()[0].length;
    }

    public int addNbrPacGomme(int i) {
        nbrPacGomme += i;
        return nbrPacGomme;
    }

    public List<Fantome> getListeFantome() {
        return listeFantome;
    }

    public PacGomme getPacGomme() {
        return pacGomme;
    }

    public Element getFruit() {

        return fruit;
    }

    public Element getChampignon() {
        return champignon;
    }

    public void playSound(String song) {
        final String playsound = song;
        Media sound = new Media(new File(playsound).toURI().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
        mediaPlayer2.play();
    }

}
