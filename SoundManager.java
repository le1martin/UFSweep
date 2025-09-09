import java.io.File;
import javax.sound.sampled.*;

///Manages and plays collision sounds for the game
public class SoundManager {

    private Clip[] collisionClips; //pool of clips to allow for overlapping collision sounds

    //constructor initializes the collision sounds
    public SoundManager() {
        loadCollisionSounds();
    }

    //load multiple clips for overlapping collision sounds
    private void loadCollisionSounds() {
        collisionClips = new Clip[5]; //pool of 5 clips
        try {
            //load the audio file
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/collision.wav"));
            //initialize each clip in the pool with the same audio
            for (int i = 0; i < collisionClips.length; i++) {
                collisionClips[i] = AudioSystem.getClip();
                collisionClips[i].open(audioIn);
                //reopen the audio stream for the next clip
                audioIn = AudioSystem.getAudioInputStream(new File("sounds/collision.wav"));
            }
        } 
        catch (Exception e) {
                System.out.println("Exception has occured");
        }
    }

    //plays a collision sound
    public void playCollisionSound() {
        for (Clip clip : collisionClips) {
            //play the first clip that is not currently playing
            if (!clip.isRunning()) {
                clip.setFramePosition(0); //start from the beginning
                clip.start();                    //play the clip
                break;                           //exit loop after playing one clip
            }
        }

        //if all clips are busy, stop and replay the first clip
        collisionClips[0].stop();
        collisionClips[0].setFramePosition(0);
        collisionClips[0].start();
    }
}