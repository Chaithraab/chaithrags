package com.ty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApp2 {
    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    private static final String BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=";

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String city = "London,us";
        String apiUrl = BASE_URL + city + "&appid=" + API_KEY;
        String jsonData = getJsonData(apiUrl);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        System.out.print("Enter the date (YYYY-MM-DD HH:mm:ss format): ");
                        String date1 = reader.readLine();
                        double temp = getTemperature(jsonData, date1);
                        System.out.println("Temperature on " + date1 + ": " + temp + "°C");
                        break;

                    case 2:
                        System.out.print("Enter the date (YYYY-MM-DD HH:mm:ss format): ");
                        String date2 = reader.readLine();
                        double windSpeed = getWindSpeed(jsonData, date2);
                        System.out.println("Wind Speed on " + date2 + ": " + windSpeed + " m/s");
                        break;

                    case 3:
                        System.out.print("Enter the date (YYYY-MM-DD HH:mm:ss format): ");
                        String date3 = reader.readLine();
                        double pressure = getPressure(jsonData, date3);
                        System.out.println("Pressure on " + date3 + ": " + pressure + " hPa");
                        break;

                    case 0:
                        System.out.println("Exiting the program.");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getJsonData(String apiUrl) {
        StringBuilder jsonData = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }

            reader.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonData.toString();
    }

    private static double getTemperature(String jsonData, String date) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray list = jsonObject.getAsJsonArray("list");

        for (int i = 0; i < list.size(); i++) {
            JsonObject entry = list.get(i).getAsJsonObject();
            String dt_txt = entry.get("dt_txt").getAsString();

            if (dt_txt.equals(date)) {
                JsonObject main = entry.getAsJsonObject("main");
                return main.get("temp").getAsDouble() - 273.15; // Convert temperature from Kelvin to Celsius
            }
        }

        return 0.0; // Return 0.0 if the date is not found
    }

    private static double getWindSpeed(String jsonData, String date) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray list = jsonObject.getAsJsonArray("list");

        for (int i = 0; i < list.size(); i++) {
            JsonObject entry = list.get(i).getAsJsonObject();
            String dt_txt = entry.get("dt_txt").getAsString();

            if (dt_txt.equals(date)) {
                JsonObject wind = entry.getAsJsonObject("wind");
                return wind.get("speed").getAsDouble();
            }
        }

        return 0.0; // Return 0.0 if the date is not found
    }

    private static double getPressure(String jsonData, String date) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray list = jsonObject.getAsJsonArray("list");

        for (int i = 0; i < list.size(); i++) {
            JsonObject entry = list.get(i).getAsJsonObject();
            String dt_txt = entry.get("dt_txt").getAsString();

            if (dt_txt.equals(date)) {
                JsonObject main = entry.getAsJsonObject("main");
                return main.get("pressure").getAsDouble();
            }
        }

        return 0.0; // Return 0.0 if the date is not found
    }
}

//public class WeatherApp2 {

//}
