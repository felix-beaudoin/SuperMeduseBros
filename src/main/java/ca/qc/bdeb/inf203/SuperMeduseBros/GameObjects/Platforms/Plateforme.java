package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.GameObject;
import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends GameObject {

    //constants
    private static final double HEIGHT = 10;

    protected Plateforme(double x, double y, double width, Color color, Partie partie) {
        super(x,y, width, HEIGHT, partie);
        this.color = color;
    }

    public void jumpOn(){

    }

    public void landOn(){

    }

    @Override
    public void draw(GraphicsContext context, long now) {
        if (this == partie.getMeduse().getStandingPlatform() && partie.isDebug()){
            context.setFill(Color.YELLOW);
        } else {
        context.setFill(color);
        }
        context.fillRect(xScreen, yScreen, width, height);
    }
}
