package VueControleur;


import javax.sound.sampled.*;
import java.net.URL;
public class Son {


    public void jouerSon(String songFilename) {
        URL resource = ClassLoader.getSystemClassLoader().getResource(songFilename);
        try {
            final Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.open(AudioSystem.getAudioInputStream(resource));
            clip.start();
        } catch (Exception e) {
            System.out.println("Failed to play sound " + songFilename+ e);
        }

    }
}
