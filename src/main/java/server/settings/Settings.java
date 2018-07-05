package server.settings;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Paths;

public class Settings {

    private static Values values;

    private static class Values{
        int startGameTimeout;
        int playerTimeOut;
        String serverIp;

        Values(){
            this.startGameTimeout = 20;
            this.playerTimeOut = 60;
            this.serverIp = "localhost";
        }
    }

    private Settings(){}

    private static void initSettings(){
        File file = Paths.get("configurations/server_settings.json").toFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileReader reader = new FileReader(file)) {
            values = new Gson().fromJson(reader, Values.class);
            if(values == null) {
                values = new Values();
            }
            save();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static int getPlayerTimeout(){
        if(values == null)
            initSettings();
        if(values.playerTimeOut < 10) {
            values.playerTimeOut = 60;
            save();
        }
        return values.playerTimeOut;
    }

    public static int getStartGameTimeout(){
        if(values == null)
            initSettings();
        if(values.startGameTimeout < 5){
            values.startGameTimeout = 20;
            save();
        }
        return values.startGameTimeout;
    }

    public static String getServerIp(){
        if(values == null)
            initSettings();
        if(values.serverIp == null)
            values.serverIp = "localhost";
        return values.serverIp;
    }

    private static void save(){
        File file = Paths.get("configurations/server_settings.json").toFile();
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
