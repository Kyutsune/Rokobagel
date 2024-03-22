
import VueControleur.VueControleur;
import modele.Jeu;
import modele.Tool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Main {
    public static void main(String[] args) {
        Jeu jeu = new Jeu();

        //Pour utiliser la recup de niveau dans un fichier
        Tool test=new Tool(jeu);

        VueControleur vc = new VueControleur(jeu);
        vc.setVisible(true);

    }
}
