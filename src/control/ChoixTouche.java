/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.Random;

/**
 *
 * @author SONA
 */
public enum ChoixTouche {

    ADROITE("Appuyer sur 'd' pour aller à droite "), //1
    AGAUCHE("Appuyer sur 'q' pour aller à gauche "), //2
    ENBAS("Appuyer sur 's' pour aller en bas "), //3
    ENHAUT("Appuyer sur 'z' pour aller en haut "), //4
    QUITTER("Appuyer sur 'x' pour quitter "); //5 

    private static final Random rand = new Random();
    private int x = 0;
    private int y = 0;
    private final String msg;

    public int numero() {
        return ordinal() + 1;
    }

    ChoixTouche(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

    public static ChoixTouche getByInt(int choix) throws IndexOutOfBoundsException {
        if (choix < 1 || choix > ChoixTouche.values().length) {
            throw new IndexOutOfBoundsException();
        }
        return ChoixTouche.values()[choix - 1];
    }

    public static ChoixTouche randomChoixTouche() {
        return ChoixTouche.values()[rand.nextInt(ChoixTouche.values().length)];
    }

    public void chgtEnCoord(ChoixTouche ct) {
        switch (ct) {
            case ENHAUT:
                x = -1;
                y = 0;
                break;
            case ENBAS:
                x = 1;
                y = 0;
                break;
            case ADROITE:
                x = 0;
                y = 1;
                break;
            case AGAUCHE:
                x = 0;
                y = -1;
                break;

        }
    }

}
