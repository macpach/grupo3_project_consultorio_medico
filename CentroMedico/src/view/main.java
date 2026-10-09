package view;

import model.structures.Pila;

public class main {
    public static void main(String[] args) {
        Pila<String> pila = new Pila<>();
        pila.push("A");
        pila.push("B");
        pila.push("C");

        System.out.println(pila.peek());    // C
        System.out.println(pila.pop());     // C
        System.out.println(pila.pop());     // B
        System.out.println(pila.pop());     // A
        System.out.println(pila.isEmpty()); // true

        try {
            pila.pop();                     // cuarto pop
            System.out.println("ERROR: debió lanzar excepción");
        } catch (IllegalStateException e) {
            System.out.println("OK: " + e.getMessage());
        }

        try {
            pila.push(null);
            System.out.println("ERROR: debió lanzar excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: " + e.getMessage() + " (size = " + pila.size() + ")");
        }
    }
}
