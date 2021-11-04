package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import static ca.qc.bdeb.inf203.SuperMeduseBros.Input.isKeyPressed;

public class Meduse extends GameObject {
    Image[] framesG;
    Image[] framesD;

    Meduse() {
        w = 50;
        h = 50;
        x = 0;
        y = 0;

        this.framesG = new Image[6];
        for (int i = 1; i <= 6; i++) {
            framesG[i - 1] = new Image("meduse" + i + "-g.png", 50, 50, false, false);
        }

        this.framesD = new Image[6];
        for (int i = 1; i <= 6; i++) {
            framesD[i - 1] = new Image("meduse" + i + ".png", 50, 50, false, false);
        }
    }

    @Override
    public void update(double deltaTemps) {
        boolean left = isKeyPressed(KeyCode.LEFT);
        boolean right = isKeyPressed(KeyCode.RIGHT);
        boolean jump = isKeyPressed(KeyCode.SPACE);

        ay = 1200;
        if (right && !left) {
            ax = 1200;
        } else if (!right && left) {
            ax = -1200;
        } else {


            ax = 0;

            int signeVitesse = vx > 0 ? 1 : -1;
            double vitesseAmortissementX = -signeVitesse * 300;
            vx += deltaTemps * vitesseAmortissementX;
            int nouveauSigneVitesse = vx > 0 ? 1 : -1;
            if (nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }


        }

        if (jump){
            vy = -600;
        }


        if (x <= 0) {
            x = 0;
            vx = -vx;
        } else if (x + w >= 350) {
            x = 350 - w;
            vx = -vx;
        }

        super.update(deltaTemps);

        if (y > 480)// juste en attendant
            y = -h;
    }

    @Override
    public void draw(GraphicsContext context, long now) {

        int frame = (int) Math.floor(now * 8 * 1e-9);

        if (vx >= 0) {
            context.drawImage(framesD[frame % framesD.length], x, y);
            System.out.println(x + "     " + y);
        } else if (vx < 0) {
            context.drawImage(framesG[frame % framesG.length], x, y);
        } // TODO si v = 0, garder la direction qu'on était, probleme est au debut du jeu, quoi faire, comment faire?
    }
}
