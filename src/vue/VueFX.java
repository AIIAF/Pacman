/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import control.ChoixTouche;
import static control.ChoixTouche.ADROITE;
import static control.ChoixTouche.AGAUCHE;
import static control.ChoixTouche.ENBAS;
import static control.ChoixTouche.ENHAUT;
import control.ControlleurFX;
import control.Type;
import static control.Type.FANTOMECOMPOSITE2;
import static control.Type.FANTOMECOMPOSITE3;
import static control.Type.FANTOMECOMPOSITE4;
import static control.Type.MUR;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Model.MementorCreator.Createur;
import model.Gardien;
import model.Labyrinthe;

/**
 *
 * @author SONA
 */
public class VueFX implements Observer {

    private final Canvas canvas = new Canvas(460, 500);

    private static final int TAILLE = 20;
    private final Image pacman_normal = new Image("file:images/pacman_normal.png");
    private final Image pacman_invincible = new Image("file:images/pacman_invincible.png");
    private final Image pacman_dead = new Image("file:images/pacman_dead.gif");
    private final Image pacman = new Image("file:images/pacman.png");
    private final Image enemy_zombie = new Image("file:images/enemy_zombie.png");
    private final Image enemy_normal = new Image("file:images/enemy_normal.png");
    private final Image champignon = new Image("file:images/champignon.png");
    private final Image boule_de_gomme = new Image("file:images/boule-de-gomme.png");
    private final Image fruit = new Image("file:images/fruit.png");
    private final Image mur = new Image("file:images/mur.png");
    private final Image fantomegroupe2 = new Image("file:images/fantomegroupe2.png");
    private final Image fantomegroupe3 = new Image("file:images/fantomegroupe3.png");
    private final Image fantomegroupe4 = new Image("file:images/fantomegroupe4.png");

    private final Label lScore = new Label("Groupe4: Sona");
    private final Label lVie = new Label();
    private final Label lFantome = new Label();
    private final Label lPacGomme = new Label();
    final Image image = new Image("file:images/menu.png");
    final Image play = new Image("file:images/play.png");

    final GraphicsContext gc = canvas.getGraphicsContext2D();
    FlowPane pane = new FlowPane();
    BorderPane root = new BorderPane();

    static boolean activee = false;

