package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;

import edu.prog2.services.VuelosService;

public class VuelosController {

  public VuelosController(final VuelosService vuelosService) {

    path("/vuelos", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 201, "ok", vuelosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      get("/:vuelo", (req, res) -> {
        try {
          String params = req.params(":vuelo");
          JSONObject json = vuelosService.get(params);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      get("/:vuelo", (req, res) -> {
        try {
          String params = req.params(":vuelo");
          JSONObject json = vuelosService.get(params);
          return new StandardResponse(res, 200, "ok", json);
        } catch (Exception e) {
          return new StandardResponse(res, 404, e);
        }
      });

      post("", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          vuelosService.add(json);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:vuelo", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          vuelosService.set(json, req.params(":vuelo"));
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });
    });
  }
}