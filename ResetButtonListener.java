import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

///Handles the reset logic of the game
public class ResetButtonListener implements ActionListener {
    private UFSweep game;

    //links the reset button listener to the game
    public ResetButtonListener(UFSweep game) {
        this.game = game;
    }

    //resets the game
    public static void reset(UFSweep game) {
        //resets the score and timer
        game.setCometsCaptured(0);
        game.setTimeLeft(10);
        
        //sets the game to active
        game.setGameActive(true);

        //resets ufo starting position
        game.setUfoX(100);
        game.setUfoY(100);

        //resets the checkbox to its initial state
        game.getStateCheckbox().setSelected(true);
        game.getStateCheckbox().setEnabled(false);

        //generate a list of 10 random comet positions within the game
        ArrayList<Point> newComets = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int x = r.nextInt(924); //random x coordinate within the bounds of the game
            int y = game.getUiOffset() + r.nextInt(668); //random y coordinate within the bounds of the game (ui offset is taken in account to ensure no overlap)
            newComets.add(new Point(x, y));
        }
        game.setComets(newComets); //updates the comets in the game

        game.getCountdown().start(); //start the countdown timer
        game.repaint(); //refresh the game
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        reset(game); //reset the game when button is clicked
        game.requestFocus(); //ensure game has keyboard focus
    }

}
