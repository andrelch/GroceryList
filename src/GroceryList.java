import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Program for print data in JSON format.
public class Grocerylist {
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

//            JSONObject file = new JSONObject();
//            file.put("Full Name", "Ritu Sharma");
//            file.put("Roll No.", new Integer(1704310046));
//            file.put("Tuition Fees", new Double(65400));
//
//
//            // To print in JSON format.
//            System.out.print(file.get("Tuition Fees"));
        pull();

    }

    public static void pull() throws ParseException {
        String output = "abc";
        String totalJson="";
        try {

//                URL url = new URL("https://swapi.dev/api/people/1/");
//                URL url = new URL("https://pokeapi.co/api/v2/pokemon/ditto");
            URL url = new URL("https://last-airbender-api.fly.dev/api/v1/characters");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
//                    System.out.println(output);
                totalJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
//            Object jsonObject = parser.parse(totalJson);
//            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totalJson);
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totalJson);
//            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(String.valueOf(jsonArray));


//            System.out.println(jsonObject);
//            org.json.simple.JSONArray jsonObject = (org.json.simple.JSONArray) parser.parse(totlaJson);
//            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
//            System.out.println("hi" + jsonObject);
//            System.out.println("hi" + jsonObject);

        try {
            //System.out.println(jsonObject.get("name"));

            int characterIndex=0;
            org.json.simple.JSONObject character =(org.json.simple.JSONObject) jsonArray.get(characterIndex);
            //why can set data type to jsonobject even though getting from jsonarray?

            org.json.simple.JSONArray allies =(org.json.simple.JSONArray) character.get("allies");
            org.json.simple.JSONArray enemies =(org.json.simple.JSONArray) character.get("enemies");

            ArrayList <String> alliesList = new ArrayList<String>();
            ArrayList <String> enemiesList = new ArrayList<String>();

            for (int i = 0; i < allies.size(); i++) {
                String temp;
                temp = (String) allies.get(i);
                alliesList.add(temp);
            }

            for (int i = 0; i < enemies.size(); i++) {
                String temp;
                temp = (String) enemies.get(i);
                enemiesList.add(temp);
            }

            System.out.println("Character Index:" + characterIndex);
            String name = (String) character.get("name");
            System.out.println("Name: " + name);
            String id = (String) character.get("_id");
            System.out.println("ID: " + id);
            System.out.println("Allies: "  + alliesList);
            System.out.println("Enemies: " + enemiesList);

//                String id = "";
//                for(int i=0; i< character.size(); i++){
//                    details.get(i);
//                    System.out.println("\n" + details);
//                }

//                org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("abilities");
//                int n =   msg.size(); //(msg).length();
//
//                for (int i = 0; i < n; ++i) {
//                    org.json.simple.JSONObject test =(org.json.simple.JSONObject) msg.get(i);
//
//                    org.json.simple.JSONObject test2 =(org.json.simple.JSONObject) test.get("ability");
//
//                    String abilityName = (String) test2.get("name");
//
//                    System.out.println(abilityName);
//                   // System.out.println(person.getInt("key"));
//                }
//                long name= (long)jsonObject.get("height");
//                System.out.println(name);
        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }
}

