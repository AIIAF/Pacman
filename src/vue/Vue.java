/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import control.ChoixTouche;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import model.Labyrinthe;

/**
 *
 *
 */
public class Vue implements Observer {

    private final Scanner scanner = new Scanner(System.in);
    private static final Vue VUE = new Vue();

    public static Vue getInstance() {
        return VUE;
    }

    private Vue() {
    }

    public static void afficherMatrice(Labyrinthe lab) {
        System.out.println("Nombre de vie restant à Pacman : " + lab.viePacman());
        System.out.println("Nombre de point de Pacman : " + lab.scorePacman());
        System.out.println("Nombre de fantome dans le labyrinthe : " + lab.getNbrFantome());
        System.out.println("Nombre de PacGomme restant : " + lab.getNbrPacGomme());
        System.out.println("");
        for (int i = 0; i < lab.getX(); ++i) {
            for (int j = 0; j < lab.getY(); ++j) {
                System.out.print(lab.stringCase(lab, i, j) + "  ");
            }
            System.out.println("");
        }
        if (lab.getNbrFantome() == 0) {
            System.out.println("Vous avez gagné la partie ! ");
        } else if (lab.viePacman() == 0) {
            System.out.println("Vous avez perdu la partie !");
        }

    }

    private int indiceSaisi() {
        return lettreSaisie("Indice : ");

    }

    private int nombreSaisi(String msg) {
        System.out.print(msg);
        int entree = 0;
        boolean entreeOK = false;
        while (!entreeOK) {
            try {
                entree = Integer.parseInt(scanner.next());
                entreeOK = true;
            } catch (NumberFormatException e) {
                System.out.print("Entrée incorrecte - " + msg);
            }
        }
        return entree;
    }

    private static int lettreSaisie(String msg) {
        System.out.print(msg);
        int entree = 0;
        boolean entreeOK = false;
        while (!entreeOK) {
            try {
                Scanner keyboard = new Scanner(System.in);
                String maChaine;
                char monCar = ' ';
                maChaine = keyboard.nextLine();
                if (maChaine.length() == 1) {
                    monCar = maChaine.charAt(0);
                }
                switch (monCar) {
                    case 'd': {
                        entree = 1;
                        break;
                    }
                    case 's': {
                        entree = 3;
                        break;
                    }
                    case 'z': {
                        entree = 4;
                        break;

                    }
                    case 'q': {
                        entree = 2;
                        break;
                    }
                    case 'x': {
                        entree = 5;
                        break;
                    }
                    default:
                        break;
                }
                entreeOK = true;
            } catch (NumberFormatException e) {
                System.out.print("Entrée incorrecte - " + msg);
            }
        }
        return entree;
    }

    public static ChoixTouche saisirChoixTouche(Labyrinthe lab) {
        int choixInt = trySaisirChoixTouche();
        while (choixInt == -1) {
            System.out.println("Mauvais choix\n");
            afficherMatrice(lab);
            choixInt = trySaisirChoixTouche();
        }
        return ChoixTouche.getByInt(choixInt);
    }

    private static int trySaisirChoixTouche() {
        affMenu();
        return rangeValidationSaisie("Votre touche", ChoixTouche.values().length);
    }

    private static int rangeValidationSaisie(String libelle, int nMax) {
        int choixMembre = lettreSaisie(libelle + " ? :");
        if (choixMembre < 1 || choixMembre > nMax) {
            return -1;
        }
        return choixMembre;
    }

    private static void affMenu() {
        StringBuilder sb = new StringBuilder("\n");
        for (ChoixTouche choix : ChoixTouche.values()) {
            sb.append(choix.numero());
            sb.append(" : ");
            sb.append(choix.toString());
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Override
    public void update(Observable obs, Object obj) {
        Labyrinthe labyrinthe = (Labyrinthe) obs;
        afficherMatrice(labyrinthe);
    }

}
