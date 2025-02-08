package Principal;

import Negocio.Menu_joed;

public class AutoLavado_joed {
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de gestión del autolavado");
        Menu_joed menu = new Menu_joed();
        menu.mostrarMenu();
    }
}