package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends GameObject {

    Plateforme(double WIDTH, double HEIGHT) {
        super(WIDTH, HEIGHT);
        color = Color.rgb(230, 134, 58);
        h = 10;
        w = (Math.random() * 95) + 80;
        x = (Math.random() * (WIDTH - w));
        y = Math.random() * HEIGHT;// TODO A CHANGER
    }

    public void jumpOn(){

    }

    public void landOn(){}

    @Override
    public void draw(GraphicsContext context, long now) {
        context.setFill(color);
        context.fillRect(x, y, w, h);
    }
}
