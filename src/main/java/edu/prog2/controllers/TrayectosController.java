package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.models.Trayecto;
import edu.prog2.services.TrayectosService;

public class TrayectosController {

  public TrayectosController(final TrayectosService trayectosService) {

    path("/trayectos", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 200, "ok", trayectosService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }

      });

      get("/:trayecto", (req, res) -> {
        try {
          String params = req.params(":trayecto");
          JSONObject json = trayectosService.get(params);
          return new StandardResponse(res, 200, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      post("", (req, res) -> {
        try {
          Trayecto trayecto = new Trayecto(new JSONObject(req.body()));
          trayectosService.add(trayecto);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:trayecto", (req, res) -> {
        try {
          JSONObject json = new JSONObject(req.body());
          json = trayectosService.set(json, req.params("trayecto"));
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception e) {
          return new StandardResponse(res, 404, e);
        }
      });

      delete("/:trayecto", (req, res) -> {
        try {
          String params = req.params(":trayecto");
          trayectosService.remove(params);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

    });

  }

}