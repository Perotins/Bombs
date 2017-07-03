package me.perotin.bombs.utils;

import org.bukkit.ChatColor;

import me.perotin.bombs.BombsPlugin;

public class Messages {

	public static String getValue(String path){
		return ChatColor.translateAlternateColorCodes('&', BombsPlugin.instance.getConfig().getString(path));
	}
}
