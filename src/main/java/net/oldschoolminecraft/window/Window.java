package net.oldschoolminecraft.window;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;

public class Window extends JavaPlugin
{
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Window instance;

    public WindowConfig config;

    private Server server;
    private final File configFile = new File("plugins/SystemWindow/config.json");

    public void onEnable()
    {
        instance = this;

        try
        {
            loadConfig();

            server = new Server(config.port);
            WebAppContext webAppContext = new WebAppContext();
            server.setHandler(webAppContext);

            URL webAppDir = getClass().getResource("META-INF/resources");
            webAppContext.setResourceBase(webAppDir.toURI().toString());

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        System.out.println("SystemWindow enabled");
    }

    public void loadConfig() throws IOException
    {
        if (configFile.exists())
        {
            config = gson.fromJson(new JsonReader(new FileReader(configFile)), WindowConfig.class);
            System.out.println("SystemWindow config loaded");
        } else {
            configFile.getParentFile().mkdirs();
            config = new WindowConfig();
            byte[] rand = new byte[32];
            new SecureRandom().nextBytes(rand);
            config.api_key = new String(DigestUtils.sha1(rand)).toLowerCase();
            config.port = 2021;
            gson.toJson(config, config.getClass(), new JsonWriter(new FileWriter(configFile)));
            System.out.println("SystemWindow generated new config");
        }
    }

    public void onDisable()
    {
        try
        {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("SystemWindow disabled");
    }

    public static class WindowConfig
    {
        public String api_key;
        public int port;
    }
}
