package ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms;

import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;
import javafx.scene.paint.Color;

/**
 * Plate-forme simple (orange)
 * La plate-forme simple est une plate-forme qu’on peut traverser depuis le bas mais qui sert de plancher
 * lorsqu’on tombe dessus.
 * Utilisez la couleur Color.rgb(230, 134, 58) pour afficher ces plates-formes.
 */
public class PlateformeSimple extends Plateforme {

    public static final Color COULEUR = Color.rgb(230, 134, 58);

    /**
     * Constructeur de la plate-forme simple
     * @param x Position en x de la plate-forme
     * @param y Position en y de la plate-forme
     * @param width Largeur de la plate-forme
     * @param partie Partie dans laquelle la plate-forme est créée
     */
    public PlateformeSimple(double x, double y, double width, Partie partie) {
        super(x, y,width, COULEUR, partie);
    }
}
