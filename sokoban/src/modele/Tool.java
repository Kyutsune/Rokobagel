package modele;

public class Tool {
    public static int monRandom(int max, int min) {
        // Générer un nombre aléatoire entre 0.0 (inclus) et 1.0 (exclus)
        double randomDouble = Math.random();

        // Calculer la plage entre max et min
        int range = max - min;

        // Échelonner le nombre aléatoire dans la plage désirée et le convertir en int
        int randomInt = (int)(randomDouble * range) + min;

        return randomInt;
    }
}
