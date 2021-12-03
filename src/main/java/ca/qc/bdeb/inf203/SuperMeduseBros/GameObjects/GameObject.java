package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class GameObject {

    protected double x, y, xScreen, yScreen;
    public double vx=0, vy=0, ax=0, ay=0; //public pour pouvoir modifier facilement dans plateforme rebondissante
    protected double width, height;
    protected Color color;
    protected final Partie partie;

    public GameObject(Partie partie){
        this.partie = partie;
    }

    public GameObject(double x, double y, double width, double height, Partie partie) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.partie = partie;
    }

    public void update(double deltaTemps) {
        vx += ax * deltaTemps;
        vy += ay * deltaTemps;

        x += vx * deltaTemps;
        y += vy * deltaTemps;

        xScreen = partie.getCamera().calculerEcranX(x);
        yScreen = partie.getCamera().calculerEcranY(y);
    }

    public double getHaut() {
        return y;
    }
    public double getBas() {
        return y + height;
    }
    public double getGauche() {
        return x;
    }
    public double getDroite() {
        return x + width;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getXScreen() {
        return xScreen;
    }

    public double getYScreen() {
        return yScreen;
    }

    public abstract void draw(GraphicsContext context, long now);
}
