package edu.prog2.helpers;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.*;

public class StandardResponse {

  private JSONObject json;

  public StandardResponse(Response res, int status, String message) {
    res.status(status);
    res.type("appliction/jsom");
    this.json = new JSONObject()
        .put("status", status)
        .put("message", message);
  }

  public StandardResponse(Response res, int status, Exception e) {
    this(res, status, e.getMessage());
    System.out.println("-".repeat(30));
    e.printStackTrace();
    System.out.println("-".repeat(30));
  }

  public StandardResponse(Response res, int status, String message, String data) {
    this(res, status, message);
    this.json.put("data", new JSONObject(data));
  }

  public StandardResponse(Response res, int status, String message, JSONObject data) {
    this(res, status, message);
    this.json.put("data", data);
  }

  public StandardResponse(Response res, int status, String message, JSONArray data) {
    this(res, status, message);
    this.json.put("data", data);
  }

  @Override
  public String toString() {
    return json.toString();
  }

}