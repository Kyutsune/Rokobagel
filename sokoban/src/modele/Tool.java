package modele;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tool {

    public Tool()
    {

    }

    public Tool(Jeu jeu_a_init)
    {
        lecture_fichier("Niveaux/Niveau_1",jeu_a_init);
    }


    public static int monRandom(int max, int min) {
        // Générer un nombre aléatoire entre 0.0 (inclus) et 1.0 (exclus)
        double randomDouble = Math.random();

        // Calculer la plage entre max et min
        int range = max - min;

        // Échelonner le nombre aléatoire dans la plage désirée et le convertir en int
        int randomInt = (int)(randomDouble * range) + min;

        return randomInt;
    }

    public static void lecture_fichier(String nomFichier, Jeu _jeu_a_init) {
        String ligne;
        String[][] tab_case;
        int taille_x, taille_y;
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            taille_x = Integer.parseInt(reader.readLine());
            taille_y = Integer.parseInt(reader.readLine());

            tab_case = new String[taille_x][taille_y];


            for (int i = 0; i < taille_x; i++) {
                ligne = reader.readLine();

                if (ligne != null) {
                    tab_case[i] = ligne.split("");
                }

            }
            System.out.println(taille_x);
            System.out.println(taille_y);
            _jeu_a_init.Jeu_init_avec_tab(taille_x,taille_y,tab_case);

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }

    }
}
