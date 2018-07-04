package client.settings;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Paths;

public class Settings {

    private static Values values;

    private static class Values{
        String serverIp;
        String username;

        Values(){
            serverIp = "localhost";
            username = null;
        }
    }

    private Settings(){}

    private static void initSettings(){
        File file = Paths.get("configurations/client_settings.json").toFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileReader reader = new FileReader(file)) {
            values = new Gson().fromJson(reader, Values.class);
            if(values == null) {
                values = new Values();
                save(values.serverIp, values.username);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServerIp(){
        if(values == null)
            initSettings();
        if(values.serverIp == null){
            values.serverIp = "localhost";
            save(values.serverIp, values.username);
        }
        return values.serverIp;
    }

    public static String getUsername(){
        if(values == null)
            initSettings();
        return values.username;
    }

    public static void save(String serverIp, String username){
        values.serverIp = serverIp;
        values.username = username;
        File file = Paths.get("configurations/client_settings.json").toFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(PrintWriter writer = new PrintWriter(file)) {
            writer.print(new Gson().toJson(values));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