    public VueFX(Stage primaryStage, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {
        // Son d'intro
        labyrinthe.playSound("sounds/OpeningSong.wav");

        gc.drawImage(image, 0, 0, 460, 530);
        canvas.setWidth(460);
        canvas.setHeight(500);
        canvas.setFocusTraversable(true);
        canvas.requestFocus();

        root = new BorderPane(canvas);
        FlowPane score = new FlowPane();
        score.setPrefSize(100, 30);
        score.setStyle("-fx-border-color:black;");
        score.setStyle("-fx-font: bold 20px Serif ;");
        score.getChildren().addAll(lScore, lVie, lPacGomme);
        root.setBottom(score);

        Scene scene = new Scene(root, 460, 530);

        addKeyHandler(scene, labyrinthe, Createur, gardien);

        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void addKeyHandler(Scene scene, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {
        scene.setOnKeyPressed(ke
                -> {
            KeyCode keyCode = ke.getCode();
            keyOnPressed(keyCode, labyrinthe, Createur, gardien);
            afficherMatriceFX(labyrinthe);

        });

    }
    ControlleurFX contr = new ControlleurFX();

    private void keyOnPressed(KeyCode keyCode, Labyrinthe labyrinthe, Createur Createur, Gardien gardien) {
        ChoixTouche choixTouche = null;
        switch (keyCode) {
            case UP:
                if (activee == false) {
                    System.out.println("Vous avez quitté le jeu!");
                    System.exit(0);
                }
                choixTouche = ENHAUT;
                labyrinthe.getPacman().dance(choixTouche, labyrinthe, Createur, gardien);
                break;
            case DOWN:
                if (activee == false) {
                    System.out.println("Vous avez quitté le jeu!");
                    System.exit(0);
                }
                choixTouche = ENBAS;
                labyrinthe.getPacman().dance(choixTouche, labyrinthe, Createur, gardien);
                break;
            case RIGHT:
                if (activee == false) {
                    System.out.println("Vous avez quitté le jeu!");
                    System.exit(0);
                }
                choixTouche = ADROITE;
                labyrinthe.getPacman().dance(choixTouche, labyrinthe, Createur, gardien);
                break;
            case LEFT:
                if (activee == false) {
                    System.out.println("Vous avez quitté le jeu!");
                    System.exit(0);
                }
                choixTouche = AGAUCHE;
                labyrinthe.getPacman().dance(choixTouche, labyrinthe, Createur, gardien);
                break;
            case S:
                if (activee == false) {
                    System.out.println("Jouons à Pacman");
                    contr.joue(labyrinthe, Createur, gardien);
                    activee = true;
                }
                break;
            default:
                if (activee == false) {
                    System.out.println("Vous avez quitté le jeu!");
                    System.exit(0);
                }

                break;

        }

        if (labyrinthe.getPacman().getInvincibilite() == false) {
            labyrinthe.playSound("sounds/move2.wav");
        }
        else
        {
            labyrinthe.playSound("sounds/move.wav");
        }

    }

    public void afficherMatriceFX(Labyrinthe labyrinthe) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < labyrinthe.getX(); ++i) {
            for (int j = 0; j < labyrinthe.getY(); ++j) {
                dessinerLabyrinhe(labyrinthe, i, j, gc);
            }
        }
        infoJeu(labyrinthe);
        if (labyrinthe.getNbrPacGomme() == 0) {
            System.out.println("Vous avez gagné la partie");
            Platform.exit();
        } else if (labyrinthe.viePacman() <= 0) {
            System.out.println("Vous avez perdu la partie");
            Platform.exit();
        }
    }

    private void dessinerLabyrinhe(Labyrinthe labyrinthe, int i, int j, GraphicsContext gc) {
        Type type = labyrinthe.getPlateau()[i][j].getType();
        if (type != null) {
            switch (type) {
                case MUR: {
                    setImage(gc, i, j, mur);
                    break;
                }
                case PACMAN: {
                    setImage(gc, i, j, pacman_normal);
                    break;
                }
                case SUPERPACMAN: {
                    setImage(gc, i, j, pacman_invincible);
                    break;
                }
                case PACGOMME: {
                    setImage(gc, i, j, boule_de_gomme);
                    break;
                }
                case FRUIT: {
                    setImage(gc, i, j, fruit);
                    break;
                }
                case FANTOME: {
                    setImage(gc, i, j, enemy_normal);
                    break;
                }
                case CHAMPIGNON: {
                    setImage(gc, i, j, champignon);
                    break;
                }
                case FANTOMECOMPOSITE2: {
                    setImage(gc, i, j, fantomegroupe2);
                    break;
                }
                case FANTOMECOMPOSITE3: {
                    setImage(gc, i, j, fantomegroupe3);
                    break;
                }

                case FANTOMECOMPOSITE4: {
                    setImage(gc, i, j, fantomegroupe4);
                    break;
                }

                case VIDE:
                    break;
                default:
                    break;
            }
        }
    }

    private void setImage(GraphicsContext gc, int i, int j, Image image) {
        gc.drawImage(image, j * TAILLE, i * TAILLE, TAILLE, TAILLE);

    }

    public void infoJeu(Labyrinthe labyrinthe) {

        lScore.setText("Point: " + labyrinthe.scorePacman() + " ");
        lVie.setText("Vie Pacman: " + labyrinthe.viePacman() + " ");
        lFantome.setText("Fantômes restants: " + labyrinthe.getNbrFantome() + " ");
        lPacGomme.setText("Gommes restant: " + labyrinthe.getNbrPacGomme() + " ");

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Labyrinthe) {
            Labyrinthe labyrinthe = (Labyrinthe) arg;
            afficherMatriceFX(labyrinthe);

        }
    }

}
