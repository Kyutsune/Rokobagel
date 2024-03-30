package VueControleur;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import javax.swing.JOptionPane;


import modele.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoBloc;
    private ImageIcon icoVictoire;

    private ImageIcon icoPorte;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    private Son son_jeu=new Son();



    public void InitialiserTailleTerrain(Jeu _jeu)
    {
        sizeX = _jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
    }

    public VueControleur(Jeu _jeu) {
        InitialiserTailleTerrain(_jeu);
        jeu = _jeu;

        //demanderNombreBut();
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();

        jeu.addObserver(this);

        mettreAJourAffichage();

    }

    private void demanderNombreBut()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Saisir le nombre de but (entier) : ");
        int nbBut = scanner.nextInt();
        jeu.setNombre_but(nbBut);
    }
    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {


                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée



                    case KeyEvent.VK_LEFT :
                        if(jeu.deplacerHeros(Direction.gauche))
                            son_jeu.jouerSon("Banque_son/Bang.wav");
                        break;

                    case KeyEvent.VK_RIGHT :
                        if(jeu.deplacerHeros(Direction.droite))
                            son_jeu.jouerSon("Banque_son/Ting.wav");
                        break;

                    case KeyEvent.VK_DOWN :
                        if(jeu.deplacerHeros(Direction.bas))
                            son_jeu.jouerSon("Banque_son/Ting.wav");
                        break;

                    case KeyEvent.VK_UP :
                        //deplacement=jeu.deplacerHeros(Direction.haut);
                        if(jeu.deplacerHeros(Direction.haut))
                            son_jeu.jouerSon("Banque_son/Son_coq.wav");
                        break;

                    case KeyEvent.VK_R:
                        jeu.Recharger_niveau();
                        mettreAJourAffichage();
                        break;

                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;


                }
                if(jeu.jeu_fini)
                    System.out.println("fini");
                if(jeu.GetNiveaux() == 6)
                    System.exit(0);

                //JOptionPane.showMessageDialog(null, "Ceci est un message d'information.");

            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/Perso.jpg");
        icoVide = chargerIcone("Images/Vide.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoBloc = chargerIcone("Images/Bloc.jpg");
        icoVictoire = chargerIcone("Images/But.jpg");
        icoPorte = chargerIcone("Images/Porte.jpg");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        InitialiserTailleTerrain(this.jeu);

        setTitle("Sokoban");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];


        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )

                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {
        InitialiserTailleTerrain(this.jeu);

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                Case c = jeu.getGrille()[x][y];

                if (c != null) {

                    Entite e = c.getEntite();

                    if (e!= null) {
                        if (c.getEntite() instanceof Heros) {
                            tabJLabel[x][y].setIcon(icoHero);
                        } else if (c.getEntite() instanceof Bloc) {
                            tabJLabel[x][y].setIcon(icoBloc);
                        }
                    } else {
                        if (jeu.getGrille()[x][y] instanceof Mur) {
                            tabJLabel[x][y].setIcon(icoMur);
                        } if (jeu.getGrille()[x][y] instanceof Vide) {

                                tabJLabel[x][y].setIcon(icoVide);
                        } if(jeu.getGrille()[x][y] instanceof Porte){
                            tabJLabel[x][y].setIcon(icoPorte);
                        }
                        else if (jeu.getGrille()[x][y] instanceof But) {

                            tabJLabel[x][y].setIcon(icoVictoire);
                        }
                    }

                }

            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        placerLesComposantsGraphiques();
        mettreAJourAffichage();
        /*

        // récupérer le processus graphique pour rafraichir
        // (normalement, à l'inverse, a l'appel du modèle depuis le contrôleur, utiliser un autre processus, voir classe Executor)


        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }
}
