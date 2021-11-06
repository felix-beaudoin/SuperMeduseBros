package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

import static ca.qc.bdeb.inf203.SuperMeduseBros.Input.isKeyPressed;

public class Meduse extends GameObject {
    Image[] framesG;
    Image[] framesD;
    LastDirection ld = LastDirection.NONE;

    Meduse(double WIDTH, double HEIGHT) {
        super(WIDTH, HEIGHT);
        this.w = 50;
        this.h = 50;
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
    public void update(double deltaTemps, ArrayList<GameObject> gameObjects) {
        boolean left = isKeyPressed(KeyCode.LEFT);
        boolean right = isKeyPressed(KeyCode.RIGHT);
        boolean jump = (isKeyPressed(KeyCode.SPACE) || isKeyPressed(KeyCode.UP));

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
        }// mouvement gauche / droite


        ay = 1200;



        if (x <= 0) {// rebondir sur les murs
            x = 0;
            vx = -vx;
            ld = LastDirection.RIGHT;
        } else if (x + w >= 350) {
            x = 350 - w;
            vx = -vx;
            ld = LastDirection.LEFT;
        }



        if (isOnPlateform(gameObjects)) {// si collision avec plateforme
            if (jump) {
                vy = -600; // si on saute, appliquer la vitesse vers le haut

            } else {
                ay = 0; //          si non, il faut rester immobile, sur la plateforme. aka ne pas tomber
                if (vy > 0){ // si vitesse est vers le bas, on la reinitialise a 0.
                    vy = 0;
                }
            }
        }
        super.update(deltaTemps, gameObjects);

    }

    public void updatePhysique(double deltaTemps) {
        vx += ax * deltaTemps;
        vy += ay * deltaTemps;

        x += vx * deltaTemps;
        y += vy * deltaTemps;
    }

    private boolean isOnPlateform(ArrayList<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Plateforme) {
                if (gameObject.getHaut() <= this.getBas() && gameObject.getBas() > this.getBas() &&
                        gameObject.getDroite() >= this.getGauche() && gameObject.getGauche() <= this.getDroite()
                ) {
                    y = gameObject.getHaut() - h;
                    ((Plateforme) gameObject).landOn();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void draw(GraphicsContext context, long now) {


        boolean left = isKeyPressed(KeyCode.LEFT);
        boolean right = isKeyPressed(KeyCode.RIGHT);


        int frame = (int) Math.floor(now * 8 * 1e-9);

        if ((ld == LastDirection.RIGHT && !left) || right) {
            context.drawImage(framesD[frame % framesD.length], x, y);
            ld = LastDirection.RIGHT;
        } else if (ld == LastDirection.LEFT || left) {
            context.drawImage(framesG[frame % framesG.length], x, y);
            ld = LastDirection.LEFT;
        } else {
            context.drawImage(framesG[frame % framesG.length], x, y);
        }

        // TODO si v = 0, garder la direction qu'on était, probleme est au debut du jeu, quoi faire, comment faire?
        //todo              changer le sprite en fonction de la vitesse ou du input?
    }
}
