package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.paint.Color;

public class PlateformeEphemere extends Plateforme {

    public static final Color COULEUR = Color.BLACK;

    public PlateformeEphemere(double x, double y, double width, Partie partie) {
        super(x, y, width, COULEUR, partie);
    }

    @Override
    public void jumpOn() {
        this.vy = 200;
    }
}
