package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.Camera;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Bulle;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Meduse;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms.Plateforme;
import ca.qc.bdeb.inf203.SuperMeduseBros.Partie;

import java.util.LinkedList;

public class BulleManager {
    // en codant bullemanager je me rends compte que le code est tres similaire a PlateformeManager, si d'autre managers
    // seraient rajouté, il serait utile de faire une classe mere abstract, mais pour l'instant c'est trop de trouble

    private final LinkedList<Bulle> bulles;
    private final Partie partie;
    private final Camera camera;
    private double lastBubbleWave;

    BulleManager(Partie partie) {
        this.partie = partie;
        this.camera = partie.getCamera();
        bulles = new LinkedList<>();
    }

    public void spawnBulles() {
        for (int i = 0; i < 3; i++) {
            addBulles(createBulle());
        }
    }

    private void addBulles(Bulle[] bullesGroupe) {
        for (int i = 0; i < bullesGroupe.length; i++) {
            bulles.add(bullesGroupe[i]);                             //on ajoute la bulle a la LinkedList (ordered list)
            partie.addBulle(bullesGroupe[i]);                     //on ajoute la bulle a la partie (unordered list)
        }
    }

    private Bulle[] createBulle() {
        Bulle[] bulles = new Bulle[5];
        double x = Math.random() * (partie.getGameWidth() - (10 + 40));
        System.out.println("x: " + x);
        for (int i = 0; i < 5; i++) {
            bulles[i] = new Bulle(x, partie);
        }
        return bulles;
    }

    public void updateManager(long now) {
        if ((now * 1e-9) - lastBubbleWave >= 3){
            spawnBulles();
            lastBubbleWave = now * 1e-9;
        }
        while (bulles.getFirst().getHaut() > camera.getBottom() + Meduse.HEIGHT) {
            Bulle bulleToRemove = bulles.removeFirst();
            partie.removeGameObject(bulleToRemove);
        }
    }
}
