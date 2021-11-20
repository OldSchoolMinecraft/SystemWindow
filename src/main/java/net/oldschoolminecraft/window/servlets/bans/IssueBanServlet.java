package net.oldschoolminecraft.window.servlets.bans;

import net.oldschoolminecraft.window.servlets.WindowServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IssueBanServlet extends WindowServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        super.doGet(request, response);

        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(new WindowServlet.StandardResponse(false, "Not implemented")));
    }
}
