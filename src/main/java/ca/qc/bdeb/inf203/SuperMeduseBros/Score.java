package ca.qc.bdeb.inf203.SuperMeduseBros;

public class Score {
    private String nom;
    private int score;


    public Score(String nom, int score) {
        this.nom = nom;
        this.score = score;
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }


    public int compareTo(Score s2){
        return Integer.compare(score, s2.getScore());
    }
}
