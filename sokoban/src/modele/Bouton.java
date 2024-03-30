package modele;

public class Bouton extends Case{

    public Bouton(Jeu _jeu) { super(_jeu); }

    public boolean peutEtreParcouru() {
        return e == null;
    }
}
