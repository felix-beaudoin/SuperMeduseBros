package ca.qc.bdeb.inf203.SuperMeduseBros;

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

        // Calcul de la vitesse en y
        velocityY += accelerationY * deltaTemps;

        // Calcul de la nouvelle position en y
        y += velocityY * deltaTemps;

        //prendre la meduse de la partie
        Meduse meduse = partie.getMeduse();

        //si le haut de la meduse est plus haut que 75% de la hauteur de la camera
        //on place la camera en haut de la meduse
        //0.25 = 25% car y = 0 est en haut de l'écran
        //donc y(a partir du haut) + 0.25 * (hauteur de l'écran)
        //est la meme chose que dire y(a partir du bas) - 0.75 * (hauteur de l'écran)
        if (meduse.getHaut() < y + partie.getGameHeight() * 0.25) {
            y = meduse.getHaut() - partie.getGameHeight() * 0.25;
        }
        //si la meduse est completement en dessous de la camera
        else if (calculerEcranY(meduse.getHaut()) > partie.getGameHeight()) {
            partie.defaite();
        }
    }
}
