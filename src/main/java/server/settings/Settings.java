package server.settings;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Paths;

public class Settings {

    private static Values values;

    private static class Values{
        int startGameTimeout;
        int playerTimeOut;

        Values(){
            this.startGameTimeout = 20;
            this.playerTimeOut = 60;
        }
    }

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
                try(PrintWriter writer = new PrintWriter(file)){
                    writer.print(new Gson().toJson(values));
                }
            }
            if(values.startGameTimeout < 5)
                values.startGameTimeout = 20;
            if(values.playerTimeOut < 10)
                values.playerTimeOut = 60;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPlayerTimeout(){
        if(values == null)
            initSettings();
        return values.playerTimeOut;
    }

    public static int getStartGameTimeout(){
        if(values == null)
            initSettings();
        return values.startGameTimeout;
    }


}
