package io.github.teamgalacticraft.galacticraft.misc;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.teamgalacticraft.galacticraft.Galacticraft;
import net.minecraft.entity.Entity;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Capes {

    private static final Marker CAPES = MarkerManager.getMarker("Capes");
    private static List<String> capeUsers = new ArrayList<>();

    public static List<String> getCapeUsers() {
        return capeUsers;
    }

    public static void updateCapeList() {
        int timeout = 10000;
        URL capeListUrl;

        try {
            capeListUrl = new URL("https://raw.github.com/teamgalacticraft/Galacticraft-Fabric/master/capes.txt");
        }
        catch (IOException e) {
            Galacticraft.logger.fatal(CAPES, "FAILED TO GET CAPES"); //TODO debug msg not error when config is in
            return;
        }

        URLConnection connection;

        try {
            connection = capeListUrl.openConnection();
        }
        catch (IOException e) {
            Galacticraft.logger.fatal(CAPES, "FAILED TO GET CAPES");
            return;
        }

        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        InputStream stream;

        try {
            stream = connection.getInputStream();
        }
        catch (IOException e) {
            Galacticraft.logger.fatal(CAPES, "FAILED TO GET CAPES");
            return;
        }

        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    capeUsers.add(line);
                }
            }

        }
        catch (IOException ignored) {}
        finally
        {
            try {
                reader.close();
            }
            catch (IOException ignored) {}
        }
    }

    public static String getName(String uuid) { //Use this sparingly
        String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
        try {
            JsonParser parser = new JsonParser();
            @SuppressWarnings("deprecation")
            String nameJson = IOUtils.toString(new URL(url));
            JsonArray nameValue = (JsonArray) parser.parse(nameJson);
            String playerSlot = nameValue.get(nameValue.size()-1).toString();
            JsonObject nameObject = (JsonObject) parser.parse(playerSlot);
            return nameObject.get("name").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
}