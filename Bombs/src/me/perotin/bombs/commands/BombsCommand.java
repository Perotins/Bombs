package me.perotin.bombs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.perotin.bombs.utils.Messages;

public class BombsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!s.hasPermission("bombs.help") && !s.hasPermission("bombs.*")){
			s.sendMessage(Messages.getValue("no-permission"));
			return true;
		}
		s.sendMessage(ChatColor.GRAY + "----- *** " + ChatColor.GOLD + " Bombs " + ChatColor.GRAY + " *** -----");
		s.sendMessage(ChatColor.GRAY + "/createbomb <§6name-of-bomb"+ChatColor.GRAY + "> <§6explosion-power"+ChatColor.GRAY+ "> <"
				+ "§6fuse-time"+ChatColor.GRAY + "> <§6velocity"+ChatColor.GRAY + ">"); 
		s.sendMessage(ChatColor.GRAY + "/givebomb <§6player"+ChatColor.GRAY + "> <§6bomb"+ChatColor.GRAY+ "> <"
				+ "§6amount"+ChatColor.GRAY + ">");
		s.sendMessage(ChatColor.GRAY + "/listbombs");
		s.sendMessage(ChatColor.GRAY + "/deletebomb <"+ChatColor.GOLD + "bomb"+ChatColor.GRAY + ">");
		s.sendMessage(ChatColor.GRAY + "----- *** " + ChatColor.GOLD + " Version 1.2.1 " + ChatColor.GRAY + " *** -----");

		
		
		return true;
	}

}
