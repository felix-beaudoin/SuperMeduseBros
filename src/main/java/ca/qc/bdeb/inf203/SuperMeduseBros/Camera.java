package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Meduse;

public class Camera {

    private double x = 0;
    private double y = 0;
    private double velocityY = 0; // px/sec
    private final Partie partie;


    public Camera(Partie partie) {
        this.partie = partie;
    }

    public double calculerEcranX(double xMonde) {
        return xMonde - x;
    }

    public double calculerEcranY(double yMonde) {
        return yMonde - y;
    }

    public double getHeight() {
        return partie.getGameHeight();
    }

    public double getWidth() {
        return partie.getGameWidth();
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update(double deltaTemps) {
        final double accelerationY = -2; //- en y = la camera monte = le reste va vers le bas
        final double cameraFollowMedusaHeightPercent = 0.75; // % de la hauteur à laquelle la camera va suivre la méduse

        // Calcul de la vitesse en y
        if (!partie.isDebug()) {
            velocityY += accelerationY * deltaTemps;

            // Calcul de la nouvelle position en y
            y += velocityY * deltaTemps;
        }
        //prendre la méduse de la partie
        Meduse meduse = partie.getMeduse();

        //si le haut de la méduse est plus haut que 75% de la hauteur de la camera
        //on place la camera en haut de la méduse
        double heightOfFollowPoint = getHeight() * (1-cameraFollowMedusaHeightPercent);
        if (meduse.getHaut() < y + heightOfFollowPoint) {
            y = meduse.getHaut() - heightOfFollowPoint;
        }
        //si la méduse est complètement en dessous de la camera
        else if (meduse.getYScreen() > getHeight()) {
            partie.defaite();
        }
    }

    public double getBottom() {
        return y + getHeight();
    }

    public double getTop() {
        return y;
    }

    public double getLeft() {
        return x;
    }

    public double getRight() {
        return x + getWidth();
    }
}
