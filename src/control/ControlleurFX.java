/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import static control.ChoixTouche.QUITTER;
import static control.Type.FANTOMECOMPOSITE2;
import static control.Type.FANTOMECOMPOSITE3;
import static control.Type.FANTOMECOMPOSITE4;
import static control.Type.MUR;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import vue.VueFX;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.util.Duration;
import Model.MementorCreator.Createur;
import model.Champignon;
import model.Fantome;
import model.Fruit;
import model.Gardien;
import model.Labyrinthe;
import model.NiveauLabyrinthe;
import model.PacGomme;
import model.Pacman;

/**
 *
 * @author SONA
 */
public class ControlleurFX extends Application {

    private static final double nombreAppel = 3; //on peut augmenter le nombre de seconde pour l'appel de l'animation
    private static double secondeF =   50;


    @Override
    public void start(Stage primaryStage) {
        Createur createur = new Createur();
        Gardien gardien = new Gardien();
        NiveauLabyrinthe niveau1 = new NiveauLabyrinthe();
        Labyrinthe lab = new Labyrinthe(new Pacman(false, 0), new PacGomme(), new Fruit(), new Champignon(), 4, new ArrayList<>(), 0, false, niveau1);

        createur.setLabyrinthe(lab);
        gardien.demandeMemento(createur.save());
        for (int i = 0; i < 5; ++i) {
            lab.getPacman().gagneVie(1);
            Labyrinthe labyrinthe = new Labyrinthe(lab);

            createur.setLabyrinthe(labyrinthe);
            gardien.demandeMementoP(createur.save());
        }
        gardien.getMementos().remove(gardien.getMementos().size() - 1);
        createur.restore(gardien.get(gardien.getMementos().size() - 1));

        createur.setLabyrinthe(gardien.getMementos().get(gardien.getMementos().size() - 1).getLabyrinthe());
        VueFX vueFX = new VueFX(primaryStage, createur.getLabyrinthe(), createur, gardien);

        createur.getLabyrinthe().addObserver(vueFX);

    }

    public void joue(Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {

        labyrinthe.setChangedAndNotify(); // appelle la fonction update;
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(nombreAppel * 100),
                ae -> actionPeriodique(labyrinthe, Createur, gardien)));

        timeline.setCycleCount(Animation.INDEFINITE);
        System.out.println("");
        timeline.play();

    }

    
    private void actionPeriodique(Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {

        secondeF -= nombreAppel;
        List<Fantome> tampon = new ArrayList<>();
        if (secondeF == 0) {

            for (int i = 0; i < labyrinthe.getListeFantome().size(); ++i) {

                if (labyrinthe.getListeFantome().get(i).getType() == FANTOMECOMPOSITE2 || labyrinthe.getListeFantome().get(i).getType() == FANTOMECOMPOSITE3 || labyrinthe.getListeFantome().get(i).getType() == FANTOMECOMPOSITE4) {

                    tampon.add(labyrinthe.getListeFantome().get(i));

                    labyrinthe.getPlateau()[labyrinthe.getListeFantome().get(i).getCurrentX()][labyrinthe.getListeFantome().get(i).getCurrentY()].getListeElement().remove(labyrinthe.getListeFantome().get(i));
                    ChoixTouche autreD = ChoixTouche.randomChoixTouche();
                    if (autreD == labyrinthe.getListeFantome().get(i).getCt()) {
                        autreD = ChoixTouche.randomChoixTouche();
                    }
                    labyrinthe.getListeFantome().get(i).getListePM().get(0).setCt(autreD);
                    tampon.add(labyrinthe.getListeFantome().get(i).getListePM().get(0));
                    labyrinthe.getListeFantome().get(i).getListePM().remove(0);

                    ChoixTouche mem = ChoixTouche.randomChoixTouche();
                    if (mem != labyrinthe.getListeFantome().get(i).getCt()) {
                        mem = ChoixTouche.randomChoixTouche();
                    }

                    labyrinthe.getListeFantome().get(i).getListePM().get(0).setCt(mem);
                    tampon.add(labyrinthe.getListeFantome().get(i).getListePM().get(0));
                    labyrinthe.getListeFantome().get(i).getListePM().remove(0);

                }
            }
            for (int i = 0; i < tampon.size(); ++i) {

                if (labyrinthe.getListeFantome().contains(tampon.get(i))) {

                    labyrinthe.getListeFantome().remove(tampon.get(i));
                } else {

                    labyrinthe.getListeFantome().add(tampon.get(i));
                }

            }
            tampon.clear();
            secondeF =  50;
        }

        if (labyrinthe.getPacman().getInvincibilite() == true) {
            labyrinthe.getPacman().retirerTempsInvicibilite(nombreAppel);
            if(labyrinthe.getPacman().getTempsInvincibilite()==0)
            {
               labyrinthe.getPacman().setInvincibilite(false);
            }
        }
        

        for (int i = 0; i < labyrinthe.getListeFantome().size(); ++i) {

            if (labyrinthe.getListeFantome().get(i).getCt() == null || labyrinthe.getListeFantome().get(i).getCt() == QUITTER) {

                ChoixTouche tmp = ChoixTouche.randomChoixTouche();

                int posX = labyrinthe.getListeFantome().get(i).getCurrentX() + xReturn(tmp);

                int posY = labyrinthe.getListeFantome().get(i).getCurrentY() + yReturn(tmp);

                Type type1 = labyrinthe.getPlateau()[posX][posY].getType();

                while (tmp == QUITTER || type1 == MUR) {
                    tmp = ChoixTouche.randomChoixTouche();
                    posX = labyrinthe.getListeFantome().get(i).getCurrentX() + xReturn(tmp);
                    posY = labyrinthe.getListeFantome().get(i).getCurrentY() + yReturn(tmp);
                    type1 = labyrinthe.getPlateau()[posX][posY].getType();

                }

                labyrinthe.getListeFantome().get(i).setCt(tmp);

            }

            labyrinthe.move(labyrinthe.getListeFantome().get(i), xReturn(labyrinthe.getListeFantome().get(i).getCt()), yReturn(labyrinthe.getListeFantome().get(i).getCt()), labyrinthe, Createur, gardien);

        }
    }

    public int xReturn(ChoixTouche ct) {
        int x = 0;
        switch (ct) {
            case ENHAUT:
                x = -1;

                break;
            case ENBAS:
                x = 1;

                break;
            case ADROITE:
                x = 0;

                break;
            case AGAUCHE:
                x = 0;

                break;

        }
        return x;
    }

    public int yReturn(ChoixTouche ct) {
        int y = 0;
        switch (ct) {

            case ENHAUT:

                y = 0;
                break;
            case ENBAS:

                y = 0;
                break;
            case ADROITE:

                y = 1;
                break;
            case AGAUCHE:

                y = -1;
                break;

        }
        return y;
    }

    public static void main(String[] args) {

        launch(args);

    }

}
