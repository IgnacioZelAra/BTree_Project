package Nodo;

public class Nodo {
    int nLlaves;
    int[] llaves;
    Nodo[] hijos;
    boolean hoja;

    public Nodo(int T) {
        this.llaves = new int[2*T -1];
        this.hijos = new Nodo[2*T];
        this.hoja = true;
    }

    public int getnLlaves() {
        return nLlaves;
    }

    public void setnLlaves(int nLlaves) {
        this.nLlaves = nLlaves;
    }

    public int getLlave(int i) {
        return llaves[i];
    }

    public void setLlave(int i, int v) {
        this.llaves[i] = v;
    }

    public Nodo getHijo(int i) {
        return hijos[i];
    }

    public void setHijo(int i, Nodo nd) {
        this.hijos[i] = nd;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }

    public int encontrar(int nLlaves) {
        for (int i = 0; i<this.nLlaves; i++){
            if (this.llaves[i] == nLlaves){
                return i;
            }
        }
        return -1;
    };
}
