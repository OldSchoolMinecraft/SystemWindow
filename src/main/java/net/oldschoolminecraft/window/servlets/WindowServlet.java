package net.oldschoolminecraft.window.servlets;

import com.google.gson.Gson;
import net.oldschoolminecraft.window.Window;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class WindowServlet extends HttpServlet
{
    protected final Gson gson = new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (!request.isSecure())
        {
            response.setStatus(403);
            response.getWriter().print(gson.toJson(new StandardResponse(false, "Insecure connection")));
        }

        List<NameValuePair> params = URLEncodedUtils.parse("http://dummy/?" + request.getQueryString(), StandardCharsets.UTF_8);
        for (NameValuePair pair : params)
        {
            if (pair.getName().equalsIgnoreCase("api_key") && !Window.instance.config.api_key.equals(pair.getValue()))
            {
                response.setStatus(200);
                response.getWriter().print(gson.toJson(new StandardResponse(false, "Invalid API key")));
            }
        }
    }

    public static class StandardResponse
    {
        public boolean success;
        public String message;

        public StandardResponse() {}

        public StandardResponse(boolean success, String message)
        {
            this.success = success;
            this.message = message;
        }
    }
}
