package client.settings;

import com.google.gson.Gson;

import java.io.*;

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

    private static void initSettings(){
        String settingsPath = Settings.class.getResource("settings.json").getPath();
        File file = new File(settingsPath);
        try(FileReader reader = new FileReader(file)) {
            values = new Gson().fromJson(reader, Values.class);
            if(values == null) {
                values = new Values();
                try(PrintWriter writer = new PrintWriter(file)){
                    writer.print(new Gson().toJson(values));
                }
            }
            if(values.serverIp == null)
                values.serverIp = "localhost";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServerIp(){
        if(values == null)
            initSettings();
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
        String settingsPath = Settings.class.getResource("settings.json").getPath();
        File file = new File(settingsPath);
        try(PrintWriter writer = new PrintWriter(file)) {
            writer.print(new Gson().toJson(values));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
