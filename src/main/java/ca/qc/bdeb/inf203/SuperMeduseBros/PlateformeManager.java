package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Meduse;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms.Plateforme;
import java.util.*;

/*
* Cette classe va faire apparaitre les Plateformes en haut de l'écran.
* Elle va aussi faire disparaitre les plateformes qui sont trop basse.
* */
public class PlateformeManager {
    private final LinkedList<Plateforme> plateformes;
    private final Partie partie;
    private final Camera camera;

    public PlateformeManager(Partie partie) {
        this.partie = partie;
        this.camera = partie.getCamera();
        plateformes = new LinkedList<>();
    }

    public void start(){
        // Créer les plateformes qui seront dans la camera au debut de la partie
        do {
            addPlateforme(createPlateforme());
        } while(plateformes.getLast().getBas() > camera.getTop());
    }

    public void updateManager() {
        // si la plateforme la plus basse est trop basse, on la supprime et on en crée
        // une autre en haut de la plateforme la plus haute
        while(plateformes.getFirst().getHaut() > camera.getBottom() + Meduse.HEIGHT){
            Plateforme platToRemove = plateformes.removeFirst();
            partie.removeGameObject(platToRemove);
            addPlateforme(createPlateforme());
        }
        //PS : c'est dans un while au cas où on va vite... tres vite :)
    }

    private Plateforme createPlateforme(){
        final double DISTANCE_BETWEEN_PLATEFORMES = 100;

        //on ne peut pas mettre x, y dans le constructor car on ne sait pas le width aléatoire
        Plateforme plat = new Plateforme(partie);

        //calcul de x
        double x = camera.getLeft() + Math.random() * (camera.getWidth() - plat.getWidth());

        //calcul du y
        double y;
        if(plateformes.size() == 0){
            y = camera.getBottom(); // spawn en bas de la camera
        }else{
            y = plateformes.getLast().getHaut() - DISTANCE_BETWEEN_PLATEFORMES; //spawn en haut de la plateforme la plus haute
        }

        //on set les coordonnes
        plat.setPosition(x, y);

        return plat;
    }


    public LinkedList<Plateforme> getPlateformes() {
        return plateformes;
    }

    private void addPlateforme(Plateforme plat){
        //on ajoute la plateforme a la LinkedList (ordered list)
        plateformes.add(plat);

        //on ajoute la plateforme a la partie (unordered list)
        partie.addGameObject(plat);
    }
}
