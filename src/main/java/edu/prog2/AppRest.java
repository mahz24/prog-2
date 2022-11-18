package edu.prog2;

import java.io.IOException;
import edu.prog2.controllers.*;
import edu.prog2.services.*;

public class AppRest {
  public static void main(String[] args) throws IOException {
    PasajerosService pasajerosService = new PasajerosService();
    new PasajerosController(pasajerosService);

    TrayectosService trayectosService = new TrayectosService();
    new TrayectosController(trayectosService);

    AvionesService avionesService = new AvionesService();
    new AvionesController(avionesService);

    VuelosService vuelosService = new VuelosService(trayectosService, avionesService);
    new VuelosController(vuelosService);

    SillasService sillasService = new SillasService(avionesService);
    new SillasController(sillasService);

    ReservasService reservasService = new ReservasService(pasajerosService);
    new ReservasController(reservasService);

    ReservasVuelosService reservasVuelosService = new ReservasVuelosService(reservasService, vuelosService,
        sillasService, avionesService, pasajerosService, trayectosService);
    new ReservasVuelosController(reservasVuelosService);
  }
}