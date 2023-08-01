package com.ty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class weather3 {
    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the choice

            switch (choice) {
                case 1:
                    getWeatherData(scanner);
                    break;
                case 2:
                    getWindSpeedData(scanner);
                    break;
                case 3:
                    getPressureData(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Get weather");
        System.out.println("2. Get Wind Speed");
        System.out.println("3. Get Pressure");
        System.out.println("0. Exit");
    }

    public static void getWeatherData(Scanner scanner) {
        System.out.print("Enter the date: ");
        String date = scanner.nextLine();
        String apiUrl = API_URL + "?date=" + date;

        try {
            String response = sendGetRequest(apiUrl);
            // Parse the response JSON and extract the temperature value
            double temperature = parseTemperatureFromApiResponse(response);
            System.out.println("Temperature on " + date + ": " + temperature + "°C");
        } catch (IOException e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }

    public static void getWindSpeedData(Scanner scanner) {
        System.out.print("Enter the date: ");
        String date = scanner.nextLine();
        String apiUrl = API_URL + "?date=" + date;

        try {
            String response = sendGetRequest(apiUrl);
            // Parse the response JSON and extract the wind speed value
            double windSpeed = parseWindSpeedFromApiResponse(response);
            System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
        } catch (IOException e) {
            System.out.println("Error fetching wind speed data: " + e.getMessage());
        }
    }

    public static void getPressureData(Scanner scanner) {
        System.out.print("Enter the date: ");
        String date = scanner.nextLine();
        String apiUrl = API_URL + "?date=" + date;

        try {
            String response = sendGetRequest(apiUrl);
       
            double pressure = parsePressureFromApiResponse(response);
            System.out.println("Pressure on " + date + ": " + pressure + " hPa");
        } catch (IOException e) {
            System.out.println("Error fetching pressure data: " + e.getMessage());
        }
    }

    private static String sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        return response.toString();
    }

    
    private static double parseTemperatureFromApiResponse(String response) {
        return 25.0;
    }

    private static double parseWindSpeedFromApiResponse(String response) {
        return 8.5;
    }

    private static double parsePressureFromApiResponse(String response) {
       
        return 1013.25;
    }
}


