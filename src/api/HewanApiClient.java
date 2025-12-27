package api;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Hewan;

public class HewanApiClient {

    private static final String BASE_URL = "http://localhost:3000/api/hewan";

    private static final HttpClient client =
            HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

    private static final Gson gson = new Gson();

    // ===== GET =====
        public static List<Hewan> getAll() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/api/hewan"))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("RAW RESPONSE DARI API:");
        System.out.println(response.body());

        Type listType = new TypeToken<List<Hewan>>() {}.getType();
        return gson.fromJson(response.body(), listType);
        }


    // ===== CREATE =====
    public static void save(Hewan h) throws Exception {
    String json = """
    {
      "nama": "%s",
      "jenis": "%s",
      "umur": "%s",
      "pemilik": "%s"
    }
    """.formatted(
        h.getNama(),
        h.getJenis(),
        h.getUmur(),
        h.getPemilik()
    );

    System.out.println("JSON CREATE:");
    System.out.println(json);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:3000/api/hewan"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    client.send(request, HttpResponse.BodyHandlers.ofString());
}


    // ===== UPDATE =====
    public static void update(Hewan hewan) throws Exception {

        String json = gson.toJson(hewan);
        System.out.println("JSON KIRIM (UPDATE): " + json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + hewan.getIdHewan()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // ===== DELETE =====
    public static void delete(int id) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}