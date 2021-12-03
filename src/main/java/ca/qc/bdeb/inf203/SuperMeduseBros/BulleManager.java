package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Bulle;

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
            addBulles(createGroupBulle());
        }
    }

    private void addBulles(Bulle... bullesGroupe) {
        for (Bulle bulle : bullesGroupe) {
            bulles.add(bulle);                             //on ajoute la bulle a la LinkedList (ordered list)
            partie.addBulle(bulle);                     //on ajoute la bulle a la partie (unordered list)
        }
    }

    private Bulle[] createGroupBulle() {

        final double distanceFromSides = 30;

        Bulle[] bulles = new Bulle[5];
        double x = camera.getLeft() + distanceFromSides + Math.random() * (camera.getWidth() - distanceFromSides);
        //System.out.println("x: " + x);
        for (int i = 0; i < 5; i++) {
            bulles[i] = new Bulle(x, partie);
        }
        return bulles;
    }

    public void updateManager(long now) {
        //on verifie sil y a des bulles a ajouter
        if ((now * 1e-9) - lastBubbleWave >= 3){
            spawnBulles();
            lastBubbleWave = now * 1e-9;
        }

        //on verifie sil y a des bulles a supprimer
        while (!bulles.isEmpty() && bulles.getFirst().getBas() < camera.getTop()) {
            Bulle bulleToRemove = bulles.removeFirst();
            partie.removeBulle(bulleToRemove);
            //System.out.println("Bulle removed. Bulle left: " + bulles.size()); //TODO: debug
        }
    }
}
