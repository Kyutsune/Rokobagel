package modele;

public class Niveaux  {
    public int niveau_actuel = 1;
    Tool t;
    public Niveaux(Jeu jeu_en_entree)
    {
        t = new Tool(niveau_actuel, jeu_en_entree);
    }

    public void Changer_niveau(Jeu jeu_en_entree)
    {
        niveau_actuel++;
        t.lecture_fichier(t.creation_nom_fichier(niveau_actuel),jeu_en_entree);
    }


}
