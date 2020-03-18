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
package org.japo.java.comparators;

import java.util.Comparator;
import org.japo.java.entities.Item;
import org.japo.java.enumerations.Criterio;
import org.japo.java.libraries.UtilesGraficos;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class ComparadorItem implements Comparator<Item> {

    private Criterio orden;

    public ComparadorItem(Criterio orden) {
        if (orden != null) {
            this.orden = orden;
        } else {
            this.orden = Criterio.ID;
        }
    }

    @Override
    public int compare(Item o1, Item o2) {
        // Valor de Comparación
        int comparacion;

        // Criterio Ordenacion > Comparacion
        switch (orden) {
            case ID:
                comparacion = o1.getId() - o2.getId();
                break;
            case NOMBRE:
                comparacion = o1.getNombre().compareTo(o2.getNombre());
                break;
            case PRECIO:
                comparacion = (int) ((o1.getPrecio() - o2.getPrecio()) * 100);
                break;
            case COLOR:
                comparacion = UtilesGraficos.obtenerNombreColor(o1.getColor()).
                        compareTo(UtilesGraficos.obtenerNombreColor(o2.getColor()));
                break;
            default:
                comparacion = o1.getId() - o2.getId();
                break;
        }

        // Devualve Comparacion
        return comparacion;
    }

    public Criterio getOrden() {
        return orden;
    }

    public void setOrden(Criterio orden) {
        if (orden != null) {
            this.orden = orden;
        }
    }

}
