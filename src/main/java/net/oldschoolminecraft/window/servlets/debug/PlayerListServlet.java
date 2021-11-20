package net.oldschoolminecraft.window.servlets.debug;

import com.google.gson.Gson;
import net.oldschoolminecraft.window.servlets.WindowServlet;
import org.bukkit.Bukkit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlayerListServlet extends WindowServlet
{
    private static Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(Bukkit.getOnlinePlayers()));
    }
}
