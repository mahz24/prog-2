package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.services.ReservasVuelosService;
import edu.prog2.helpers.StandardResponse;

public class ReservasVuelosController {

  public ReservasVuelosController(final ReservasVuelosService reservasVuelosService) {

    path("/vuelos-reservas", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 201, "ok", reservasVuelosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      get("/:reserva-vuelo", (req, res) -> {
        try {
          String params = req.params(":reserva-vuelo");
          JSONObject json = reservasVuelosService.get(params);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      post("", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          reservasVuelosService.add(json);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:reserva-vuelo", (request, response) -> {
        try {
          String params = request.params(":reserva-vuelo");
          JSONObject jsonBody = new JSONObject(request.body());
          JSONObject json = reservasVuelosService.set(params, jsonBody);
          return new StandardResponse(response, 201, "ok", json);
        } catch (Exception e) {
          return new StandardResponse(response, 404, e);
        }
      });

      delete("/:vuelo-reserva", (req, res) -> {
        try {
          String params = req.params(":vuelo-reserva");
          reservasVuelosService.remove(params);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

    });
  }
}