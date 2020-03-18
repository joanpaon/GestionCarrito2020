/*
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
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
package org.japo.java.app;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.japo.java.comparators.ComparadorItem;
import org.japo.java.entities.Item;
import org.japo.java.enumerations.Criterio;
import org.japo.java.libraries.UtilesCarrito;
import org.japo.java.libraries.UtilesEntrada;
import org.japo.java.libraries.UtilesGraficos;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class App {

    // Posiciones Campos CSV
    public static final int DEF_INDICE_ID = 0;
    public static final int DEF_INDICE_NOMBRE = 1;
    public static final int DEF_INDICE_PRECIO = 2;
    public static final int DEF_INDICE_COLOR = 3;

    // Expresiones Regulares Separador CSV
    public static final String REG_CSV_LECT = "\\b*,\\b*";
    public static final String REG_CSV_ESCR = ",";

    // Nombre del Fichero
    public static final String DEF_NOMBRE_FICHERO = "datos.csv";

    // Colección Items
    public static final List<Item> CARRITO = new ArrayList<Item>();
    public static final List<Item> FILTRO = new ArrayList<Item>();

    // Propiedades de la Aplicación
    private Properties prp;

    // Criterios
    private Criterio criOrd = Criterio.NINGUNO;
    private Criterio criFil = Criterio.NINGUNO;

    // Constructor
    public App(Properties prp) {
        this.prp = prp;
    }

    // Lógica de la Aplicación
    public final void launchApp() {
        // Datos arbitrarios
//        CARRITO.add(new Item(3, "Naranja", 1.23, Color.ORANGE));
//        CARRITO.add(new Item(13, "Tomate", 14.95, Color.RED));
//        CARRITO.add(new Item(8, "Ciruela", 3.21, Color.YELLOW));

        // Menú Principal
        procesarMenuPpal();

        // Mensajes de Despedida
        System.out.println("Sesión de Trabajo Finalizada");
        System.out.println("Gracias por utilizar los servicios de JAPO Labs");
        System.out.println("Hasta la próxima");
    }

    // Menú Principal
    private void procesarMenuPpal() {
        // Semáforo Bucle
        boolean salirOK = false;

        // Bucle Menú Principal
        do {
            // Consola + Opciones > Opcion
            char opcion = UtilesEntrada.obtenerOpcion(
                    UtilesCarrito.TXT_MENU_PPAL,
                    UtilesCarrito.TXT_MENU_ERROR,
                    UtilesCarrito.OPC_MENU_PPAL);

            // Separación cosmética
            System.out.println("---");

            // Gestión Opciones
            switch (opcion) {
                case 'i':
                case 'I':
                    procesarMenuItems();
                    break;
                case 'c':
                case 'C':
                    procesarMenuLista();
                    break;
                case 'p':
                case 'P':
                    procesarMenuPersistencia();
                    break;
                case 'x':
                case 'X':
                    salirOK = true;
                    break;
                default:
                    System.out.println("ERROR: Opción NO disponible");
                    System.out.println("---");
            }
        } while (!salirOK);
    }

    // Menú Items
    private void procesarMenuItems() {
        // Semaforo control bucle
        boolean salirOK = false;

        // Bucle Menú
        do {
            // Consola + Opciones > Opcion
            char opcion = UtilesEntrada.obtenerOpcion(
                    UtilesCarrito.TXT_MENU_ITEM,
                    UtilesCarrito.TXT_MENU_ERROR,
                    UtilesCarrito.OPC_MENU_ITEM);

            System.out.println("---");

            switch (opcion) {
                case 'a':
                case 'A':
                    agregarItem();
                    break;
                case 'b':
                case 'B':
                    borrarItem();
                    break;
                case 'c':
                case 'C':
                    consultarItem();
                    break;
                case 'm':
                case 'M':
                    modificarItem();
                    break;
                case 'x':
                case 'X':
                    salirOK = true;
                    break;
                default:
                    System.out.println("ERROR: Opción NO disponible");
                    System.out.println("---");
            }
        } while (!salirOK);
    }

    // Menú contenido
    private void procesarMenuLista() {
        // Semaforo control bucle
        boolean salirOK = false;

        // Bucle Menú
        do {
            // Consola + Opciones > Opcion
            char opcion = UtilesEntrada.obtenerOpcion(
                    UtilesCarrito.TXT_MENU_CONT,
                    UtilesCarrito.TXT_MENU_ERROR,
                    UtilesCarrito.OPC_MENU_CONT);

            System.out.println("---");

            switch (opcion) {
                case 'l':
                case 'L':
                    listarItems();
                    break;
                case 'f':
                case 'F':
                    procesarMenuFiltro();
                    break;
                case 'o':
                case 'O':
                    procesarMenuOrden();
                    break;
                case 'x':
                case 'X':
                    salirOK = true;
                    break;
                default:
                    System.out.println("ERROR: Opción NO disponible");
                    System.out.println("---");
            }
        } while (!salirOK);
    }

    // Menú Ordenación
    private void procesarMenuFiltro() {
        // Semaforo control bucle
        boolean salirOK = false;

        // Bucle Menú
        do {
            // Consola + Opciones > Opcion
            char opcion = UtilesEntrada.obtenerOpcion(
                    UtilesCarrito.TXT_MENU_FILT,
                    UtilesCarrito.TXT_MENU_ERROR,
                    UtilesCarrito.OPC_MENU_FILT);

            // Separador
            System.out.println("---");

            // Registro Criterio
            switch (opcion) {
                case 'd':
                case 'D':
                    desactivarFiltro(true);
                    salirOK = true;
                    break;
                case 'i':
                case 'I':
                    activarFiltroId();
                    salirOK = true;
                    break;
                case 'n':
                case 'N':
                    criFil = Criterio.NOMBRE;
                    activarFiltroNombre();
                    salirOK = true;
                    break;
                case 'p':
                case 'P':
                    criFil = Criterio.PRECIO;
                    activarFiltroPrecio();
                    salirOK = true;
                    break;
                case 'c':
                case 'C':
                    criFil = Criterio.COLOR;
                    activarFiltroColor();
                    salirOK = true;
                    break;
                case 'x':
                case 'X':
                    salirOK = true;
                    break;
                default:
                    System.out.println("ERROR: Opción NO disponible");
                    System.out.println("---");
            }
        } while (!salirOK);

    }

    // Menú Ordenación
    private void procesarMenuOrden() {
        // Semaforo control bucle
        boolean salirOK = false;

        // Bucle Menú
        do {
            // Consola + Opciones > Opcion
            char opcion = UtilesEntrada.obtenerOpcion(
                    UtilesCarrito.TXT_MENU_ORDE,
                    UtilesCarrito.TXT_MENU_ERROR,
                    UtilesCarrito.OPC_MENU_ORDE);

            // Separador
            System.out.println("---");

            // Registro Criterio Ordenacion
            switch (opcion) {
                case 'i':
                case 'I':
                    ordenarItems(Criterio.ID);
                    salirOK = true;
                    break;
                case 'n':
                case 'N':
                    ordenarItems(Criterio.NOMBRE);
                    salirOK = true;
                    break;
                case 'p':
                case 'P':
                    ordenarItems(Criterio.PRECIO);
                    salirOK = true;
                    break;
                case 'c':
                case 'C':
                    ordenarItems(Criterio.COLOR);
                    salirOK = true;
                    break;
                case 'x':
                case 'X':
                    salirOK = true;
                    break;
                default:
                    System.out.println("ERROR: Opción NO disponible");
                    System.out.println("---");
            }
        } while (!salirOK);
    }

    // Item > Colección
    private void agregarItem() {
        // Cabecera
        System.out.println("Inserción de Item");
        System.out.println("=================");

        // Consola > Clave
        int id = UtilesEntrada.leerEntero(
                "Id .....: ",
                "ERROR: Entrada Incorrecta");

        // Comparador de Búsqueda
        Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

        // Ordenacion
        Collections.sort(CARRITO, cmp);

        // Clave de Busqueda
        Item clave = new Item(id, null, 0, null);

        // Proceso de Busqueda
        int posicion = Collections.binarySearch(CARRITO, clave, cmp);

        // Analisis Resultado Busqueda
        if (posicion < 0) {
            // Obtiene Item
            Item i = new Item(id, null, 0, null);

            // Separador
            System.out.println("---");

            // Consola > Nombre
            do {
                i.setNombre(UtilesEntrada.leerTexto("Nombre .: "));
            } while (i.getNombre().equals(Item.DEF_ITEM_NOMBRE));

            // Consola > Precio
            do {
                i.setPrecio(UtilesEntrada.leerReal("Precio .: ", "ERROR: Entrada incorrecta"));
            } while (i.getPrecio() == Item.DEF_ITEM_PRECIO);

            // Consola > Color
            do {
                String nombreColor = UtilesEntrada.leerTexto("Color ..: ");

                Color color = UtilesGraficos.generarColor(nombreColor.toLowerCase());

                i.setColor(color);
            } while (i.getColor().equals(Item.DEF_ITEM_COLOR));

            System.out.println("---");

            // Consola > Datos Nuevo Item
            System.out.println("Item a insertar");
            System.out.println("---------------");
            System.out.printf("Id .....: %d%n", i.getId());
            System.out.printf("Nombre .: %s%n", i.getNombre());
            System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
            System.out.printf("Color ..: %s%n",
                    UtilesGraficos.obtenerNombreColor(i.getColor()));

            // Separador
            System.out.println("---");

            // Confirmar Proceso
            if (UtilesEntrada.confirmarProceso("Confirmar Inserción (S/n) ...: ", true)) {
                // Insercion Ítem
                CARRITO.add(i);

                // Mensaje Informacivo
                UtilesEntrada.hacerPausa("Ítem INSERTADO correctamente");
            } else {
                // Mensaje Informacivo
                UtilesEntrada.hacerPausa("Inserción de ítem CANCELADA");
            }
        } else {
            UtilesEntrada.hacerPausa("ERROR: Ítem ya existe");
        }
    }

    // Item X Colección
    private void borrarItem() {
        // Cabecera
        System.out.println("Borrado de Item");
        System.out.println("===============");

        // Consola > Clave
        int id = UtilesEntrada.leerEntero(
                "Id .....: ",
                "ERROR: Entrada Incorrecta");

        // Comparador de Búsqueda
        Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

        // Ordenacion
        Collections.sort(CARRITO, cmp);

        // Clave de Busqueda
        Item clave = new Item(id, null, 0, null);

        // Localización
        int posicion = Collections.binarySearch(CARRITO, clave, cmp);

        if (posicion < 0) {
            UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
        } else {
            // Obtiene Item
            Item i = CARRITO.get(posicion);

            // Separador
            System.out.println("---");

            // Muestra Datos
            System.out.println("Item Seleccionado");
            System.out.println("-----------------");
            System.out.printf("Id .....: %d%n", i.getId());
            System.out.printf("Nombre .: %s%n", i.getNombre());
            System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
            System.out.printf("Color ..: %s%n",
                    UtilesGraficos.obtenerNombreColor(i.getColor()));

            // Separador
            System.out.println("---");

            // Confirmar Proceso
            if (UtilesEntrada.confirmarProceso("Confirmar Borrado (s/N) ...: ", false)) {
                // Eliminación Ítem
                CARRITO.remove(posicion);

                // Mensaje Informacivo
                UtilesEntrada.hacerPausa("Ítem BORRADO correctamente");
            } else {
                // Mensaje Informacivo
                UtilesEntrada.hacerPausa("Eliminación de ítem CANCELADO");
            }
        }
    }

    // Colección > Item
    private void consultarItem() {
        // Cabecera
        System.out.println("Consultar Item");
        System.out.println("==============");

        // Consola > Clave
        int id = UtilesEntrada.leerEntero(
                "Id .....: ",
                "ERROR: Entrada Incorrecta");

        // Comparador de Búsqueda
        Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

        // Ordenacion
        Collections.sort(CARRITO, cmp);

        // Clave de Busqueda
        Item clave = new Item(id, null, 0, null);

        // Localización
        int posicion = Collections.binarySearch(CARRITO, clave, cmp);

        if (posicion < 0) {
            UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
        } else {
            // Obtiene Item
            Item i = CARRITO.get(posicion);

            // Separador
            System.out.println("---");

            // Muestra Datos
            System.out.println("Item Seleccionado");
            System.out.println("-----------------");
            System.out.printf("Id .....: %d%n", i.getId());
            System.out.printf("Nombre .: %s%n", i.getNombre());
            System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
            System.out.printf("Color ..: %s%n",
                    UtilesGraficos.obtenerNombreColor(i.getColor()));

            // Mensaje Informacivo
            UtilesEntrada.hacerPausa();
        }
    }

    // Colección > Item > Colección
    private void modificarItem() {
        // Cabecera
        System.out.println("Modificación de Item");
        System.out.println("====================");

        // Consola > Clave
        int id = UtilesEntrada.leerEntero(
                "Id .....: ",
                "ERROR: Entrada Incorrecta");

        // Comparador de Búsqueda
        Comparator<Item> cmp = new ComparadorItem(Criterio.ID);

        // Ordenacion
        Collections.sort(CARRITO, cmp);

        // Clave de Busqueda
        Item clave = new Item(id, null, 0, null);

        // Localización
        int posicion = Collections.binarySearch(CARRITO, clave, cmp);

        if (posicion < 0) {
            UtilesEntrada.hacerPausa("ERROR: Ítem NO encontrado");
        } else {
            // Obtiene Item
            Item i = CARRITO.get(posicion);

            // Separador
            System.out.println("---");

            // Muestra Datos
            System.out.println("Item Seleccionado");
            System.out.println("-----------------");
            System.out.printf("Id .....: %d%n", i.getId());
            System.out.printf("Nombre .: %s%n", i.getNombre());
            System.out.printf(Locale.ENGLISH, "Precio .: %.2f€%n", i.getPrecio());
            System.out.printf("Color ..: %s%n",
                    UtilesGraficos.obtenerNombreColor(i.getColor()));

            // Separador
            System.out.println("---");

            // Confirmar Proceso
            if (UtilesEntrada.confirmarProceso("Modificar ítem (s/N) ...: ", false)) {
                // Separador
                System.out.println("---");

                // Item Vacío
                Item aux = new Item(id, null, 0, null);

                // Item Modificado
                System.out.println("Item Modificado");
                System.out.println("---------------");

                // Consola > Nombre
                do {
                    aux.setNombre(UtilesEntrada.leerTexto("Nombre .: "));
                } while (aux.getNombre().equals(Item.DEF_ITEM_NOMBRE));

                // Consola > Precio
                do {
                    aux.setPrecio(UtilesEntrada.leerReal("Precio .: ", "ERROR: Entrada incorrecta"));
                } while (aux.getPrecio() == Item.DEF_ITEM_PRECIO);

                // Consola > Color
                do {
                    String nombreColor = UtilesEntrada.leerTexto("Color ..: ");

                    Color color = UtilesGraficos.generarColor(nombreColor.toLowerCase());

                    aux.setColor(color);
                } while (aux.getColor().equals(Item.DEF_ITEM_COLOR));

                // Separador
                System.out.println("---");

                // Confirmar Sustitución
                if (UtilesEntrada.confirmarProceso("Guardar cambios (s/N) ...: ", false)) {
                    // Sustitución Item
                    CARRITO.set(posicion, aux);

                    // Mensaje Informacivo
                    UtilesEntrada.hacerPausa("Ítem MODIFICADO correctamente");
                } else {
                    // Mensaje Informacivo
                    UtilesEntrada.hacerPausa("Modificación de ítem CANCELADA");
                }
            } else {
                // Mensaje Informacivo
                UtilesEntrada.hacerPausa("Modificación de ítem CANCELADA");
            }
        }
    }

    // Lista > Listado Consola
    private void listarItems() {
        // Cabecera
        System.out.println("Listado de Items");
        System.out.println("================");

        // Criterio de Ordenación/Filtrado
        System.out.printf("Criterio de Ordenación .: %S%n", criOrd.getNombre());
        System.out.printf("Criterio de Filtrado ...: %S%n", criFil.getNombre());

        // Separados
        System.out.println("---");

        // Filtrado > Selección Colección
        List<Item> lista = criFil.equals(Criterio.NINGUNO) ? CARRITO : FILTRO;

        // Recorrido Colección
        for (Item item : lista) {
            System.out.println(item.toString());
        }

        // Pausai
        UtilesEntrada.hacerPausa();
    }

    // Lista > Ordenada por ID
    private void ordenarItems(Criterio criOrd) {
        // Ordenación > Desactivación Filtro
        desactivarFiltro(false);

        // Registra Criterio Ordenación
        this.criOrd = criOrd;

        // Ordenación Colección
        Collections.sort(CARRITO, new ComparadorItem(criOrd));

        // Mensaje
        System.out.printf("Items ordenados por %s%n", criOrd.getNombre());

        // Pausa
        UtilesEntrada.hacerPausa();
    }

    private void desactivarFiltro(boolean pausaOK) {
        // Registra Estado Filtro
        criFil = Criterio.NINGUNO;

        // Vacia Filtro
        FILTRO.clear();

        // Mensaje Informativo
        if (pausaOK) {
            UtilesEntrada.hacerPausa("Filtro DESACTIVADO");
        } else {
            System.out.println("Filtro DESACTIVADO");
        }
    }

    // Filtro ID
    private void activarFiltroId() {
        System.out.println("Activación Filtro ID");
        System.out.println("====================");

        // Registrar Filtro
        criFil = Criterio.ID;

        // Establecer Rango
        int idMin = UtilesEntrada.leerEntero("Id mínima ....: ",
                "ERROR: Entrada incorrecta", 0, Integer.MAX_VALUE);
        int idMax = UtilesEntrada.leerEntero("Id máxima ....: ",
                "ERROR: Entrada incorrecta", idMin, Integer.MAX_VALUE);

        // Vaciar Filtro
        FILTRO.clear();

        // Filtrado de Datos
        for (Item item : CARRITO) {
            if (item.getId() >= idMin && item.getId() <= idMax) {
                FILTRO.add(item);
            }
        }

        // Mensaje Informativo
        UtilesEntrada.hacerPausa("Filtro pod ID establecido");
    }

    private void activarFiltroNombre() {
        System.out.println("Activación Filtro NOMBRE");
        System.out.println("========================");

        // Registrar Filtro
        criFil = Criterio.NOMBRE;

        // Establecer Patrón
        String nombre = UtilesEntrada.leerTexto("Patrón Nombre ...: ");

        // Expresion Regular
        String patron = String.format(".*%s.*", nombre);

        // Vaciar Filtro
        FILTRO.clear();

        // Filtrado de Datos
        for (Item item : CARRITO) {
            if (UtilesValidacion.validar(item.getNombre(), patron)) {
                FILTRO.add(item);
            }
        }

        // Mensaje Informativo
        UtilesEntrada.hacerPausa("Filtro por NOMBRE establecido");
    }

    private void activarFiltroPrecio() {
        System.out.println("Activación Filtro PRECIO");
        System.out.println("========================");

        // Registrar Filtro
        criFil = Criterio.PRECIO;

        // Establecer Rango
        double precioMin = UtilesEntrada.leerReal("Precio mínimo .: ",
                "ERROR: Entrada incorrecta", 0, Double.MAX_VALUE);
        double precioMax = UtilesEntrada.leerReal("Precio máximo .: ",
                "ERROR: Entrada incorrecta", precioMin, Double.MAX_VALUE);

        // Vaciar Filtro
        FILTRO.clear();

        // Filtrado de Datos
        for (Item item : CARRITO) {
            if (item.getPrecio() >= precioMin && item.getPrecio() <= precioMax) {
                FILTRO.add(item);
            }
        }

        // Mensaje Informativo
        UtilesEntrada.hacerPausa("Filtro por PRECIO establecido");
    }

    private void activarFiltroColor() {
        System.out.println("Activación Filtro COLOR");
        System.out.println("=======================");

        // Registrar Filtro
        criFil = Criterio.COLOR;

        // Establecer Patrón
        String color = UtilesEntrada.leerTexto("Patrón Color ...: ");

        // Expresion Regular
        String patron = String.format(".*%s.*", color);

        // Vaciar Filtro
        FILTRO.clear();

        // Filtrado de Datos
        for (Item item : CARRITO) {
            if (UtilesValidacion.validar(UtilesGraficos.
                    obtenerNombreColor(item.getColor()), patron)) {
                FILTRO.add(item);
            }
        }

        // Mensaje Informativo
        UtilesEntrada.hacerPausa("Filtro por COLOR establecido");
    }

    // Menú Entrada / Salida
    private void procesarMenuPersistencia() {
        // Semaforo control bucle
        boolean salirOK = false;

        // Bucle Menú
        do {
            // Consola + Opciones > Opcion
            char opcion = UtilesEntrada.obtenerOpcion(
                    UtilesCarrito.TXT_MENU_PERS,
                    UtilesCarrito.TXT_MENU_ERROR,
                    UtilesCarrito.OPC_MENU_PERS);

            // Separador
            System.out.println("---");

            // Registro Criterio Ordenacion
            switch (opcion) {
                case 'i':
                case 'I':
                    importarDatos();
                    salirOK = true;
                    break;
                case 'e':
                case 'E':
                    exportarDatos();
                    salirOK = true;
                    break;
                case 'x':
                case 'X':
                    salirOK = true;
                    break;
                default:
                    System.out.println("ERROR: Opción NO disponible");
                    System.out.println("---");
            }
        } while (!salirOK);
    }

    // Fichero CSV > CARRITO
    private void importarDatos() {
        // Cabecera
        System.out.println("Importación de Datos");
        System.out.println("====================");

        // Acceso al Fichero
        try (
                FileReader fr = new FileReader(DEF_NOMBRE_FICHERO);
                BufferedReader br = new BufferedReader(fr)) {
            // Colección Auxiliar
            final List<Item> AUX = new ArrayList<>();

            // Bucle de Lectura
            boolean lecturaOK = true;
            do {
                // Lectura Linea Actual
                String linea = br.readLine();

                // Procesar Lectura
                if (linea != null) {
                    // String > Array
                    String[] items = linea.split(REG_CSV_LECT);

                    // Campo 0 - id ( int )
                    int id = Integer.parseInt(items[DEF_INDICE_ID]);

                    // Campo 1 - nombre ( String )
                    String nombre = items[DEF_INDICE_NOMBRE].trim();

                    // Campo 2 - precio ( double )
                    double precio = Double.parseDouble(items[DEF_INDICE_PRECIO].trim());

                    // Campo 3 - color ( Color )
                    Color color = UtilesGraficos.generarColor(items[DEF_INDICE_COLOR].trim());

                    // Generar Nuevo Item
                    Item item = new Item(id, nombre, precio, color);

                    // Item > Carrito
                    AUX.add(item);
//                    System.out.println("Importado: " + item);
                } else {
                    lecturaOK = false;
                }
            } while (lecturaOK);

            // Vaciar Carrito
            CARRITO.clear();

            // AUX > CARRITO
            CARRITO.addAll(AUX);

            // Mensaje Informativo
            UtilesEntrada.hacerPausa("Datos importados correctamente");
        } catch (NumberFormatException | NullPointerException e) {
            // Mensaje Informativo
            UtilesEntrada.hacerPausa("ERROR: Formato de datos incorrecto");

            // Vaciado Carrito
            CARRITO.clear();
        } catch (IOException e) {
            // Mensaje Informativo
            UtilesEntrada.hacerPausa("ERROR: Acceso al fichero");
        }
    }

    // CARRITO > Fichero CSV
    private void exportarDatos() {
        // Cabecera
        System.out.println("Exportación de Datos");
        System.out.println("====================");

        // Acceso al Fichero
        try (PrintWriter pr = new PrintWriter(DEF_NOMBRE_FICHERO)) {
            // Bucle de Escritura
            for (Item item : CARRITO) {
                // Generar Linea de Texto
                String linea = String.format(Locale.ENGLISH, "%d%s%s%s%.2f%s%s",
                        item.getId(), REG_CSV_ESCR,
                        item.getNombre(), REG_CSV_ESCR,
                        item.getPrecio(), REG_CSV_ESCR,
                        UtilesGraficos.obtenerNombreColor(item.getColor()));

                // Item > Fichero
                pr.println(linea);
            }

            // Mensaje Informativo
            UtilesEntrada.hacerPausa("Datos exportados correctamente");
        } catch (NumberFormatException | NullPointerException e) {
            // Mensaje Informativo
            UtilesEntrada.hacerPausa("ERROR: Formato de datos incorrecto");

            // Vaciado Carrito
            CARRITO.clear();
        } catch (IOException e) {
            // Mensaje Informativo
            UtilesEntrada.hacerPausa("ERROR: Acceso al fichero");
        }
    }
}
