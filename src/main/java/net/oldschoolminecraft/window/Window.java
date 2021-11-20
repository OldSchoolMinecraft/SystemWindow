package net.oldschoolminecraft.window;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.oldschoolminecraft.window.servlets.accounts.DeleteAccountServlet;
import net.oldschoolminecraft.window.servlets.bans.IssueBanServlet;
import net.oldschoolminecraft.window.servlets.bans.RevokeBanServlet;
import net.oldschoolminecraft.window.servlets.debug.PlayerListServlet;
import net.oldschoolminecraft.window.servlets.errors.InternalServerError;
import net.oldschoolminecraft.window.servlets.errors.NoRoute;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
            ServerConnector connector = new ServerConnector(server);
            server.setConnectors(new Connector[]{connector});
            ServletContextHandler handler = new ServletContextHandler();
            handler.setContextPath("/*");



            // debug
            handler.addServlet(PlayerListServlet.class, "/debug/playerList");

            // bans
            handler.addServlet(IssueBanServlet.class, "/bans/issue");
            handler.addServlet(RevokeBanServlet.class, "/bans/revoke");

            // accounts
            handler.addServlet(DeleteAccountServlet.class, "/accounts/delete");

            // fail routing
            handler.addServlet(NoRoute.class, "/");
            handler.addServlet(NoRoute.class, "/noRoute");
            handler.addServlet(InternalServerError.class, "/internalServerError");

            // error handling
            ErrorPageErrorHandler errorPageErrorHandler = new ErrorPageErrorHandler();
            errorPageErrorHandler.addErrorPage(404, "/noRoute");
            errorPageErrorHandler.addErrorPage(500, "/internalServerError");

            server.setHandler(handler);

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
