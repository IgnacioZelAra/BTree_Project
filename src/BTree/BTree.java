package BTree;

import Nodo.Nodo;

public class BTree {
    private int T;

    private Nodo raiz;

    public BTree(int t) {
        T = t;
        raiz = new Nodo(T);
        raiz.setnLlaves(0);
        raiz.setHoja(true);
    }

    private Nodo encontrarLlave(Nodo nd, int llave){
        int i = 0;
        if (nd == null){
            return nd;
        }

        for (i = 0; i<nd.getnLlaves(); i++){
            if (llave < nd.getLlave(i)){
                break;
            }
            if (llave == nd.getLlave(i)){
                return nd;
            }
        }

        if (nd.isHoja()){
            return null;
        }else{
            return encontrarLlave(nd.getHijo(i), llave);
        }
    }

    private void parteNodo(Nodo nd1, int pos, Nodo nd2){
        Nodo aux = new Nodo(T);
        aux.setHoja(nd2.isHoja());
        aux.setnLlaves(T-1);

        for (int j = 0; j<T-1; j++){
            aux.setLlave(0, nd2.getLlave(j + T));
        }

        if (!nd2.isHoja()){
            for (int j = 0; j < T; j++){
                aux.setHijo(j, nd2.getHijo(j+T));
            }
        }

        nd2.setnLlaves(T-1);

        for (int j = nd1.getnLlaves(); j>= pos +1; j--){
            nd1.setHijo(j+1, nd1.getHijo(j));
        }

        nd1.setHijo(pos+1, aux);

        for (int j = nd1.getnLlaves()-1; j>= pos; j--){
            nd1.setLlave(j+1, nd1.getLlave(j));
        }

        nd1.setLlave(pos, nd2.getLlave(T-1));
        nd1.setnLlaves(nd1.getnLlaves()+1);
    }

    public void insertarValor(final int llave){
        Nodo r = raiz;
        if (r.getnLlaves() == 2 * T - 1){
            Nodo aux = new Nodo(T);
            raiz = aux;
            aux.setHoja(false);
            aux.setnLlaves(0);
            aux.setHijo(0, r);
            parteNodo(aux, 0, r);
            insertarNodo(aux, llave);
        }else{
            insertarNodo(r, llave);
        }
    }

    private void insertarNodo(Nodo nd, int llaves){
        if (nd.isHoja()){
            int i = 0;

            for (i = nd.getnLlaves()-1; i> 0 && llaves < nd.getLlave(i); i--){
                nd.setLlave(i+1, nd.getLlave(i));
            }

            nd.setLlave(i + 1, llaves);
            nd.setnLlaves(nd.getnLlaves()+1);
        }else{
            int i = 0;

            for (i = nd.getnLlaves() - 1; i >= 0 && llaves < nd.getLlave(i); i--) {
            }

            i++;
            Nodo temp = nd.getHijo(i);

            if (temp.getnLlaves() == 2*T-1){
                parteNodo(nd, i, temp);
                if (llaves > nd.getLlave(i)){
                    i++;
                }
            }

            insertarNodo(nd.getHijo(i), llaves);
        }
    }

