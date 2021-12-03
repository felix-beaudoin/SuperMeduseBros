package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms.Plateforme;
import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.function.Function;

import static ca.qc.bdeb.inf203.SuperMeduseBros.Input.isKeyPressed;

public class Meduse extends GameObject {
    // direction enum
    enum LastDirection {LEFT, RIGHT}

    LastDirection ld = LastDirection.RIGHT; //default

    // images
    Image[] framesG;
    Image[] framesD;

    //constants
    public static final double WIDTH = 50;
    public static final double HEIGHT = 50;
    private final boolean canJumpWhileJumping = false; //si la méduse peut sauter quand elle est en train de monter
    private Plateforme standingPlatform;

    public Meduse(double x, double y, Partie partie) {
        super(x, y, WIDTH, HEIGHT, partie);

        this.framesG = new Image[6];
        for (int i = 1; i <= 6; i++) {
            framesG[i - 1] = new Image("meduse" + i + "-g.png", WIDTH, HEIGHT, false, false);
        }

        this.framesD = new Image[6];
        for (int i = 1; i <= 6; i++) {
            framesD[i - 1] = new Image("meduse" + i + ".png", WIDTH, HEIGHT, false, false);
        }
    }

    public Plateforme getStandingPlatform() {
        return standingPlatform;
    }

    @Override
    public void update(double deltaTemps) {
        // on peut simplement changé ces valeurs pour changer la physique de la méduse
        final double ACCELERATION_X = 1200;
        final double GRAVITY = 1200;
        final double JUMP = -600;
        final double REBOUND_PERCENT_SPEED = 0.5;
        final double COEFFICIENT_FROTTEMENT_PLATFORM = 0.8;
        final double COEFFICIENT_FROTTEMENT_AIR = 0.5;

        //TODO: on pourrait faire que certains types de plateformes aient un coefficient de frottement différent

        boolean left = isKeyPressed(KeyCode.LEFT);
        boolean right = isKeyPressed(KeyCode.RIGHT);
        boolean jump = (isKeyPressed(KeyCode.SPACE) || isKeyPressed(KeyCode.UP));
        standingPlatform = searchStandingPlatform();

        /* Rebondir sur les murs */
        if (getGauche() <= partie.getCamera().getX()) {
            x = 0;
            vx = -vx * REBOUND_PERCENT_SPEED;
        } else if (getDroite() >= partie.getCamera().getRight()) {
            x = partie.getCamera().getRight() - width;
            vx = -vx * REBOUND_PERCENT_SPEED;
        }

        /* Mouvement gauche/droite */
        if (right && !left) {
            ax = ACCELERATION_X;
        } else if (!right && left) {
            ax = -ACCELERATION_X;
        } else {
            double coefficientFrottement = (standingPlatform != null) ? COEFFICIENT_FROTTEMENT_PLATFORM : COEFFICIENT_FROTTEMENT_AIR;
            int signeVitesse = (vx > 0) ? 1 : -1;
            ax = signeVitesse * -(coefficientFrottement * GRAVITY); //F=ma(x) et F=uF(N) et F(N)=-a(y)*m => a(x) = u*(m/m)*-a(y) = -(u * a(y))
            double futureVX = vx + ax * deltaTemps;
            int nouveauSigneVitesse = (futureVX > 0) ? 1 : -1;

            // on est si lent quon devient immobile au lieu daller en direction contraire
            if (nouveauSigneVitesse != signeVitesse) {
                ax = vx = 0;
            }
        }

        /* Saut/plateforme */
        if (standingPlatform != null) {// si collision avec plateforme
            if (jump) {
                vy = JUMP; // si on saute, appliquer la vitesse vers le haut
                standingPlatform.jumpOn();
            } else {
                ay = 0; // si non, il faut rester immobile, sur la plateforme. aka ne pas tomber
                if (vy > 0) { // si vitesse est vers le bas, on la reinitialise a 0.
                    vy = 0;
                }
            }
        } else {
            ay = GRAVITY; // sinon, on applique la gravité
        }

        super.update(deltaTemps);
    }

    private Plateforme searchStandingPlatform() {
        if (vy < 0 && !canJumpWhileJumping) { // on ne peut sauter que si on est immobile en y
            return null;
        }

        //determiner si la meduse est sur une plateforme
        for (Plateforme plat : partie.getPlatManager().getPlateformes()) {
            //?x = v1 * t + (1/2) * a * t²
            double newY = y + vy * partie.getDeltaTime() + 0.5 * ay * partie.getDeltaTime() * partie.getDeltaTime();
            double newX = x + vx * partie.getDeltaTime() + 0.5 * ax * partie.getDeltaTime() * partie.getDeltaTime();

            //https://stackoverflow.com/a/32088787
            //TODO: collision avec les plateformes


            if (plat.getHaut() <= this.getBas() && plat.getBas() > this.getBas() &&
                    plat.getDroite() >= this.getGauche() && plat.getGauche() <= this.getDroite()
            ) {
                y = plat.getHaut() - height;
                plat.landOn();
                return plat;
            }
        }
        return null;
    }

    @Override
    public void draw(GraphicsContext context, long now) {
        /* Dessiner le bon frame */
        boolean left = isKeyPressed(KeyCode.LEFT);
        boolean right = isKeyPressed(KeyCode.RIGHT);

        if (left && !right) { // si on appuie sur la gauche
            ld = LastDirection.LEFT;
        } else if (right && !left) { // si on appuie sur la droite
            ld = LastDirection.RIGHT;
        } else { // right et left sont appuyés OU ni right ni left sont appuyés
            if (vx > 0) ld = LastDirection.RIGHT;
            else if (vx < 0) ld = LastDirection.LEFT;
        }

        //prendre la bonne liste de frame
        Image[] listeFrames = switch (ld) {
            case LEFT -> framesG;
            case RIGHT -> framesD;
        };

        // prendre le bon sprite
        int frame = (int) Math.floor(now * 8 * 1e-9);

        //dessiner le sprite
        context.drawImage(listeFrames[frame % listeFrames.length], xScreen, yScreen);

        if (partie.isDebug()) {
            context.setFill(Color.rgb(255, 0, 0, 0.3));
            context.fillRect(xScreen, yScreen, height, height);
        }
    }
}
