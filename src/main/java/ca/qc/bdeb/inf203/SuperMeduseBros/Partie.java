package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Bulle;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.GameObject;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Meduse;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms.Plateforme;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Partie {

    private final double gameWidth, gameHeight;
    private Meduse meduse;
    private Camera camera;
    private PlateformeManager platManager;
    private BulleManager bulleManager;
    private double lastBubbleWave = 0;

    Set<GameObject> gameObjects = new HashSet<>();

    LinkedList<Bulle> bulles = new LinkedList<>();

    public Partie(double gameWidth, double gameHeight){
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        //start the game
        start();
    }

    private void start(){
        //create the camera
        camera = new Camera(this);

        //create the plateformes
        platManager = new PlateformeManager(this);
        platManager.start();

        bulleManager = new BulleManager(this);
        bulleManager.spawnBulles();

        //create the méduse
        Plateforme spawnPlat = platManager.getPlateformes().get((int)Math.ceil(platManager.getPlateformes().size()/2d - 1));
        meduse = new Meduse(
                spawnPlat.getGauche() + spawnPlat.getWidth()/2 - Meduse.WIDTH/2,
                spawnPlat.getHaut() - Meduse.HEIGHT - 35, // -35 pour éviter que la méduse ne sorte de la plateforme
                this
        );



        //add the gameObjects
        gameObjects.add(meduse);

    }

    private void restart(){
        //tmp implémentation
        System.out.println("restart");
        gameObjects.clear();
        start();
    }

    public void update(double deltaTemps, long now, long lastTime){



        //update camera
        camera.update(deltaTemps);

        //update managers
        platManager.updateManager();
        bulleManager.updateManager();

        //update gameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTemps);
        }

        for (Bulle bulle : bulles){
            bulle.update(deltaTemps);
        }


      if ((now * 1e-9) - lastBubbleWave >= 3){// si ça fait 3 secondes min qu'on a pas fait de bulle, faire des bulles
            bulleManager.spawnBulles();
            lastBubbleWave = now * 1e-9;
           }



    }

    public void draw(GraphicsContext context, long now){
        context.clearRect(0, 0, gameWidth, gameHeight);
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, gameWidth, gameHeight);

        for (Bulle bulle : bulles){
            bulle.draw(context, now);
        }

       for (GameObject gameObject : gameObjects) {
            gameObject.draw(context, now);
        }
    }

    public Meduse getMeduse() {
        return meduse;
    }

    public double getGameWidth() {
        return gameWidth;
    }

    public double getGameHeight() {
        return gameHeight;
    }

    public Camera getCamera() {
        return camera;
    }

    /**
     * Cette méthode est appelée par la camera quand la méduse est en dessous de la camera (on a donc perdu)
     */
    public void defaite() {
        //tmp implémentation
        System.out.println("perdu");
        restart();
    }

    public void removeGameObject(GameObject gameObject){
        gameObjects.remove(gameObject);
    }

    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public void addBulle(Bulle bulle){
        bulles.add(bulle);
    }

    public PlateformeManager getPlatManager() {
        return platManager;
    }
}
