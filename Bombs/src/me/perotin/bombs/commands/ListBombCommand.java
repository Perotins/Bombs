package me.perotin.bombs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.perotin.bombs.Bomb;
import me.perotin.bombs.BombsPlugin;
import me.perotin.bombs.utils.Messages;


public class ListBombCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(!s.hasPermission("bombs.list") && !s.hasPermission("bombs.*")){
			s.sendMessage(Messages.getValue("no-permission"));
			return true;
		}
		else{
			if(BombsPlugin.instance.bombsStorage.size() == 0){
				s.sendMessage(Messages.getValue("no-bombs"));
				return true;
			}
			if(s instanceof Player){
				Player toShow = (Player) s;
				BombsPlugin.instance.listGui(toShow);
				return true;
			}
			String msg = "";
			for(Bomb b : BombsPlugin.instance.bombsStorage){
				msg +=ChatColor.GOLD + b.getName() + ChatColor.GRAY+", ";
			}
			s.sendMessage(Messages.getValue("prefix")+msg);
		}
		return true;
	}

}
