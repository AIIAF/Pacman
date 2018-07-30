/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import model.Champignon;
import Model.MementorCreator.Createur;
import model.Fruit;
import model.Gardien;
import model.Labyrinthe;
import model.NiveauLabyrinthe;
import model.PacGomme;
import model.Pacman;

import vue.Vue;

/**
 *
 * @author
 */
public class Controlleur {

    public static void main(String[] args) {
        Controlleur Ctrl = new Controlleur();
    }

    public Controlleur() {
        Createur Createur = new Createur();
        Gardien gardien = new Gardien();
        NiveauLabyrinthe niveau1 = new NiveauLabyrinthe();
        Labyrinthe labyrinthe = new Labyrinthe(new Pacman(false, 0), new PacGomme(), new Fruit(), new Champignon(), 4, new ArrayList<>(), 0, false, niveau1);
        labyrinthe.addObserver(Vue.getInstance());
        labyrinthe.initData(labyrinthe);
        Vue.afficherMatrice(labyrinthe);
        ChoixTouche choix = null;
        do {

            try {
                choix = Vue.saisirChoixTouche(labyrinthe);
                System.out.println("");
                System.out.println("---------------------------------------------------------------------");
                System.out.println("");
                labyrinthe.getPacman().dance(choix, labyrinthe, Createur, gardien);

            } catch (Exception e) {
                System.out.println("Mauvais choix! il y a une exception ! " + e);
                Vue.afficherMatrice(labyrinthe);

            }

        } while (choix != ChoixTouche.QUITTER && labyrinthe.getFinJeu() == false);
        System.out.println("Merci et au-revoir !");

    }

}
