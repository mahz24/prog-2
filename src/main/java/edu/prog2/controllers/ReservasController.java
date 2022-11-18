package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.services.ReservasService;
import edu.prog2.helpers.StandardResponse;

public class ReservasController {

  public ReservasController(final ReservasService reservasService) {

    path("/reservas", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(201, "ok", reservasService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:reserva", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String params = req.params(":reserva");
          JSONObject json = reservasService.get(params);
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

    });
  }
}