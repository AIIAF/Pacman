/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Model.MementorCreator.Createur;
import control.Type;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author SONA
 */
public abstract class PersonnageMobile extends Element implements Jouable {

    private int initialeX;
    private int initialeY;
    private int currentX;
    private int currentY;
    private int currentXPrec;
    private int currentYPrec;
    private Type type;
    private int nbrVie = 0;
    private boolean dansGroupe = false;
    private boolean maitreDuGroupe = false;
    private int niveau = 0;
  
    public int getNiveau()
    {
        return niveau;
    }
    public void setNiveau(int b)
    {
        niveau = b;
    }
    public void setMaitreDuGroupe(boolean b) {
        maitreDuGroupe = b;
    }

    public boolean getMaitreDuGroupe() {
        return maitreDuGroupe;
    }

    public void setDansGroupe(boolean b) {
        dansGroupe = b;
    }

    public boolean getDansGroupe() {
        return dansGroupe;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getCurrentXPrec() {
        return currentXPrec;
    }

    public int getCurrentYPrec() {
        return currentYPrec;
    }

    public int getInitialeX() {
        return initialeX;
    }

    public int getInitialeY() {
        return initialeY;
    }

    public int getNbrVie() {
        return nbrVie;
    }

    public void perdreVie(int i) {
        nbrVie -= i;
    }
    public void gagneVie(int i)
    {
        nbrVie+=i;
    }

    public void setNbrVie(int nbrVie) {
        this.nbrVie = nbrVie;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setCurrentXPrec(int currentXPrec) {
        this.currentXPrec = currentXPrec;
    }

    public void setCurrentYPrec(int currentYPrec) {
        this.currentYPrec = currentYPrec;
    }

    public void setInitialeX(int initialeX) {
        this.initialeX = initialeX;
    }

    public void setInitialeY(int initialeY) {
        this.initialeY = initialeY;
    }

    @Override
    public boolean jouable() {
        return true;
    }

    public void initialiserPos(int i, int j) {
        this.initialeX = i;
        this.initialeY = j;
        this.currentX = i;
        this.currentY = j;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void mettreAJourPos(int x, int y) {
        currentXPrec = currentX + x;
        currentYPrec = currentY + y;
        currentX = currentX + x;
        currentY = currentY + y;
    }

    public void tuerPM(Labyrinthe labyrinthe, int x, int y) {
        for (int i = 0; i < labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().size(); ++i) {
            if (labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().get(i).jouable() == true) {
                labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().get(i));                                
            }
        }
    }

    public void manger(Labyrinthe labyrinthe, int x, int y) {
        for (int i = 0; i < labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().size(); ++i) {
            if (labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().get(i).jouable() == false) {
                labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().get(i));
            }
        }
    }
    public void apparaitre(Labyrinthe labyrinthe, int x, int y)
    {
        currentX = initialeX;
        currentY = initialeY;
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().add(this);
    }

    public void avancer(Labyrinthe labyrinthe, int x, int y) {
        labyrinthe.getPlateau()[getCurrentX()][getCurrentY()].getListeElement().remove(this);
        labyrinthe.getPlateau()[getCurrentX()+x][getCurrentY()+y].getListeElement().add(this);
        mettreAJourPos(x, y);
    }
    public void playSound(String song) {
        final String playsound = song;
        Media sound = new Media(new File(playsound).toURI().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(sound);
        mediaPlayer2.play();
    }


    public abstract void bouger(int x, int y, Labyrinthe labyrinthe,Createur Createur,  Gardien gardien);

    public abstract void rencontreFantome(Labyrinthe labyrinthe, int x, int y,Createur Createur,  Gardien gardien);

    public abstract int nombre();
    public abstract PersonnageMobile getPM(int i); 
    
    public abstract void rencontrePacman(Labyrinthe labyrinthe, int x, int y,Createur Createur,Gardien gardien);

    public abstract void rencontreSuperPacman(Labyrinthe labyrinthe, int x, int y);

    public abstract void rencontreFruit(Labyrinthe labyrinthe, int x, int y);

    public abstract void rencontrePacGomme(Labyrinthe labyrinthe, int x, int y);

    public abstract void rencontreChampignon(Labyrinthe labyrinthe, int x, int y,Createur Createur, Gardien gardien);

    public abstract void rencontreVide(Labyrinthe labyrinthe, int x, int y);

    public abstract void rencontreMur(Labyrinthe labyrinthe, int x, int y);
    

 

}
