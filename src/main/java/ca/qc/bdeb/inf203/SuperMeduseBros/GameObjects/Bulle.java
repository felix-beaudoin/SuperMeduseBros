package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bulle extends GameObject {

    public Bulle(double x, Partie partie) {
        super(partie);
        width = (int) (10 + Math.random() * 30);
        vy = -(350 + (Math.random() * 100));
        color = Color.rgb(0, 0, 255, 0.4);
        y = partie.getGameHeight() + height / 2;
        this.x = (x + (Math.random() * 20)) - 10;
        height = width;
    }

    @Override
    public void draw(GraphicsContext context, long now) {
        context.setFill(color);
        context.fillOval(x, y, width, height);
    }
}

