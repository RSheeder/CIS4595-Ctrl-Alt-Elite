package com.example.lochat;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationActivityTest {
    @Test
    public void doInBackground() {
        double latitude = 30.5491;
        double longitude = -87.2186;

        HttpHandler http = new HttpHandler();
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&result_type=postal_code&sensor=false&key=AIzaSyB8Ur4e8Z57G9TgIna0Nrr4tQiYJz5SDIM ",latitude,longitude);

        String returnedJSON = "{\n" +
                "   \"plus_code\" : {\n" +
                "      \"compound_code\" : \"GQXJ+JH Ferry Pass, FL, USA\",\n" +
                "      \"global_code\" : \"862JGQXJ+JH\"\n" +
                "   },\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"32514\",\n" +
                "               \"short_name\" : \"32514\",\n" +
                "               \"types\" : [ \"postal_code\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Pensacola\",\n" +
                "               \"short_name\" : \"Pensacola\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Florida\",\n" +
                "               \"short_name\" : \"FL\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"United States\",\n" +
                "               \"short_name\" : \"US\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Pensacola, FL 32514, USA\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 30.5852189,\n" +
                "                  \"lng\" : -87.16080889999999\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 30.476449,\n" +
                "                  \"lng\" : -87.266682\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 30.5327462,\n" +
                "               \"lng\" : -87.2120599\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 30.5852189,\n" +
                "                  \"lng\" : -87.16080889999999\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 30.476449,\n" +
                "                  \"lng\" : -87.266682\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJ5aYoNM3qkIgRcUm3VA0-bfw\",\n" +
                "         \"types\" : [ \"postal_code\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}";

        String expected = returnedJSON.replace("\n", "");
        String response = http.GetHTTP(url);
        assertEquals(expected, response);
    }
}