package model.structures;

public class ListaDoble<T> {
    private NodoDoble<T> head;
    private NodoDoble<T> tail;
    private NodoDoble<T> actual;
    private int tamano;

    public ListaDoble() {
        this.head = null;
        this.tail = null;
        this.actual = null;
        this.tamano = 0;
    }

    public void insertarAlInicio(T elemento) {
        NodoDoble<T> nuevo = new NodoDoble<>(elemento);
        if (isEmpty()) {
            head = nuevo;
            tail = nuevo;
            actual = nuevo;
        } else {
            nuevo.setSiguiente(head);
            head.setAnterior(nuevo);
            head = nuevo;
        }
        tamano++;
    }

    public void insertarAlFinal(T elemento) {
        NodoDoble<T> nuevo = new NodoDoble<>(elemento);
        if (isEmpty()) {
            head = nuevo;
            tail = nuevo;
            actual = nuevo;
        } else {
            tail.setSiguiente(nuevo);
            nuevo.setAnterior(tail);
            tail = nuevo;
        }
        tamano++;
    }

    public T eliminar(NodoDoble<T> nodo) {
        if (nodo == null || isEmpty()) {
            return null;
        }
        if (nodo == actual) {
            return retirarActual();
        }
        if (nodo.getAnterior() != null) {
            nodo.getAnterior().setSiguiente(nodo.getSiguiente());
        } else {
            head = nodo.getSiguiente();
        }
        if (nodo.getSiguiente() != null) {
            nodo.getSiguiente().setAnterior(nodo.getAnterior());
        } else {
            tail = nodo.getAnterior();
        }
        tamano--;
        return nodo.getDato();
    }

    public NodoDoble<T> buscar(T dato) {
        NodoDoble<T> temp = head;
        while (temp != null) {
            if (temp.getDato().equals(dato)) {
                return temp;
            }
            temp = temp.getSiguiente();
        }
        return null;
    }

    public NodoDoble<T> getHead() {
        return head;
    }

    public T irAlInicio() {
        actual = head;
        return actual != null ? actual.getDato() : null;
    }

    public T irAlFinal() {
        actual = tail;
        return actual != null ? actual.getDato() : null;
    }

    public T siguiente() {
        if (actual != null && actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
            return actual.getDato();
        }
        return null;
    }

    public T anterior() {
        if (actual != null && actual.getAnterior() != null) {
            actual = actual.getAnterior();
            return actual.getDato();
        }
        return null;
    }

    public T retirarActual() {
        if (actual == null) {
            return null;
        }
        NodoDoble<T> eliminado = actual;
        T dato = eliminado.getDato();

        if (eliminado.getAnterior() != null) {
            eliminado.getAnterior().setSiguiente(eliminado.getSiguiente());
        } else {
            head = eliminado.getSiguiente();
        }

        if (eliminado.getSiguiente() != null) {
            eliminado.getSiguiente().setAnterior(eliminado.getAnterior());
            actual = eliminado.getSiguiente();
        } else {
            tail = eliminado.getAnterior();
            actual = tail;
        }

        tamano--;
        return dato;
    }

    public String recorrerAdelante() {
        StringBuilder sb = new StringBuilder();
        NodoDoble<T> temp = head;
        while (temp != null) {
            sb.append(temp.getDato().toString()).append("\n");
            temp = temp.getSiguiente();
        }
        return sb.toString();
    }

    public String recorrerAtras() {
        StringBuilder sb = new StringBuilder();
        NodoDoble<T> temp = tail;
        while (temp != null) {
            sb.append(temp.getDato().toString()).append("\n");
            temp = temp.getAnterior();
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return tamano == 0;
    }

    public int size() {
        return tamano;
    }
}