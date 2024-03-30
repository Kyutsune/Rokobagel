package modele;

public class Grille extends Case {

    private boolean etat_grille = false;
    public void setEtat_grille(boolean etat_grille) {
        this.etat_grille = etat_grille;
    }

    public boolean isEtat_grille() {
        return etat_grille;
    }

    public Grille(Jeu _jeu) { super(_jeu); }

    public boolean peutEtreParcouru() {
        if (isEtat_grille()) {
            return e == null;
        }
        else
            return false;
    }
}
