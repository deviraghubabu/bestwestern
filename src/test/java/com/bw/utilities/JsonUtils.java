package com.bw.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;


public class JsonUtils {

    public static <T> T getObject(String response, Class<T> cls){
        Gson gson = new Gson();
        return gson.fromJson(response, cls);
    }

    public static synchronized String getJSONFor(Object object){
        Gson jsonObject = new GsonBuilder().disableHtmlEscaping().create();
        String json = jsonObject.toJson(object);
        return json;
    }

    public static synchronized String getJSONForPrettyPrint(Object object){
        Gson jsonObject = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String json = jsonObject.toJson(object);
        return json;
    }

    public static synchronized <T> T getObject(FileReader fileReader, Class<T> cls){
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(fileReader);
        return gson.fromJson(reader, cls);
    }
}
