package modele;

public class Porte extends Case{
    public Porte(Jeu _jeu) { super(_jeu); }

    public boolean peutEtreParcouru() {
        return e == null;
    }
}
