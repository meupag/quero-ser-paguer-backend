package com.ordersapi.orders.Services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ordersapi.orders.Domain.Entities.Cliente;
import com.ordersapi.orders.Domain.Repositories.IClientService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class ClienteService implements IClientService {
    @Override
    public Cliente getClientById(UUID id) throws Exception {
        String sURL = "http://clients-api.us-east-1.elasticbeanstalk.com/client/"+ id.toString(); //just a string
        Cliente cliente = new Cliente();
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            int resposeCode = request.getResponseCode();
            if(resposeCode == 200){
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
                JsonObject rootobj = root.getAsJsonObject();
                cliente.Id = UUID.fromString(rootobj.get("id").getAsString());
            }
            return cliente;
        }catch (Exception ex){
            throw new Exception("Não foi possivel se comunicar com o serviço de cliente");
        }
    }
}
