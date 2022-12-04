package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.models.Pasajero;
import edu.prog2.services.PasajerosService;

public class PasajerosController {

  public PasajerosController(final PasajerosService pasajerosService) {

    path("/pasajeros", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 200, "ok", pasajerosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }

      });

      get("/:id", (req, res) -> {
        try {
          String id = req.params(":id");
          JSONObject json = pasajerosService.get(id);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      post("", (req, res) -> {
        try {
          Pasajero pasajero = new Pasajero(new JSONObject(req.body()));
          pasajerosService.add(pasajero);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:identificacion", (req, res) -> {
        try {
          String identificacion = req.params(":identificacion");
          JSONObject json = new JSONObject(req.body());
          json = pasajerosService.set(identificacion, json);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      delete("/:id", (req, res) -> {
        try {
          String id = req.params(":id");
          pasajerosService.remove(id);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception e) {
          return new StandardResponse(res, 404, e);
        }
      });

    });

  }

}