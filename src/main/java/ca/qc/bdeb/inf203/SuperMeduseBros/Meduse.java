package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import static ca.qc.bdeb.inf203.SuperMeduseBros.Input.isKeyPressed;

public class Meduse extends GameObject{
    Image[] framesG;
    Image[] framesD;
    Meduse(){
        w = 50;
        h = 50;
        x = 175 - 50;
        y = 490;

        this.framesG = new Image[6];
        for (int i = 1; i <= 6; i++){
            framesG[i-1] = new Image("meduse" + i + "-g.png", 50, 50, false, false);
        }

        this.framesD = new Image[6];
        for (int i = 1; i <= 6; i++){
            framesD[i-1] = new Image("meduse" + i + ".png", 50, 50, false, false);
        }
    }

    @Override
    public void update(double deltaTemps) {

        if (isKeyPressed(KeyCode.RIGHT) && !isKeyPressed(KeyCode.LEFT)) {
            ax = 1200;
        } else if (!isKeyPressed(KeyCode.RIGHT) && isKeyPressed(KeyCode.LEFT)) {
            ax = -1200;
        } else if (!isKeyPressed(KeyCode.RIGHT) && !isKeyPressed(KeyCode.LEFT)){
                ax = -ax;
        }
        super.update(deltaTemps);
    }

    @Override
    public void draw(GraphicsContext context, long now) {

        int frame = (int) Math.floor(now * 8*1e-9);

        if (vx >= 0){
        context.drawImage(framesD[frame % framesD.length], x, y);
        } else if (vx < 0) {
            context.drawImage(framesG[frame % framesG.length], x, y);
        } // TODO si v = 0, garder la direction qu'on était, probleme est au debut du jeu, quoi faire, comment faire?
    }
}
