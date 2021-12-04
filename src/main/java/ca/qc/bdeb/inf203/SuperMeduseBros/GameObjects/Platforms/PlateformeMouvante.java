package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.paint.Color;

public class PlateformeMouvante extends Plateforme {

    public static final Color COULEUR = Color.rgb(184, 15, 36);

    private double totalTime = 0;
    private double vitesse;
    private final double amplitude;

    public PlateformeMouvante(double x, double y, double width, Partie partie) {
        super(x, y, width, COULEUR , partie);
        vitesse = 0.4 * Math.random() + 0.5;
        amplitude = 2 * Math.random() + 2;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        totalTime += deltaTime;

        x += Math.sin(totalTime * vitesse) * amplitude;

        if (x < partie.getCamera().getLeft()) {
            vitesse = -vitesse;
            x = partie.getCamera().getLeft();
        }else if (x + width > partie.getCamera().getRight()) {
            vitesse = -vitesse;
            x = partie.getCamera().getRight() - width;
        }
    }
}
