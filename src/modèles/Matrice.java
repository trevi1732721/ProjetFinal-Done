package modèles;

public class Matrice {
    protected double[][] matrice = new double[3][3];

    public double[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(double[][] matrice) {
        this.matrice = matrice;
    }
    public void Matrice(int i,int j){
        matrice = new double[i][j];
    }
}
