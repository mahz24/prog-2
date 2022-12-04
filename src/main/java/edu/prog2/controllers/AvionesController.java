package edu.prog2.controllers;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.models.Avion;
import edu.prog2.services.AvionesService;

public class AvionesController {

  public AvionesController(final AvionesService avionesService) {
    path("/aviones", () -> {

      get("", (req, res) -> {
        try {
          return new StandardResponse(res, 200, "ok", avionesService.getJSON());
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      get("/:matricula", (req, res) -> {
        try {
          String matricula = req.params(":matricula");
          JSONObject json = avionesService.get(matricula);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      post("", (req, res) -> {
        try {
          Avion avion = new Avion(new JSONObject(req.body()));
          avionesService.add(avion);
          return new StandardResponse(res, 201, "ok");
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });

      put("/:matricula", (req, res) -> {
        try {
          String matricula = req.params(":matricula");
          JSONObject json = new JSONObject(req.body());
          json = avionesService.set(matricula, json);
          return new StandardResponse(res, 201, "ok", json);
        } catch (Exception exception) {
          return new StandardResponse(res, 404, exception);
        }
      });
    });
  }

}