package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.AvionesService;

public class AvionesController {

  public AvionesController(final AvionesService avionesService) {
    path("/aviones", () -> {

      get("", (req, res) -> {

        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(200, "ok", avionesService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:matricula", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String matricula = req.params(":matricula");
          JSONObject json = avionesService.get(matricula);
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });
    });
  }

}