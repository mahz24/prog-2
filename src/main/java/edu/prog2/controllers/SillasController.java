package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.SillasService;

public class SillasController {
  public SillasController(final SillasService sillasService) {
    path("/sillas", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 200, "ok", sillasService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      get("/:silla", (req, res) -> {
        try {
          String params = req.params(":silla");
          JSONObject json = sillasService.get(params);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      post("", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          sillasService.create(json.getString("avion"), json.getInt("ejecutivas"), json.getInt("economicas"));
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:silla", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          json = sillasService.set(json, req.params(":silla"));
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      delete("/:silla", (req, res) -> {
        try {
          String params = req.params(":silla");
          sillasService.remove(params);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });
    });
  }
}