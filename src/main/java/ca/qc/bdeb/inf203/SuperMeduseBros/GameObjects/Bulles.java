package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;

public class Bulles extends GameObject{
    protected int r;

    public Bulles(double x, double y, double width, double height, Partie partie) {
        super(x, y, width, height, partie);
        r = (int) (10 + Math.random() * 30);
        vy = (int) (350 + Math.random() * 100);

    }

    @Override
    public void draw(GraphicsContext context, long now) {
        
    }
}

