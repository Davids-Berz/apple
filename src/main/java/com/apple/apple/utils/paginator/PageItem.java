package com.apple.apple.utils.paginator;

public class PageItem {

    private int numero;
    private boolean current;

    public PageItem(int numero, boolean current) {
        this.numero = numero;
        this.current = current;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isCurrent() {
        return current;
    }
}
