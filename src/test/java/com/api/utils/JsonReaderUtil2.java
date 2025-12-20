package com.api.utils;

import com.api.request.model.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonReaderUtil2 {

    public static void main(String[] args) throws IOException {

        //loginAPITestData.json ----> src/test/resources/demodata/loginAPITestData.json
        // covert JSON Object into POJO or Java Object using ObjectMapper ----> Deserialization
        // using library Jackson Databind  ---> ObjectMapper class

        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("testdata/loginAPITestData.json");

        /*ObjectMapper objMapper = new ObjectMapper();
        UserCredentials userCredentials = objMapper
                .readValue(inputStream, UserCredentials.class);

        System.out.println("Username: " + userCredentials.username());
        System.out.println("Password: " + userCredentials.password());*/


        //  when u have json array in the json file i.e list of objects [{},{},{}]
        // but the probem is that we cannot directly map json array to List<UserCredentials>

/*        ObjectMapper objMapper = new ObjectMapper();
        List userCredentials = objMapper
                .readValue(inputStream, List.class);

        System.out.println(userCredentials);*/

        // hence we can use Array of UserCredentials and then print all objects

        ObjectMapper objMapper = new ObjectMapper();
        UserCredentials[] userCredentials = objMapper
                .readValue(inputStream, UserCredentials[].class);

        System.out.println("Total user credentials: " + userCredentials.length);

        for (UserCredentials uc : userCredentials) {
            System.out.println("Username: " + uc.username() + ", Password: " + uc.password());
        }

        System.out.println("-----------------------------------");
        // alternatively we can convert array to List using Arrays.asList() method
        List<UserCredentials> userCredentialsList = List.of(userCredentials);
        System.out.println("User Credentials List: " + userCredentialsList);
        for(UserCredentials users : userCredentialsList){
            System.out.println("Username: " + users.username() + ", Password: " + users.password());
        }

        // we can also use iterator to iterate over the list and give this to data provider
        userCredentialsList.iterator();






    }
}
