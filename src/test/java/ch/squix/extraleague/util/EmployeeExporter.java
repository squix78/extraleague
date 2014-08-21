package ch.squix.extraleague.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import ch.squix.extraleague.rest.playeruser.PlayerUserDto;

public class EmployeeExporter {

  private static final URI PLAZA_WS_BASE_URI = URI.create("http://plaza.netcetera.com/res");

  private static final long MEGABYTE = 1024L * 1024L;

  public static long bytesToMegabytes(long bytes) {
    return bytes / MEGABYTE;
  }
  
  public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
    if (args.length != 2) {
      System.err.println("Give user and password for Plaza as arguments!");
      System.exit(1);
    }

    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

    System.out.println("Employee export started...");
    
    HttpClientBuilder builder = HttpClientBuilder.create();

    CredentialsProvider provider = new SystemDefaultCredentialsProvider();
    provider.setCredentials(new AuthScope(PLAZA_WS_BASE_URI.getHost(), 443),
        new UsernamePasswordCredentials(args[0], args[1]));
    builder.setDefaultCredentialsProvider(provider);

    CloseableHttpClient httpClient = builder.build();

    HttpGet httpGet = new HttpGet(PLAZA_WS_BASE_URI + "/employees");
    httpGet.setHeader("Accept", "application/json");

    JsonHttpResponse response = httpClient.execute(httpGet, new JsonResponseHandler());
    if (response.isOk()) {
      //ObjectNode result = JsonNodeFactory.instance.objectNode();
      List<PlayerUserDto> dtos = new ArrayList<>();
      for (JsonNode emp : response.getJson()) {
        String shortName = emp.get("shortName").asText();

        if (!emp.get("active").asBoolean()) {
          System.out.println("Skipping inactive: " + shortName);
          continue;
        }
        CloseableHttpClient detailClient = builder.build();
        httpGet = new HttpGet(PLAZA_WS_BASE_URI + "/employees/" + shortName);
        httpGet.setHeader("Accept", "application/json");
        response = detailClient.execute(httpGet, new JsonResponseHandler());
        if (!response.isOk()) {
          System.err.println("ERROR for employee '" + shortName + "': " + response);
          return;
        }

        JsonNode empDetails = response.getJson();

        if (!empDetails.has("contact") || !empDetails.get("contact").has("email")) {
          System.err.println("Skipping " + shortName + " (No email address!)");
          continue;
        }
        PlayerUserDto dto = new PlayerUserDto();
        String email = empDetails.get("contact").get("email").asText();
        dto.setEmail(email);
        dto.setPlayer(shortName.toLowerCase());
        String fullname = parseNameFromEmail(email);
        dto.setImageUrl("http://www.netcetera.com/en/data/contacts/Netcetera/" + fullname + "/photo/"+ fullname +".jpeg");
        dtos.add(dto);
        System.out.println(dtos.size() + ". Successfully added " + shortName);
        //Thread.sleep(1500L);
        detailClient.close();
        dumpMemoryInformation();
      }

      ObjectMapper mapper = new ObjectMapper();
      System.out.println(mapper.writeValueAsString(dtos));
    } else {
      System.err.println("ERROR: " + response);
    }
  }
  
  private static void dumpMemoryInformation() {
	    // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    //runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
  }

  private static String parseNameFromEmail(String email) {
    String[] split = email.split("@");
    return split[0].replace('.', '-');
  }
  
  private static JsonHttpResponse getUsers(String username, String password) throws ClientProtocolException, IOException {

	    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

	    System.out.println("Employee export started...");
	    
	    HttpClientBuilder builder = HttpClientBuilder.create();

	    CredentialsProvider provider = new SystemDefaultCredentialsProvider();
	    provider.setCredentials(new AuthScope(PLAZA_WS_BASE_URI.getHost(), 443),
	        new UsernamePasswordCredentials(username, password));
	    builder.setDefaultCredentialsProvider(provider);

	    CloseableHttpClient httpClient = builder.build();

	    HttpGet httpGet = new HttpGet(PLAZA_WS_BASE_URI + "/employees");
	    httpGet.setHeader("Accept", "application/json");

	    return httpClient.execute(httpGet, new JsonResponseHandler());
  }
}
