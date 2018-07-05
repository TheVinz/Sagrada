package server.settings;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Paths;

/**
 * The <tt>Settings</tt> class only contains static methods for reading from the server_settings.json configuration files.
 */
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

    /**
     * Returns the timeout time for the player's move read from the settings file.
     * @return the player move timeout.
     */
    public static int getPlayerTimeout(){
        if(values == null)
            initSettings();
        if(values.playerTimeOut < 10) {
            values.playerTimeOut = 60;
            save();
        }
        return values.playerTimeOut;
    }

    /**
     * Returns the start game timeout read from the settings file.
     * @return the start game timeout.
     */
    public static int getStartGameTimeout(){
        if(values == null)
            initSettings();
        if(values.startGameTimeout < 5){
            values.startGameTimeout = 20;
            save();
        }
        return values.startGameTimeout;
    }

    /**
     * Returns the server ip read from the settings file.
     * @return the server ip.
     */
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
