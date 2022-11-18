package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.SillasService;

public class SillasController {
  public SillasController(final SillasService sillasService) {
    path("/sillas", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(200, "ok", sillasService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:silla", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String params = req.params(":silla");
          JSONObject json = sillasService.get(params);
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });
    });
  }
}