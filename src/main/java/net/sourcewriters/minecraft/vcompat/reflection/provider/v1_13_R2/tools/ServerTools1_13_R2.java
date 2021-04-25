package net.sourcewriters.minecraft.vcompat.reflection.provider.v1_13_R2.tools;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;

import net.sourcewriters.minecraft.vcompat.reflection.tools.ServerTools;

public class ServerTools1_13_R2 extends ServerTools {

    @Override
    public void setMotd(String text) {
        ((CraftServer) Bukkit.getServer()).getServer().setMotd(text);
    }

    @Override
    public String getMotd() {
        return ((CraftServer) Bukkit.getServer()).getServer().getMotd();
    }
    
    @Override
    public ConsoleReader getConsole() {
        return ((CraftServer) Bukkit.getServer()).getServer().reader;
    }

}