package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.PasajerosService;
import edu.prog2.models.Pasajero;

public class PasajerosController {

  public PasajerosController(final PasajerosService pasajerosService) {

    path("/pasajeros", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(200, "ok", pasajerosService.getJSON());
        } catch (Exception exception) {
          res.status(404);
          return new StandardResponse(404, exception);
        }

      });

      get("/:id", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String id = req.params(":id");
          Pasajero pasajero = new Pasajero(id, null, null);
          JSONObject json = new JSONObject(pasajerosService.get(pasajero));
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          res.status(404);
          return new StandardResponse(404, exception);
        }
      });

    });

  }

}