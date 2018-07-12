package client.settings;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Paths;

/**
 * The <tt>Settings</tt> class reads from the "client_settings.json" file the settings for the connection to the server.
 */
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
        File dir = Paths.get("configurations").toFile();
        dir.mkdir();
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

    /**
     * Returns the server IP read from the file, if present, or "localhost" as default value.
     * @return the server IP.
     */
    public static String getServerIp(){
        if(values == null)
            initSettings();
        if(values.serverIp == null){
            values.serverIp = "localhost";
            save(values.serverIp, values.username);
        }
        return values.serverIp;
    }

    /**
     * Returns the last username used to connect to a Sagrada server, if present, or <code>null</code> as default value.
     * @return the last username used to connect to a Sagrada server.
     */
    public static String getUsername(){
        if(values == null)
            initSettings();
        return values.username;
    }

    /**
     * Saves the server IP and the username into the settings file.
     * @param serverIp the server IP.
     * @param username the client's username.
     */
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
