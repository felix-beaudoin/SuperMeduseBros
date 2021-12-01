package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.GameObject;
import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends GameObject {

    //constants
    private static final double HEIGHT = 10;
    private static final double MIN_WIDTH = 80, MAX_WIDTH = 170;
    private static final Color defaultColor = Color.rgb(230, 134, 58);

    public Plateforme(Partie partie) {
        this(0, 0, defaultColor, partie);
    }

    protected Plateforme(double x, double y, Partie partie) {
        this(x, y, defaultColor, partie);
    }

    protected Plateforme(double x, double y, Color color, Partie partie) {
        super(x,y, Math.random() * (MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH, HEIGHT, partie);
        this.color = color;
    }

    public void jumpOn(){

    }

    public void landOn(){

    }

    @Override
    public void draw(GraphicsContext context, long now) {
        context.setFill(color);
        context.fillRect(xScreen, yScreen, width, height);
    }
}
