/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;


import VueControleur.Son;

import java.awt.Point;
import java.nio.channels.AsynchronousByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.List;




public class Jeu extends Observable {

    public static int SIZE_X;
    public static int SIZE_Y;

    public boolean jeu_fini=false;

    private Case tab_bloc;
    private int atteindre_but = 0;
    private int nombre_but = 0;
    private List<Entite> tab_entite_but;

    private int nombre_pas;

    public void setNombre_but(int nombre_but) {
        this.nombre_but = nombre_but;
    }

    private Heros heros;

    public Tool t=new Tool();


    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence

    private Case[][] grilleEntites = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées


    private Niveaux niveaux;

    private Son son_jeu=new Son();

    
    public Jeu() {
        tab_entite_but=new ArrayList<>();
        initialisationNiveau();
        niveaux = new Niveaux(this);
    }

    public void Jeu_init_avec_tab(int taille_x,int taille_y,String tab_terrain[][])
    {
        this.SIZE_X=taille_x;
        this.SIZE_Y=taille_y;
        initialisationNiveau_a_partir_fichier(tab_terrain);
    }

    public int GetNiveaux()
    {
        return niveaux.niveau_actuel;
    }

    public Case[][] getGrille() {
        return grilleEntites;
    }

    public Heros getHeros() {
        return heros;
    }

    public boolean deplacerHeros(Direction d) {
        boolean reponse=heros.avancerDirectionChoisie(d);
        setChanged();
        notifyObservers();
        return reponse;
    }


    private void initialisationNiveau() {
        nombre_but=0;atteindre_but=0;nombre_pas=0;
        // murs extérieurs horizontaux
        for (int x = 0; x < SIZE_X; x++) {
            addCase(new Mur(this), x, 0);
            addCase(new Mur(this), x, SIZE_Y - 1);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < SIZE_Y; y++) {
            addCase(new Mur(this), 0, y);
            addCase(new Mur(this), SIZE_X-1, y);
        }

        for (int x = 1; x < SIZE_X-1; x++) {
            for (int y = 1; y < SIZE_Y-1; y++) {
                addCase(new Vide(this), x, y);
            }
        }



        // ajout des cases de But à atteindre

        Bloc[] tableauDeBlocs = new Bloc[nombre_but];
        for(int i = 0; i < nombre_but; i++)
        {
            int x = t.monRandom(SIZE_X-1, 2);
            int y = t.monRandom(SIZE_Y-1, 2);
            tableauDeBlocs[i] = new Bloc(this,grilleEntites[x][y]);

        }
        //Bloc b = new Bloc(this, grilleEntites[6][6]);

        for(int i = 0; i < nombre_but; i++)
        {
            int x = t.monRandom(SIZE_X-1, 2);
            int y = t.monRandom(SIZE_Y-1, 2);

            addCase(new But(this),x,y);
        }



    }


    private void initialisationNiveau_a_partir_fichier(String m_ter[][]) {
        viderGrille();
        initialisationNiveau();

        jeu_fini = false;







        for(int x=0;x<this.SIZE_X;++x)
            for(int y=0;y<this.SIZE_Y;++y)
            {
                switch(m_ter[x][y])
                {
                    case "#": addCase(new Mur(this), x, y); break;
                    case ".": addCase(new Vide(this),x,y);break;
                    case "h": heros = new Heros(this,grilleEntites[x][y]); break;
                    case "f": addCase(new But(this),x,y);setNombre_but(nombre_but+1);break;
                    case "p": addCase(new Porte(this),x,y);break;
                    case "b": new Bloc(this,grilleEntites[x][y]); break;
                    case "g": addCase(new Grille(this),x,y);break;
                    case "B": addCase(new Bouton(this),x,y);break;

                }
            }


    }

    private void addCase(Case e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }



/** Si le déplacement d2e l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */

