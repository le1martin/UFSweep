import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

///Handles the keyboard input for moving the ufo
public class KeyTracker implements KeyListener {

    private UFSweep game;

    //links the key tracker to the game
    public KeyTracker(UFSweep game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent m) {
        //checks if the game is running
        if (game.isGameActive() == true) {
            int code = m.getKeyCode(); //gets the code of the key that was pressed

            //move the ufo left
            if (code == KeyEvent.VK_LEFT) {
                int ufo_x = game.getUfoX();
                ufo_x -= 10;
                if (ufo_x < 0) {
                    ufo_x = 0; //prevents the ufo from going left off the screen
                }
                game.setUfoX(ufo_x);
            }

            //move the ufo right
            else if (code == KeyEvent.VK_RIGHT) {
                int ufo_x = game.getUfoX();
                ufo_x += 10;
                if (ufo_x > game.getWidth() - game.getUfoWidth()) {
                    ufo_x = game.getWidth() - game.getUfoWidth(); //prevents the ufo from going right off the screen
                }
                game.setUfoX(ufo_x);
            }

            //move the ufo up
            else if (code == KeyEvent.VK_UP) {
                int ufo_y = game.getUfoY();
                ufo_y -= 10;
                if (ufo_y < game.getUiOffset()) {
                    ufo_y = game.getUiOffset(); //prevents the ufo from touching the UI
                }
                game.setUfoY(ufo_y);
            }

            //move the ufo down
            else if (code == KeyEvent.VK_DOWN) {
                int ufo_y = game.getUfoY();
                ufo_y += 10;
                if (ufo_y > game.getHeight() - game.getUfoHeight()) {
                    ufo_y = game.getHeight() - game.getUfoHeight(); //prevents the ufo from going below the screen
                }
                game.setUfoY(ufo_y);
            }

            Capture.check(game); //checks if a comet has been captured after pressing the keys
            game.repaint(); //updates the screen
        }
    }

    //unused methods (needed for KeyListener interface)
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}