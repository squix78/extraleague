package ch.squix.extraleague.util;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.codehaus.jackson.JsonNode;

public class JsonHttpResponse {

  private final JsonNode json;
  private StatusLine statusLine;

  public JsonHttpResponse(StatusLine statusLine, JsonNode json) {
    this.statusLine = statusLine;
    this.json = json;
  }

  public int getStatusCode() {
    return statusLine.getStatusCode();
  }

  public String getReasonPhrase() {
    return statusLine.getReasonPhrase();
  }

  public JsonNode getJson() {
    return json;
  }

  public boolean isOk() {
    return getStatusCode() == HttpStatus.SC_OK;
  }

  @Override
  public String toString() {
    return statusLine.toString();
  }
}
