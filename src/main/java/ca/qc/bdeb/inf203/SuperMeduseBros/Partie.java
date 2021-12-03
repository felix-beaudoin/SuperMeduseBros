package ca.qc.bdeb.inf203.SuperMeduseBros;

import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Bulle;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.GameObject;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Meduse;
import ca.qc.bdeb.inf203.SuperMeduseBros.GameObjects.Platforms.Plateforme;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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
    private boolean debug = false;
    private String positionInfo;
    private String vitesseInfo;
    private String accelerationInfo;
    private String standingOnPlateformInfo;
    private boolean isGameLost = false;
    protected double deltaTime;

    Set<GameObject> gameObjects = new HashSet<>();

    //on veut les bulles spawn en background, donc on peut pas juste les mettre dans le linkedlist gameObject
    LinkedList<Bulle> bulles = new LinkedList<>();

    public Partie(double gameWidth, double gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        //start the game
        start();
    }

    private void start() {
        //create the camera
        camera = new Camera(this);

        //create the plateformes
        platManager = new PlateformeManager(this);
        platManager.start();

        bulleManager = new BulleManager(this);
        bulleManager.spawnBulles();

        //create the méduse
        Plateforme spawnPlat = platManager.getPlateformes().get((int) Math.ceil(platManager.getPlateformes().size() / 2d - 1));
        meduse = new Meduse(
                spawnPlat.getGauche() + spawnPlat.getWidth() / 2 - Meduse.WIDTH / 2,
                spawnPlat.getHaut() - Meduse.HEIGHT - 35, // -35 pour éviter que la méduse ne sorte de la plateforme
                this
        );


        //add the gameObjects
        gameObjects.add(meduse);

    }

    private void restart() {
        //tmp implémentation
        System.out.println("restart");
        gameObjects.clear();
        start();
    }

    public void update(double deltaTemps, long now, long lastTime) {
        deltaTime = deltaTemps;

        //update camera
        camera.update(deltaTemps);

        //update managers
        platManager.updateManager();
        bulleManager.updateManager(now);

        //update gameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTemps);
        }

        //update bulle
        for (Bulle bulle : bulles) {
            bulle.update(deltaTemps);
        }

        if (Input.isKeyPressed(KeyCode.T)){
            debug = !debug;
        }

        updateDebugInfo();

    }

    private void updateDebugInfo(){
        if (isDebug()) {
            positionInfo = ("Position = (" + (int) getMeduse().getX() + ", " + (int) getMeduse().getY() + ")");
            vitesseInfo = ("Vitesse = (" + (int) getMeduse().getVx() + ", " + (int) getMeduse().getVy() + ")");
            accelerationInfo = ("Acceleration = (" + (int) getMeduse().getAx() + ", " + (int) getMeduse().getAy() + ")");
            if (getMeduse().getStandingPlatform() == null) {
                standingOnPlateformInfo = ("Touche le sol? non");
            } else {
                standingOnPlateformInfo = ("Touche le sol? oui");
            }

        } else {
            positionInfo = "";
            vitesseInfo = "";
            accelerationInfo = "";
            standingOnPlateformInfo = "";
        }
    }

    public void draw(GraphicsContext context, long now) {
        context.clearRect(0, 0, gameWidth, gameHeight);
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, gameWidth, gameHeight);

        for (Bulle bulle : bulles) {
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

    public int getScore(){
        return (int) (java.lang.Math.floor(getCamera().getTop() * -1));
    }

    public Camera getCamera() {
        return camera;
    }

    /**
     * Cette méthode est appelée par la camera quand la méduse est en dessous de la camera (on a donc perdu)
     */
    public void defaite() {
        isGameLost = true;
    }




    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void addBulle(Bulle bulle) {
        bulles.add(bulle);
    }

    public boolean isGameLost() {
        return isGameLost;
    }

    public void removeBulle(Bulle bulle){
        bulles.remove(bulle);
    }

    public PlateformeManager getPlatManager() {
        return platManager;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getPositionInfo() {
        return positionInfo;
    }

    public String getVitesseInfo() {
        return vitesseInfo;
    }

    public String getAccelerationInfo() {
        return accelerationInfo;
    }

    public String getStandingOnPlateformInfo() {
        return standingOnPlateformInfo;
    }

    public double getDeltaTime() {
        return deltaTime;
    }
}
