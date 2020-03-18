/*
 * Copyright 2020 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.entities;

import java.awt.Color;
import java.util.Locale;
import org.japo.java.libraries.UtilesGraficos;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class Item implements Comparable<Item> {

    // Valores Predeterminados
    public static final int DEF_ITEM_ID = 0;
    public static final String DEF_ITEM_NOMBRE = "Doméstico";
    public static final double DEF_ITEM_PRECIO = 0.0;
    public static final Color DEF_ITEM_COLOR = Color.WHITE;

    // Expresiones Regulares
    public static final String REG_ITEM_ID = "\\d+";
    public static final String REG_ITEM_NOMBRE = "[ \\p{Alpha}]{3,}";
    public static final String REG_ITEM_PRECIO = "\\d+\\.\\d+";
    public static final String REG_ITEM_COLOR = "rojo|amarillo|verde|blanco";

    // Campos
    private int id;
    private String nombre;
    private double precio;
    private Color color;

    // Constructor Predeterminado
    public Item() {
        id = DEF_ITEM_ID;
        nombre = DEF_ITEM_NOMBRE;
        precio = DEF_ITEM_PRECIO;
        color = DEF_ITEM_COLOR;
    }

    // Constructor Parametrizado
    public Item(int id, String nombre, double precio, Color color) {
        if (UtilesValidacion.validar(id + "", REG_ITEM_ID)) {
            this.id = id;
        } else {
            this.id = DEF_ITEM_ID;
        }

        if (UtilesValidacion.validar(nombre, REG_ITEM_NOMBRE)) {
            this.nombre = nombre;
        } else {
            this.nombre = DEF_ITEM_NOMBRE;
        }

        if (UtilesValidacion.validar(precio + "", REG_ITEM_PRECIO)) {
            this.precio = precio;
        } else {
            this.precio = DEF_ITEM_PRECIO;
        }

        if (UtilesValidacion.validar(UtilesGraficos.obtenerNombreColor(color), REG_ITEM_COLOR)) {
            this.color = color;
        } else {
            this.color = DEF_ITEM_COLOR;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "Item %2d - %-16s - %6.2f€ - %s", getId(), getNombre(), getPrecio(), UtilesGraficos.obtenerNombreColor(getColor()));
    }

    @Override
    public int compareTo(Item o) {
//        return getId() - o.getId();
        return getNombre().compareTo(o.getNombre());
//        return (int) ((precio - o.precio) * 100);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (UtilesValidacion.validar(id + "", REG_ITEM_ID)) {
            this.id = id;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (UtilesValidacion.validar(nombre, REG_ITEM_NOMBRE)) {
            this.nombre = nombre;
        }
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (UtilesValidacion.validar(precio + "", REG_ITEM_PRECIO)) {
            this.precio = precio;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (UtilesValidacion.validar(UtilesGraficos.obtenerNombreColor(color), REG_ITEM_COLOR)) {
            this.color = color;
        }
    }
}
