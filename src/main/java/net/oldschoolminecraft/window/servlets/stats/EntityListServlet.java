package net.oldschoolminecraft.window.servlets.stats;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.oldschoolminecraft.window.servlets.WindowServlet;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.io.IOException;
import java.util.ArrayList;

public class EntityListServlet extends WindowServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        super.doGet(request, response);

        response.setContentType("application/json");
        ArrayList<EntityListEntry> entityList = new ArrayList<>();
        for (World world : Bukkit.getWorlds())
            for (Entity entity : world.getEntities())
                entityList.add(new EntityListEntry()
                {{
                    world = entity.getWorld().getName();
                    entityId = entity.getEntityId();
                    type = determineEntityType(entity);
                    x = entity.getLocation().getX();
                    y = entity.getLocation().getY();
                    z = entity.getLocation().getZ();
                }});
        response.getWriter().print(gson.toJson(new StandardResponse(true, "--")));
    }

    private EntityType determineEntityType(Entity entity)
    {
        if (entity instanceof Chicken)
            return EntityType.Chicken;
        if (entity instanceof Cow)
            return EntityType.Cow;
        if (entity instanceof Creeper)
            return EntityType.Creeper;
        if (entity instanceof Ghast)
            return EntityType.Ghast;
        if (entity instanceof Giant)
            return EntityType.Giant;
        if (entity instanceof Player)
            return EntityType.Player;
        if (entity instanceof Pig)
            return EntityType.Pig;
        if (entity instanceof PigZombie)
            return EntityType.PigZombie;
        if (entity instanceof Sheep)
            return EntityType.Sheep;
        if (entity instanceof Skeleton)
            return EntityType.Skeleton;
        if (entity instanceof Slime)
            return EntityType.Slime;
        if (entity instanceof Spider)
            return EntityType.Spider;
        if (entity instanceof Squid)
            return EntityType.Squid;
        if (entity instanceof Wolf)
            return EntityType.Wolf;
        if (entity instanceof Zombie)
            return EntityType.Zombie;
        return EntityType.Unknown;
    }

    public class EntityListEntry
    {
        public String world;
        public int entityId;
        public EntityType type;
        public double x, y, z;
    }

    public enum EntityType
    {
        Chicken, Cow, Creeper, Ghast, Giant, Player, Pig, PigZombie, Sheep, Skeleton, Slime, Spider, Squid, Wolf, Zombie, Unknown
    }
}
