import BTree.BTree;

import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Scanner intInput = new Scanner(System.in);
    public static void main(String[] args) {
        BTree bArbol = new BTree(3);
        boolean seguir = true;
        while (seguir){
            System.out.print("Elija una opción"+"\n");
            System.out.print("1. Insertar un valor"+"\n");
            System.out.print("2. Mostrar árbol"+"\n");
            System.out.print("3. Eliminar un valor"+"\n");
            System.out.print("Presione cualquier otra tecla para salir"+"\n");

            String opcion = input.nextLine();

            switch (opcion){
                case "1":
                    System.out.print("Escriba el valor: "+"\n");
                    int v1 = intInput.nextInt();
                    bArbol.insertarValor(v1);
                    break;
                case "2":
                    System.out.print("Arbol: "+"\n");
                    bArbol.MostrarArbol();
                    System.out.print("Fin"+"\n");
                    break;
                case "3":
                    System.out.print("Escriba el valor que quiere eliminar: "+"\n");
                    int v2 = intInput.nextInt();
                    bArbol.Remover(v2);
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
}