package modele;

public class But extends Case {
    public But(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }



}
