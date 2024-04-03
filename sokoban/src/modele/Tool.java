package modele;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Tool {

    public Tool()
    {

    }

    public Tool(int Niveaux_numero,Jeu jeu_a_init)
    {
        lecture_fichier(this.creation_nom_fichier(Niveaux_numero), jeu_a_init);
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
            _jeu_a_init.Jeu_init_avec_tab(taille_x,taille_y,tab_case);

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }

    }


    public String creation_nom_fichier(int niveau_a_charger){
        return ("Niveaux/Niveau_" + niveau_a_charger);
    }


    public int recuperation_meilleur_score_niveau(int niveau_souhaité)
    {
        String nomFichier="Score/Niveau_"+niveau_souhaité;
        String reponse="";
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {

            reponse = reader.readLine();

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return Integer.parseInt(reponse);
    }

    public void Enregistre_score_dans_fichier(int niveau_souhaité, int score_a_enregistrer) {
        String nomFichier = "Score/Niveau_" + niveau_souhaité; // Nom du fichier à créer ou à écrire

        try {
            FileWriter fileWriter = new FileWriter(nomFichier);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(Integer.toString(score_a_enregistrer));

            bufferedWriter.close();

            System.out.println("Score enregistré avec succès dans le fichier " + nomFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement du score dans le fichier " + nomFichier);
            e.printStackTrace();
        }
    }

}
