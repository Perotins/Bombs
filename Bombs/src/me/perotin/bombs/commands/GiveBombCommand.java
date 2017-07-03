package me.perotin.bombs.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.perotin.bombs.Bomb;
import me.perotin.bombs.utils.Messages;

public class GiveBombCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		String prefix = Messages.getValue("prefix");
		if(!s.hasPermission("bombs.give") && !s.hasPermission("bombs.*")){
			s.sendMessage(prefix +Messages.getValue("no-permission"));
			return true;
		}
		else{
			if(args.length != 3){
				s.sendMessage(prefix +Messages.getValue("give-incorrect-args"));
				return true;
			}
			Player toGive =  Bukkit.getPlayer(args[0]);
			if(toGive == null){
				s.sendMessage(prefix + Messages.getValue("unknown-player").replace("%player", args[0]));
				return true;
			}
			Bomb bomb = Bomb.getBomb(args[1]);
			if(bomb == null){
				s.sendMessage(Messages.getValue("unknown-bomb").replace( "%bomb", args[1]));
				return true;
			}
			Integer amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			}catch(NumberFormatException e){
				s.sendMessage(prefix + Messages.getValue("give-numbers-only"));
				return true;
			}
			bomb.giveBomb(toGive, amount);
			s.sendMessage(prefix + Messages.getValue("give-bomb-sender").replace("%amount", amount.toString()).replace("%bomb", ChatColor.translateAlternateColorCodes('&', bomb.getName()).replace("%player", toGive.getName())));
			
			toGive.sendMessage(prefix+Messages.getValue("give-bomb-receiver").replace("%amount", amount.toString()).replace("%bomb", ChatColor.translateAlternateColorCodes('&', bomb.getName())));
		}
		return true;
	}

}
