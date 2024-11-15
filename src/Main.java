import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/3b3f985ec04d086f73ace72b/latest/USD";

    public static void main(String[] args) {
        try {
            // Crear la URL y abrir la conexión
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Verificar el código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                // Leer la respuesta
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Cerrar las conexiones
                in.close();
                connection.disconnect();

                // Analizar JSON usando Gson
                JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();
                JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

                // Mostrar algunas tasas de cambio
                System.out.println("Tasa de cambio USD a EUR: " + conversionRates.get("EUR").getAsDouble());
                System.out.println("Tasa de cambio USD a GBP: " + conversionRates.get("GBP").getAsDouble());
                System.out.println("Tasa de cambio USD a JPY: " + conversionRates.get("JPY").getAsDouble());
            } else {
                System.out.println("Error: Código de respuesta " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
