package edu.prog2;

import java.io.IOException;
import edu.prog2.controllers.*;
import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.*;
import static spark.Spark.*;

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

    after("/*/:param", (req, res) -> {

      if (req.requestMethod().equals("PUT")) {
        String[] path = req.pathInfo().split("/");
        try {
          if (path[1].equals("pasajeros")) {
            reservasService.update();
          } else if (path[1].equals("aviones")) {
            sillasService.update();
            vuelosService.update();
          } else if (path[1].equals("pasajeros")) {
            vuelosService.update();
          }
          reservasVuelosService.update();

        } catch (Exception e) {
          new StandardResponse(res, 404, e);
        }
      }
    });

  }
}