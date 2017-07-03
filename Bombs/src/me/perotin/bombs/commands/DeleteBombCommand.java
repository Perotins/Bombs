package me.perotin.bombs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.perotin.bombs.Bomb;
import me.perotin.bombs.utils.Messages;

public class DeleteBombCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		String prefix = Messages.getValue("prefix");
		if(!s.hasPermission("bombs.delete") && !s.hasPermission("bombs.*")){
			s.sendMessage(Messages.getValue("no-permission"));
			return true;
		}
		else if(args.length != 1){
			s.sendMessage(prefix + "delete-incorrect-args");
			return true;
		}
		else{
			Bomb bomb = Bomb.getBomb(args[0]);
			if(bomb == null){
				s.sendMessage(Messages.getValue("unknown-bomb").replace("%bomb", args[0]));
				return true;
			}else{
				s.sendMessage(prefix +Messages.getValue("deleted-bomb").replace("%bomb", bomb.getName()));
				bomb.delete();
			}
		}
		return true;
	}

}
