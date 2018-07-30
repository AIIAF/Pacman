package Model.MementorCreator;

import model.Memento;
import model.Labyrinthe;


/**
 *
 * @author SONA
 */
public class MementoImpl implements Memento {

    private Labyrinthe labyrinthe;

    public MementoImpl(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }

    @Override
    public Labyrinthe getLabyrinthe() {
        return labyrinthe;
    }

}