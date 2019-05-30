package br.com.pag.service.order.model;

import java.io.Serializable;

public class Pair<K,V> implements Serializable{

    private K key;

    public K getKey() { return key; }

    private V value;

    public V getValue() { return value; }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
