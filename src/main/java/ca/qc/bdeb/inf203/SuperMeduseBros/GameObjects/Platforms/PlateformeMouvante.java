package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.paint.Color;

public class PlateformeMouvante extends Plateforme {

    public static final Color COULEUR = Color.rgb(184, 15, 36);

    private double totalTime = 0;

    public PlateformeMouvante(double x, double y, double width, Partie partie) {
        super(x, y, width, COULEUR , partie);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        totalTime += deltaTime;

        final double amplitude = 100;
        final double vitesse = 5;

        x += Math.sin(totalTime * vitesse) * amplitude;
    }
}
