package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Meduse;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms.*;

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

    private Plateforme randomPlateforme(double x, double y, double width){

        final double limitPlatSimple = 1; // de 1 a 0.5
        final double limitPlatMouvante = 0.5; // de 0.5 a 0.3
        final double limitPlatRebondissante = 0.3; // de 0.3 a 0.15
        final double limitPlatEphemere = 0.15; // de 0.15 a 0.00

        double random = Math.random();

        if (random < limitPlatEphemere) {
            return new PlateformeEphemere(x,y,width,partie);
        }
        else if (random < limitPlatRebondissante) {
            return new PlateformeRebondissante(x,y,width,partie);
        }
        else if (random < limitPlatMouvante) {
            return new PlateformeMouvante(x,y,width,partie);
        }
        else {
            return new PlateformeSimple(x,y,width,partie);
        }
    }

    private Plateforme createPlateforme(){
        final double DISTANCE_BETWEEN_PLATEFORMES = 100;
        final double MIN_WIDTH = 80, MAX_WIDTH = 170;

        //calcul de la width aléatoire
        double width = Math.random() * (MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH;

        //calcul de x
        double x = camera.getLeft() + Math.random() * (camera.getWidth() - width);

        //calcul du y
        double y;
        if(plateformes.size() == 0){
            y = camera.getBottom(); // spawn en bas de la camera
        }else{
            y = plateformes.getLast().getHaut() - DISTANCE_BETWEEN_PLATEFORMES; //spawn en haut de la plateforme la plus haute
        }

        return randomPlateforme(x, y, width);
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
