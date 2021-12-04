package ca.qc.bdeb.inf203.SuperMeduseBros;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private String nom;
    private final int score;

    public Score(int score) {
        this("", score);
    }

    public Score(String nom, int score) {
        this.nom = nom;
        this.score = score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(Score s2){
        return Integer.compare(score, s2.getScore());
    }
}
