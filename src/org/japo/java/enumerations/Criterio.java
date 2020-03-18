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
package org.japo.java.enumerations;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public enum Criterio {
    NINGUNO("ninguno"),
    ID("id"),
    NOMBRE("nombre"),
    PRECIO("precio"),
    COLOR("color");

    // Camposc
    private final String nombre;

    // Constructor Privado
    private Criterio(String nombre) {
        // Validar Nombre
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
