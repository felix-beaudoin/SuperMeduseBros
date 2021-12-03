package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulles extends GameObject{
    protected int r;

    public Bulles(double x, double y, double width, double height, Partie partie) {
        super(x, y, width, height, partie);
        r = (int) (10 + Math.random() * 30);
        vy = (int) (350 + Math.random() * 100);
        color = Color.rgb(0, 0, 255, 0.4);
        y = partie.getCamera().getBottom();
        this.x = x + (Math.random() * 10) - 5;
    }

    @Override
    public void draw(GraphicsContext context, long now) {
        context.setFill(color);
        context.fillOval(xScreen, yScreen, width, height);
    }
}

