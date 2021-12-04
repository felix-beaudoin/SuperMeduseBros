package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle extends GameObject {

    private static final double MIN_RADIUS = 10;
    private static final double MAX_RADIUS = 40;
    private static final double OFFSET_X = 20;

    public Bulle(double x, Partie partie) {
        super(partie);
        height = width = MIN_RADIUS + Math.random() * (MAX_RADIUS - MIN_RADIUS);
        vy = -(350 + Math.random() * 100);
        color = Color.rgb(0, 0, 255, 0.4);
        y = partie.getCamera().getBottom() + height / 2;
        this.x = x + Math.random() * (2*OFFSET_X) - OFFSET_X;
    }

    @Override
    public void draw(GraphicsContext context, long now) {
        context.setFill(color);
        context.fillOval(xScreen, yScreen, width, height);
    }
}