    private void Remover(Nodo n, int llave){
        int pos = n.encontrar(llave);
        if (pos != -1){
            if (n.isHoja()){
                int i = 0;
                for (i = 0; i<n.getnLlaves() && n.getLlave(i) != llave; i++){

                }

                for (; i < n.getnLlaves(); i++){
                    if(i != 2 * T-2){
                        n.setLlave(i, n.getLlave(i+1));
                    }
                }
                int templlaves = n.getnLlaves();
                templlaves--;
                n.setnLlaves(templlaves);
                return;
            }
            if (!n.isHoja()){
                Nodo pred = n.getHijo(pos);
                int predLlave = 0;
                if (pred.getnLlaves() >= 1){
                    for (;;){
                        if (pred.isHoja()){
                            System.out.println(pred.getnLlaves());
                            predLlave = pred.getLlave(pred.getnLlaves() -1);
                            break;
                        }else{
                            pred = pred.getHijo(pred.getnLlaves());
                        }
                    }
                    Remover(pred, predLlave);
                    n.setLlave(pos, predLlave);
                    return;
                }
                Nodo sgteNodo = n.getHijo(pos + 1);
                if (sgteNodo.getnLlaves() >= T){
                    int sgteLlave = sgteNodo.getLlave(0);
                    if (!sgteNodo.isHoja()){
                        sgteNodo = sgteNodo.getHijo(0);
                        for(;;){
                            if (sgteNodo.isHoja()){
                                sgteLlave = sgteNodo.getLlave(sgteNodo.getnLlaves()-1);
                                break;
                            }else{
                                sgteNodo = sgteNodo.getHijo(sgteNodo.getnLlaves());
                            }
                        }
                    }
                    Remover(sgteNodo, sgteLlave);
                    n.setLlave(pos, sgteLlave);
                    return;
                }

                int temp = pred.getnLlaves() + 1;
                int in = pred.getnLlaves();
                in++;
                pred.setLlave(in, n.getLlave(pos));
                int it = pred.getnLlaves();
                for (int i = 0, j = pred.getnLlaves(); i < sgteNodo.getnLlaves(); i++){
                    pred.setLlave(pred.getLlave(j++), sgteNodo.getLlave(i));
                    it++;
                    pred.setnLlaves(it);
                }
                for (int i = 0; i < sgteNodo.getnLlaves()+1; i++){
                    pred.setHijo(temp++, sgteNodo.getHijo(i));
                }

                n.setHijo(pos, pred);
                for (int i = pos; i < n.getnLlaves(); i++){
                    if (i != 2 * T - 2){
                        n.setLlave(i, n.getLlave(i + 1));
                    }
                }
                for(int i = pos + 1; i < n.getnLlaves() + 1; i++){
                    if (i != 2 * T - 1){
                        n.setHijo(i, n.getHijo(i + 1));
                    }
                }
                int a = n.getnLlaves();
                a--;
                n.setnLlaves(a);
                if (n.getnLlaves() == 0) {
                    if (n == raiz){
                        raiz = n.getHijo(0);
                    }
                    n = n.getHijo(0);
                }
                Remover(pred, llave);
                return;
            }
        }else{
            for (pos = 0; pos < n.getnLlaves(); pos++){
                if (n.getLlave(pos) > llave){
                    break;
                }
            }
            Nodo tmp = n.getHijo(pos);
            if (tmp.getnLlaves() >= T){
                Remover(tmp, llave);
                return;
            }
            if (true){
                Nodo nb = null;
                int devdr = -1;

                if (pos != n.getnLlaves() && nb.getHijo(pos+1).getnLlaves() >= T){
                    devdr = n.getLlave(pos);
                    nb = n.getHijo(pos + 1);
                    n.setLlave(pos, nb.getLlave(0));
                    int b =tmp.getnLlaves();
                    tmp.setLlave(b++, devdr);
                    tmp.setHijo(tmp.getnLlaves(), nb.getHijo(0));
                    for (int i = 1; i < nb.getnLlaves(); i++){
                        nb.setLlave(i - 1, nb.getLlave(i));
                    }
                    for (int i = 1; i <= nb.getnLlaves(); i++){
                        nb.setHijo(i - 1, nb.getHijo(i));
                    }
                    int c = nb.getnLlaves();;
                    c--;
                    nb.setnLlaves(c);
                    Remover(tmp, llave);
                    return;
                } else if (pos != 0 && n.getHijo(pos - 1).getnLlaves() >= T) {
                    devdr = n.getLlave(pos - 1);
                    nb = n.getHijo(pos - 1);
                    n.setLlave(pos - 1, nb.getLlave(nb.getnLlaves() - 1));
                    Nodo hijo = nb.getHijo(nb.getnLlaves());
                    int d = nb.getnLlaves();
                    d--;
                    nb.setnLlaves(d);

                    for (int i = tmp.getnLlaves(); i > 0; i--){
                        tmp.setLlave(i, tmp.getLlave(i - 1));
                    }
                    tmp.setLlave(0, devdr);
                    for (int i = tmp.getnLlaves() + 1; i > 0; i--){
                        tmp.setHijo(i, tmp.getHijo(i - 1));
                    }
                    tmp.setHijo(0, hijo);
                    int e = tmp.getnLlaves();
                    e++;
                    tmp.setnLlaves(e);
                    Remover(tmp, llave);
                    return;
                }else{
                    Nodo izq = null;
                    Nodo der = null;
                    boolean ultimo = false;
                    if (pos != n.getnLlaves()){
                        devdr = n.getLlave(pos);
                        izq = n.getHijo(pos);
                        der = n.getHijo(pos + 1);
                    }else {
                        devdr = n.getLlave(pos - 1);
                        der = n.getHijo(pos);
                        izq = n.getHijo(pos - 1);
                        ultimo = true;
                        pos--;
                    }
                    for (int i = pos; i < n.getnLlaves() - 1; i++){
                        n.setLlave(i, n.getLlave(i + 1));
                    }
                    for (int i = pos + 1; i < n.getnLlaves(); i++){
                        n.setHijo(i, n.getHijo(i + 1));
                    }
                    int f = n.getnLlaves();
                    f--;
                    n.setnLlaves(f);
                    int g = izq.getnLlaves();
                    g++;
                    izq.setnLlaves(g);
                    izq.setLlave(g, devdr);

                    for (int i = 0, j = izq.getnLlaves(); i < der.getnLlaves() + 1; i++, j++){
                        if (i < der.getnLlaves()){
                            izq.setLlave(j, der.getLlave(i));
                        }
                        izq.setHijo(j, der.getHijo(i));
                    }
                    izq.setnLlaves(izq.getnLlaves()+der.getnLlaves());
                    if (n.getnLlaves() == 0){
                        if (n == raiz){
                            raiz = n.getHijo(0);
                        }
                        n = n.getHijo(0);
                    }
                    Remover(izq, llave);
                    return;
                }
            }
        }
    }

    public void Remover(int llave){
        Nodo x = encontrarLlave(raiz, llave);
        if (x == null){
            return;
        }
        Remover(raiz, llave);
    }

    public void MostrarArbol(){
        MostrarArbol(raiz);
    }

    private void MostrarArbol(Nodo nd){
        assert (nd == null);
        for (int i = 0; i<nd.getnLlaves(); i++){
            System.out.printf(nd.getLlave(i)+"-");
        }
        if (!nd.isHoja()){
            for (int i = 0; i < nd.getnLlaves()+1; i++){
                MostrarArbol(nd.getHijo(i));
            }
        }
    }
}
