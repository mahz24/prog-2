package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;

import edu.prog2.services.VuelosService;

public class VuelosController {

  public VuelosController(final VuelosService vuelosService) {

    path("/vuelos", () -> {

      get("", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          return new StandardResponse(201, "ok", vuelosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:vuelo", (req, res) -> {
        res.type("application/json");
        try {
          res.status(201);
          String params = req.params(":vuelo");
          JSONObject json = vuelosService.get(params);
          return new StandardResponse(201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(404, exception);
        }
      });

      get("/:vuelo", (request, response) -> {
        response.type("application/json");
        try {
          String params = request.params(":vuelo");
          JSONObject json = vuelosService.get(params);
          response.status(201);
          return new StandardResponse(200, "ok", json);
        } catch (Exception e) {
          response.status(404);
          return new StandardResponse(404, e);
        }
      });
    });
  }
}