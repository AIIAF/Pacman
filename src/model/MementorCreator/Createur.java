package Model.MementorCreator;

/**
 *
 * @author SONA
 */
public class Createur {

    private model.Labyrinthe labyrinthe;

    public void setLabyrinthe(model.Labyrinthe lab) {
        this.labyrinthe = lab;

    }

    public model.Memento save() {
        return new MementoImpl(labyrinthe);
    }

    public void restore(model.Memento m) {
        labyrinthe = m.getLabyrinthe();
    }

    public model.Labyrinthe getLabyrinthe() {
        return labyrinthe;
    }

}
