package com.infosupport;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by stijn on 9-6-2017.
 */
public class LambdaHandler implements RequestStreamHandler {

    JSONParser parser = new JSONParser();
    private ArrayList<UserModel> knownUsers;

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        JSONObject responseJson = new JSONObject();
        String responseCode = "200";
        try {
            if (knownUsers == null) {
                knownUsers = new ArrayList<UserModel>();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            JSONObject event = (JSONObject)parser.parse(reader);
            if (event.get("httpMethod").equals("GET")) {
                responseJson.put("body", getKnownUsersResponseBody().toJSONString());
            }
            else if(event.get("httpMethod").equals("POST")){
                logger.log(event.toJSONString());
                String bodyString = (String)event.get("body");
                if(bodyString != null){ event = (JSONObject)parser.parse(bodyString); }
                UserModel user = new UserModel();
                user.setFirstName((String) event.get("firstName"));
                user.setLastName((String) event.get("lastName"));

                if (user.getFirstName() != null && user.getLastName() != null) {
                    knownUsers.add(user);
                }
                responseJson.put("body", getKnownUsersResponseBody());
            }

            responseJson.put("statusCode", responseCode);
        }
        catch(ParseException e){
            logger.log(e.getMessage());
            responseJson.put("statusCode", "500");
            responseJson.put("exception", e);
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            logger.log(responseJson.toJSONString());
            writer.write(responseJson.toJSONString());
            writer.close();
        }
        catch(Exception e){
            logger.log(e.getMessage());
        }
    }

    private JSONArray getKnownUsersResponseBody(){
        JSONArray body = new JSONArray();
        for (UserModel model:
             knownUsers) {
            JSONObject user = new JSONObject();
            user.put("firstName", model.getFirstName());
            user.put("lastName", model.getLastName());
            body.add(user);
        }
        return body;
    }

}
