package net.oldschoolminecraft.window.servlets.errors;

import com.google.gson.Gson;
import net.oldschoolminecraft.window.servlets.WindowServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoRoute extends WindowServlet
{
    private static Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(new WindowServlet.StandardResponse(false, "No route")));
    }
}
