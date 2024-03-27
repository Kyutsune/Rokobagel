package modele;

public class Niveaux  {
    public int niveau_actuel = 1;
    public Niveaux(Jeu jeu_en_entree)
    {
        Tool t = new Tool(niveau_actuel, jeu_en_entree);
    }

    public void Changer_niveau()
    {
        niveau_actuel++;
    }


}
