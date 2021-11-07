package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateforme extends GameObject {

    //TODO: peut-etre pour creer les plateformes faire une classe ManagerPlateforme ou jsp si tu as une autre idee? A voir!

    //constants
    private static final double HEIGHT = 20;
    private static final double MIN_WIDTH = 80, MAX_WIDTH = 170;
    private static final Color defaultColor = Color.rgb(230, 134, 58);;

    Plateforme(double x, double y, Partie partie) {
        this(x, y, defaultColor, partie);
    }

    Plateforme(double x, double y, Color color, Partie partie) {
        super(x,y, Math.random() * (MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH, HEIGHT, partie);
        this.color = color;
    }

    public void jumpOn(){

    }

    public void landOn(){

    }

    @Override
    public void draw(GraphicsContext context, long now) {
        //TODO: penses tu quon devrait store xScreen et yScreen dans GameObject?
        //TODO: comme ca, pour chaque implementation de Draw on est pas oblige
        //TODO: de recalculer xScreen et yScreen (copie/colle du code a chaque draw()).
        //TODO: on pourrait calculer xScreen et yScreen dans GameObject en faisant un genre
        //TODO: de getters/setters pour xScreen et yScreen?
        //TODO: entk on limplementera en temps et lieux :)

        double xScreen = partie.getCamera().calculerEcranX(x);
        double yScreen = partie.getCamera().calculerEcranY(y);

        context.setFill(color);
        context.fillRect(xScreen, yScreen, width, height);
    }
}
