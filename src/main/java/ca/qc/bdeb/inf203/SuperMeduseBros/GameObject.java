package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class GameObject {

    double x, y, vx, vy, ax, ay, w, h;
    Color color;

    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }

    public void updatePhysique(double deltaTemps) {
        vx += ax * deltaTemps;
        vy += ay * deltaTemps;

        x += vx * deltaTemps;
        y += vy * deltaTemps;
    }

    public abstract void draw(GraphicsContext context, long now);


}
