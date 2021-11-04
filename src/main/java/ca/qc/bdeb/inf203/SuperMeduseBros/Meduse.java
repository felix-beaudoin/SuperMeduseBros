package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Meduse extends GameObject{
    Image sprite;
    Meduse(){
        w = 50;
        h = 50;
        x = 175 - 50;
        y = 490;
        Image sprite = new Image("meduse1.png", 500, 500, false, false);
    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(sprite, w, h);
        System.out.println("yo");
    }
}
