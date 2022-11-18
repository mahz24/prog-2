package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.TrayectosService;

public class TrayectosController {

  public TrayectosController(final TrayectosService trayectosService) {

    path("/trayectos", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(200, "ok", trayectosService.getJSON());
        } catch (Exception exception) {
          res.status(404);
          return new StandardResponse(404, exception);
        }

      });

      get("/:trayecto", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String params = req.params(":trayecto");
          JSONObject json = trayectosService.get(params);
          return new StandardResponse(200, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

    });

  }

}