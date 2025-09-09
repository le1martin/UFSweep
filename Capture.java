import java.awt.*;
import java.util.ArrayList;

public class Capture {

    ///Handles the comet capture checking logic
    public static void check(UFSweep game) {

        //checks if the game is running
        if (!game.isGameActive()) return;

        //expand the ufo's hitbox slightly to improve collision detection accuracy
        int padding = 5;
        int hitboxX = game.getUfoX() - padding;
        int hitboxY = game.getUfoY() + game.getUiOffset() - padding;
        int hitboxWidth = game.getUfoWidth() + padding * 2;
        int hitboxHeight = game.getUfoHeight() + padding * 2;
        Rectangle ufoHitbox = new Rectangle(game.getUfoX()-5, hitboxY, hitboxWidth, hitboxHeight);

        //list to store comets that are still not captured
        ArrayList<Point> remainingComets = new ArrayList<>();

        //checks each comet to see if it intersects with the ufo's hitbox
        for (Point comet : game.getComets()) {

            //define a rectangle representing the comet's collision area
            Rectangle comet_hitbox = new Rectangle(comet.x, comet.y + game.getUiOffset(), game.getCometSize(), game.getCometSize());

            //if the ufo collides with a comet, increase the counter by one 
            if (ufoHitbox.intersects(comet_hitbox)) {
                game.setCometsCaptured(game.getCometsCaptured() + 1);
                game.playCollisionSound(); // always plays on collision
            }
            //if the comet is not captured, add it to the list
            else {
                remainingComets.add(comet);
            }
        }

        //updates the game with the remaining comets
        game.setComets(remainingComets);

        //refresh the game
        game.repaint();
    }
}
