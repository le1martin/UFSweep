import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

///Sets up the main window of the game and holds the getters/setters, visuals and logic of the game
public class UFSweep extends JPanel {

    private SoundManager soundManager;

    //initalize the frame
    private JFrame frame;

    //initalize the ufo position and size
    private int ufoX = 100;
    private int ufoY = 100;
    private int ufoWidth = 60; 
    private int ufoHeight = 30;

    //initalize the comet size and comet list
    private int cometSize = 15;
    private ArrayList<Point> comets;
    private int cometsCaptured = 0;
    
    //initalize the countdown timer and time left
    private Timer countdown;
    private int timeLeft = 11;

    //initalize the ui components
    private JLabel countLabel;
    private JLabel timeLabel;
    private JButton resetButton;
    private JCheckBox stateCheckbox;

    //initalize the game state 
    private boolean gameActive;

    //initalize the vertical offset to ensure that the ui doesn't overlap with the game
    private int uiOffset = 40; 


    public UFSweep() {

        //initialize sound manager
        soundManager = new SoundManager(); 

        //creates the game window
        frame = new JFrame("UFSweep");

        //sets the size of the window (1024 by 768 pixels)
        frame.setSize(1024, 768);

        //sets the dimensions and background color of the game
        this.setPreferredSize(new Dimension(1024,768));
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(null);

        //label to display the number of comets captured (start at 0)
        countLabel = new JLabel("Comets Captured: 0"); 
        countLabel.setBounds(430, 10, 150, 20); 
        countLabel.setForeground(Color.WHITE); 
        countLabel.setFont(new Font("Verdana", Font.BOLD, 12)); 

        //label to display the countdown timer (start at 10)
        timeLabel = new JLabel("Time: 10" ); 
        timeLabel.setBounds(180, 8, 150, 25); 
        timeLabel.setForeground(Color.WHITE); 
        timeLabel.setFont(new Font("Verdana", Font.BOLD, 12)); 

        //reset button to restart the game when clicked
        resetButton = new JButton("Reset"); 
        resetButton.setBounds(910, 8, 90, 23); 
        resetButton.setFont(new Font("Verdana", Font.BOLD, 12)); 

        //checkbox to indicate whether the game is running
        stateCheckbox = new JCheckBox("Game Active"); 
        stateCheckbox.setBounds(10, 10, 120, 20); 
        stateCheckbox.setFont(new Font("Verdana", Font.BOLD, 12)); 
        stateCheckbox.setBackground(Color.DARK_GRAY); 
        stateCheckbox.setSelected(true); //check the box to indicate that the game is running at the start
        stateCheckbox.setEnabled(false); //makes the checkbox unclickable so players can't turn the game on or off
        
        //adds the ui elements to the game
        this.add(countLabel);
        this.add(timeLabel);
        this.add(resetButton);
        this.add(stateCheckbox);
        
        //connects the reset game logic to the reset button 
        ResetButtonListener reset = new ResetButtonListener(this);
        resetButton.addActionListener(reset);

        //start a timer that countdowns every second 
        CountdownListener time = new CountdownListener(this);
        countdown = new Timer(1000, time); 
    
        //ensures that the game can receive keyboard input
        KeyTracker kt = new KeyTracker(this);
        this.setFocusable(true);
        this.addKeyListener(kt);

        //initalize the game to its default state
        ResetButtonListener.reset(this);

        //add the game to the frame 
        frame.add(this);
        frame.setVisible(true);
    }

    public void playCollisionSound() { soundManager.playCollisionSound(); }

    //getter for the x position of the ufo
    public int getUfoX() { 
        return ufoX; 
    }
    
    //setter for the x position of the ufo
    public void setUfoX(int ufoX) { 
        this.ufoX = ufoX;
    }

    //getter for the y position of the ufo
    public int getUfoY() { 
        return ufoY; 
    }
    //setter for the y position of the ufo
    public void setUfoY(int ufoY) { 
        this.ufoY = ufoY; 
    }

    //getter for the width of the ufo
    public int getUfoWidth() { 
        return ufoWidth; 
    }
    //setter for the width of the ufo
    public void setUfoWidth(int ufoWidth) { 
        this.ufoWidth = ufoWidth;
    }

    //getter for the height of the ufo
    public int getUfoHeight() { 
        return ufoHeight; 
    }
    //setter for the height of the ufo
    public void setUfoHeight(int ufoHeight) { 
        this.ufoHeight = ufoHeight;
    }

    //getter for comet size
    public int getCometSize() { 
        return cometSize; 
    }

    //getter for the number of coment captured 
    public int getCometsCaptured() { 
        return cometsCaptured; 
    }
    //setter for the number of comets captured
    public void setCometsCaptured(int captured) { 
        this.cometsCaptured = captured; 
        countLabel.setText("Comets Captured: " + captured); //updates the number of comets captured
    }

    //getter for the remaining time left in the game
    public int getTimeLeft() { 
        return timeLeft; 
    }
    //setter for the remaining time left 
    public void setTimeLeft(int timeLeft) { 
        this.timeLeft = timeLeft; 
        timeLabel.setText("Time: " + timeLeft); // updates the amount of time left
    }

    //getter to check whether the game is active
    public boolean isGameActive() { 
        return gameActive; 
    }
    //setter for the current game state (true = active and false = inactive)
    public void setGameActive(boolean active) { 
        this.gameActive = active; 
    }

    //getter for the list of comet positions
    public ArrayList<Point> getComets() { 
        return comets; 
    }
    //setter to update the list of comet positions
    public void setComets(ArrayList<Point> comets) { 
        this.comets = comets; 
    }

    //getter for the vertical spacing between the UI and game area
    public int getUiOffset() { 
        return uiOffset; 
    }

    //getter for the countdown timer used to track game time
    public Timer getCountdown() { 
        return countdown; 
    }

    //getter for the game state checkbox 
    public JCheckBox getStateCheckbox() {
        return stateCheckbox;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draws a line to seperate the ui from the actual game
        g.setColor(Color.GRAY);
        g.drawLine(0, 35, 1024, 35);

        //draws the comets (as white circles)
        g.setColor(Color.WHITE);
        for (Point comet : comets) {
            g.fillOval(comet.x, comet.y, cometSize, cometSize);
        }

        //draws the ufo
        g.setColor(Color.GRAY);
        g.fillOval(ufoX, ufoY, ufoWidth, ufoHeight);
        g.setColor(Color.CYAN);
        g.fillArc(ufoX + 10, ufoY - 5, 40, 40, 0, 180);
        g.setColor(Color.RED);
        g.fillOval(ufoX + 17, ufoY + 18, 5, 5);
        g.setColor(Color.BLUE);
        g.fillOval(ufoX + 27, ufoY + 18 , 5, 5);
        g.setColor(Color.ORANGE);
        g.fillOval(ufoX + 37, ufoY + 18, 5, 5);
    }
}