package ch.squix.extraleague.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Preconditions;

public class JsonResponseHandler implements ResponseHandler<JsonHttpResponse> {

  private final Charset charset;

  public JsonResponseHandler() {
    this.charset = Consts.UTF_8;
  }

  public JsonResponseHandler(Charset charset) throws NullPointerException {
    this.charset = Preconditions.checkNotNull(charset, "charset");
  }

  @Override
  public JsonHttpResponse handleResponse(HttpResponse response) throws ClientProtocolException,
      IOException {
    StatusLine statusLine = response.getStatusLine();
    HttpEntity entity = response.getEntity();
    String body = (entity != null ? EntityUtils.toString(entity, charset) : null);
    JsonNode json = null;
    if (body != null) {
      try {
        json = new ObjectMapper().readValue(body, JsonNode.class);
      } catch (IOException e) {
        // Do nothing, probably just no JSON in response
      }
    }

    return new JsonHttpResponse(statusLine, json);
  }
}