    public boolean recupereEtatGrille()
    {
        Grille g = null;
        return g.isEtat_grille();
    }
    
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = true;
        boolean tp=false;

        Point pCourant = map.get(e.getCase());

        Point pCible = calculerPointCible(pCourant, d);

        if (contenuDansGrille(pCible)) {
            Entite eCible = caseALaPosition(pCible).getEntite();
            Entite eCourant = caseALaPosition(pCourant).getEntite();

            if (eCible != null && eCible instanceof Bloc) {
                // On peut pousser le bloc que si la cible est du vide
                if (caseALaPosition(pCible) instanceof Vide || caseALaPosition(pCible) instanceof Bouton || caseALaPosition(pCible) instanceof Grille)
                    eCible.pousser(d);
            }
            if(caseALaPosition(pCible) instanceof Porte)
            {
                Case precedente = caseALaPosition(pCible);
                for(int x=0;x<this.SIZE_X;++x) {
                    for (int y = 0; y < this.SIZE_Y; ++y) {
                        if (grilleEntites[x][y] instanceof Porte && grilleEntites[x][y] != precedente) {

                            if(grilleEntites[x][y].getEntite()!=null)
                                grilleEntites[x][y].getEntite().pousser(d);

                            pCible=new Point(x, y);
                        }

                    }
                }
            }




                    if(caseALaPosition(pCible) instanceof Bouton) {
                        for(int x=0;x<this.SIZE_X;++x) {
                            for (int y = 0; y < this.SIZE_Y; ++y) {
                                if (grilleEntites[x][y] instanceof Grille) {
                                    ((Grille) grilleEntites[x][y]).setEtat_grille(true);
                                }
                            }
                        }
                    }

                    if(caseALaPosition(pCourant) instanceof Bouton) {
                        for(int x=0;x<this.SIZE_X;++x) {
                            for (int y = 0; y < this.SIZE_Y; ++y) {
                                if (grilleEntites[x][y] instanceof Grille) {
                                    ((Grille) grilleEntites[x][y]).setEtat_grille(false);
                                }
                            }
                        }
                    }





            if(eCourant instanceof Heros)
                nombre_pas++;



            // si la case est libérée
            if (caseALaPosition(pCible).peutEtreParcouru()) {
                e.getCase().quitterLaCase();
                caseALaPosition(pCible).entrerSurLaCase(e);
            }
            else
                retour = false;
            

            if(eCible!=null)
                partie_terminee(eCible);



        } else
            retour = false;

        return retour;
    }
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;

        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;

        }

        return pCible;
    }



    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    private Case caseALaPosition(Point p) {
        Case retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }

        return retour;
    }

    public boolean pas_deja_validee(Entite b)
    {
        return tab_entite_but.contains(b);
    }

    public void Recharger_niveau()
    {
        niveaux.Recharger_niveau(this);
    }

    public void partie_terminee(Entite b)
    {
        if(b.getCase() instanceof But && !pas_deja_validee(b)) {
            atteindre_but++;
            tab_entite_but.add(b);
            son_jeu.jouerSon("Banque_son/Mort.wav");
        }
        if(atteindre_but == nombre_but) {
            jeu_fini = true;
            this.enregistre_score();


            niveaux.Changer_niveau(this);
            if (niveaux.niveau_actuel == 6) {

            }
        }
        else
            jeu_fini=false;


        return;
    }


    public void viderGrille() {
        grilleEntites=new Case[SIZE_X][SIZE_Y];
    }

    public int Get_score_actuel(){return this.nombre_pas;}

    private void enregistre_score()
    {
        int meilleur_score=t.recuperation_meilleur_score_niveau(this.GetNiveaux());
        if(meilleur_score>this.nombre_pas)
        {
            t.Enregistre_score_dans_fichier(this.GetNiveaux(),this.nombre_pas);
        }
    }

    public void Passer_Niveau(){
        jeu_fini = true;
        niveaux.Changer_niveau(this);
    }
}
