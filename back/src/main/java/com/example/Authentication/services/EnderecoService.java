package com.example.Authentication.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class EnderecoService {
    public Object findByRegiao(String cep) throws IOException {
        long cepConvert = Long.parseLong(cep);
        String url = "https://viacep.com.br/ws/"+cepConvert+"/json/";
        URL obj = new URL(url);
        //Abre a conexão
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // Lê a resposta do servidor
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }
}
