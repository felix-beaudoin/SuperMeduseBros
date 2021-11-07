package ca.qc.bdeb.inf203.SuperMeduseBros;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Partie {

    private double gameWidth, gameHeight;
    private Meduse meduse;
    private Camera camera;

    ArrayList<GameObject> gameObjects = new ArrayList<>();
    Partie(double gameWidth, double gameHeight){
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        //start the game
        start();
    }

    private void start(){
        //TODO: peut etre que on ne veut pas recreer une nouvelle camera et meduse quand on restart?
        //TODO: niveau Memoire et Vitesse ca change rien pcq le GC va enlever les anciens gameObjects
        //TODO: du heap memory vu quils seront dereferencé.
        //TODO: BREF, c vrm pas necessaire mais pt c plus clean (jsp frl c plus une question quune affirmation lol)

        //create the camera
        camera = new Camera(this);

        //this section is a test
        meduse = new Meduse(gameWidth/2, Meduse.HEIGHT, this);
        gameObjects.add(meduse);

        //add platforms at random position between 0 and gameWidth and 0 and gameHeight (10 platforms)
        for (int i = 0; i < 10; i++) {
            double platX = Math.random() * gameWidth;
            double platY = Math.random() * gameHeight;
            Plateforme p = new Plateforme(platX, platY, this);
            gameObjects.add(p);
        }
    }

    private void restart(){
        //tmp implentation
        System.out.println("restart");
        gameObjects.clear();
        start();
    }

    public void update(double deltaTemps){

        //update camera
        camera.update(deltaTemps);

        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTemps, gameObjects);
        }
    }

    public void draw(GraphicsContext context, long now){
        context.clearRect(0, 0, gameWidth, gameHeight);
        context.setFill(Color.BLUE);
        context.fillRect(0, 0, gameWidth, gameHeight);

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
     * Cette methode est appele par la camera quand la meduse est en dessous de la camera (on a donc perdu)
     */
    public void defaite() {
        //tmp implentation
        System.out.println("perdu");
        restart();
    }
}
