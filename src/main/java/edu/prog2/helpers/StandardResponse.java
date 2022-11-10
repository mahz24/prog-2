package edu.prog2.helpers;

import org.json.JSONArray;
import org.json.JSONObject;

public class StandardResponse {

  private JSONObject json;

  public StandardResponse(int status, String message) {
    this.json = new JSONObject()
        .put("status", status)
        .put("message", message);
  }

  public StandardResponse(int status, Exception e) {
    this(status, e.getMessage());
    System.out.println("-".repeat(30));
    e.printStackTrace();
    System.out.println("-".repeat(30));
  }

  public StandardResponse(int status, String message, String data) {
    this(status, message);
    this.json.put("data", new JSONObject(data));
  }

  public StandardResponse(int status, String message, JSONObject data) {
    this(status, message);
    this.json.put("data", data);
  }

  public StandardResponse(int status, String message, JSONArray data) {
    this(status, message);
    this.json.put("data", data);
  }

  @Override
  public String toString() {
    return json.toString();
  }

}