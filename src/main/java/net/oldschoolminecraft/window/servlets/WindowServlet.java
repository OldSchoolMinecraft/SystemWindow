package net.oldschoolminecraft.window.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.oldschoolminecraft.window.Window;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class WindowServlet extends HttpServlet
{
    protected final Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (!request.isSecure()) return;
        List<NameValuePair> params = URLEncodedUtils.parse("http://example.com/?" + request.getQueryString(), StandardCharsets.UTF_8);
        for (NameValuePair pair : params)
            if (pair.getName().equalsIgnoreCase("api_key") && !Window.instance.config.api_key.equals(pair.getValue()))
                response.getWriter().print(gson.toJson(new StandardResponse(false, "Invalid API key")));
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
