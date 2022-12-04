package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.services.ReservasService;
import edu.prog2.helpers.StandardResponse;

public class ReservasController {

  public ReservasController(final ReservasService reservasService) {

    path("/reservas", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 201, "ok", reservasService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      get("/:reserva", (req, res) -> {
        try {
          String params = req.params(":reserva");
          JSONObject json = reservasService.get(params);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      post("", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          reservasService.add(json);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:reserva", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          reservasService.set(json, req.params("reserva"));
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      delete("/:reserva", (req, res) -> {
        try {
          String params = req.params(":reserva");
          reservasService.remove(params);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });
    });
  }
}