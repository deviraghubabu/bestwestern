package com.bw.utilities;

import com.bw.accelerators.ReportManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

public class HttpUtils {


    public static synchronized String getResponseAsString(String httpMethod, String url, MultivaluedHashMap<String, Object> headers, String json) throws Exception {
        Response response = getResponse(httpMethod, url, headers, json);
        String strResponse = response.readEntity(String.class);        
        Log.info(strResponse);
        if (response.getStatus() != 200) {        
           // System.out.println("Http Error: " + response.getStatus());
            throw new Exception(strResponse);
        }
        return strResponse;
    }

    public static synchronized Response getResponse(String httpMethod, String url, MultivaluedHashMap<String, Object> headers, String json) throws Exception {
        Response response = null;
        String requestDetails = "{'Method': '".concat(httpMethod).concat("',").
                concat("'Url': '").concat(url).concat("',").
                concat("'RequestBody':").concat(json).concat("}");
        Log.info(requestDetails);
        ReportManager.info(requestDetails,true);
        ResteasyClient client = new ResteasyClientBuilderImpl().connectionPoolSize(100).disableTrustManager().build();
        ResteasyWebTarget target = client.target(url);
        Invocation.Builder request = target.request().headers(headers);

        switch (httpMethod) {
            case HttpMethod.GET:
                response = request.get();
                break;
            case HttpMethod.POST:
                response = request.post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
                break;
            case HttpMethod.PUT:
                response = request.put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
                break;
            case HttpMethod.DELETE:
                response = request.build(HttpMethod.DELETE, Entity.entity(json, MediaType.APPLICATION_JSON_TYPE)).invoke();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + httpMethod);
        }
        return response;
    }

}
