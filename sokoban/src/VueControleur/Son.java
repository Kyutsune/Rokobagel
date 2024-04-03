package VueControleur;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Son {

    private Clip clip;

    public void jouerSon(String songFilename) {
        try {
            /*
            A rajouter si on veut que les sons ne puissent pas se superposer
            On l'enlève car cela peut faire buguer l'appli et que nos son sont trop courts pour que ce soit
            important
            (on le laisse en commentaire au cas ou)
            // Vérifier si un clip est en cours de lecture
            if (clip != null && clip.isRunning()) {
                clip.stop(); // Arrêter le clip en cours
                clip.close(); // Fermer le clip en cours
            }
            */


            // Charger le fichier audio
            File f = new File(songFilename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);


            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Démarrer la lecture du clip
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }



}

