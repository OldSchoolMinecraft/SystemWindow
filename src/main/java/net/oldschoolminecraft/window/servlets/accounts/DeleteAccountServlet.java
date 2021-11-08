package net.oldschoolminecraft.window.servlets.accounts;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.oldschoolminecraft.window.servlets.WindowServlet;

import java.io.IOException;

@WebServlet("/accounts/delete")
public class DeleteAccountServlet extends WindowServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        super.doGet(request, response);

        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(new StandardResponse(true, "--")));
    }
}
