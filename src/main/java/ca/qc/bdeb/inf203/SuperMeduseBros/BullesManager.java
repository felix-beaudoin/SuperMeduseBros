package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.Camera;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Bulle;
import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;

import java.util.LinkedList;

public class BullesManager {
    private final LinkedList<Bulle> bulles;
    private final Partie partie;
    private final Camera camera;

    BullesManager(Partie partie) {
        this.partie = partie;
        this.camera = partie.getCamera();
        bulles = new LinkedList<>();
    }

    public void spawnBulles(){
        for (int i = 0; i < 3; i++){
            addBulles(createBulle());
        }
    }

    private void addBulles(Bulle[] bullesGroupe) {

        for (int i = 0; i < bullesGroupe.length; i++) {
            bulles.add(bullesGroupe[i]);                             //on ajoute la bulle a la LinkedList (ordered list)
            partie.addGameObject(bullesGroupe[i]);                             //on ajoute la bulle a la partie (unordered list)
        }
    }

    private Bulle[] createBulle() {
        Bulle[] bulles = new Bulle[5];
        double x = Math.random() * partie.getGameWidth();
        for (int i = 0; i < 5; i++) {
            bulles[i] = new Bulle(x, partie);
        }


        return bulles;
    }
}
