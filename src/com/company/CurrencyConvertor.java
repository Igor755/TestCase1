package com.company;

import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;



public class CurrencyConvertor {

    // API Provider URL http://data.fixer.io/api/latest?access_key=395a99dddbab6370cd145f2534fbc113&=base:usd
   // https://data.fixer.io/api/latest?access_key=395a99dddbab6370cd145f2534fbc113&base = USD
    private static final String API_PROVDIER = "http://data.fixer.io/api/";
    private static final String KEY = "395a99dddbab6370cd145f2534fbc113";

    public static double convert(String fromCurrencyCode, String toCurrencyCode) {

        double conversionRate = 0.0;
        if ((fromCurrencyCode != null && !fromCurrencyCode.isEmpty())
                && (toCurrencyCode != null && !toCurrencyCode.isEmpty())) {

            CurrencyConversionResponse response = getResponse(API_PROVDIER+"latest?access_key="+KEY+"&"+"USD");

            if (response != null) {

                double conversionRateUSD = 0.0;
                String rateEURtoUSD= response.getRates().get("USD"); // 1 EUR = x USD
                if (rateEURtoUSD != null)
                    conversionRateUSD  = Double.valueOf(rateEURtoUSD);

                String rate = response.getRates().get(toCurrencyCode); // 1 EUR = x toCurrencyCode

                if (rate != null)
                    conversionRate  = Double.valueOf(rate);

                return conversionRate / conversionRateUSD;
            }

        }

        return conversionRate;
    }

    // Method to get the response from API
    private static CurrencyConversionResponse getResponse(String strUrl) {

        CurrencyConversionResponse response = null;

        Gson gson = new Gson();
        StringBuffer sb = new StringBuffer();

        if (strUrl == null || strUrl.isEmpty()) {

            System.out.println("Application Error");
            return null;
        }

        URL url;
        try {
            url = new URL(strUrl);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            InputStream stream = connection.getInputStream();

            int data = stream.read();

            while (data != -1) {

                sb.append((char) data);

                data = stream.read();
            }

            stream.close();

            response = gson.fromJson(sb.toString(), CurrencyConversionResponse.class);

        } catch (MalformedURLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    public static void main(String[] args) throws IOException {


        String fromCurrencyCode = "USD";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the price :");
        double amount = scanner.nextDouble();

        System.out.println("Enter the Currency Code that you want to covert: UAH, EUR, GBP");
        String toCurrencyCode = scanner.next();
        fromCurrencyCode = fromCurrencyCode.toUpperCase();
        toCurrencyCode = toCurrencyCode.toUpperCase();


        if (toCurrencyCode.equals("UAH")) {
            double coversionRate = convert(fromCurrencyCode, toCurrencyCode);

            System.out.println("Hi, The " + amount + " " + fromCurrencyCode + " is equivalent to " + (coversionRate * amount) + " " + toCurrencyCode + " today.");
            scanner.close();

        } else if (toCurrencyCode.equals("EUR")) {
            double coversionRate = convert(fromCurrencyCode, toCurrencyCode);

            System.out.println("Hi, The " + amount + " " + fromCurrencyCode + " is equivalent to " + (coversionRate * amount) + " " + toCurrencyCode + " today.");
            scanner.close();

        } else if (toCurrencyCode.equals("GBP")) {
            double coversionRate = convert(fromCurrencyCode, toCurrencyCode);

            System.out.println("Hi, The " + amount + " " + fromCurrencyCode + " is equivalent to " + (coversionRate * amount) + " " + toCurrencyCode + " today.");
            scanner.close();

        } else {
            System.out.println("Incorected Currency code");
        }

    }

}