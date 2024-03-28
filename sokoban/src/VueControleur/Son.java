package VueControleur;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
public class Son {

    private Clip clip;

    private AudioInputStream tir_converted;


    public void jouerSon(String songFilename) {
        try {
            // Vérifier si un clip est en cours de lecture
            if (clip != null && clip.isRunning()) {
                clip.stop(); // Arrêter le clip en cours
                clip.close(); // Fermer le clip en cours
            }


            File f = new File(songFilename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);

            AudioFormat baseFormat = audioIn.getFormat();

            // Convertir le format du fichier audio si nécessaire
            AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            AudioInputStream convertedIn = AudioSystem.getAudioInputStream(targetFormat, audioIn);

            // Ouvrir le clip avec le fichier audio converti
            clip = AudioSystem.getClip();
            clip.open(convertedIn);

            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }





    }
}
