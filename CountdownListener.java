import java.awt.event.*;

///Handles the timer and countdown logic of the game
public class CountdownListener implements ActionListener {
    private UFSweep game; 

    //links the countdown listener to the game
    public CountdownListener(UFSweep game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int timeLeft = game.getTimeLeft() - 1; //gets the current time and subtracts it by 1
        game.setTimeLeft(timeLeft); //updates the timer

        //if the timer hits zero, end the game
        if (timeLeft <= 0) {
            game.setGameActive(false); //turns off the game
            game.getCountdown().stop(); //stops the countdown timer
            game.getStateCheckbox().setSelected(false); //unchecks the checkbox (indicate game is no longer active)
        }
    }
}
