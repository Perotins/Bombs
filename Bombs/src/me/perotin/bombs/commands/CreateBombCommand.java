package me.perotin.bombs.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.perotin.bombs.Bomb;
import me.perotin.bombs.BombsPlugin;
import me.perotin.bombs.utils.Messages;

public class CreateBombCommand implements CommandExecutor {

	// command for creating a bomb
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		String prefix = Messages.getValue("prefix");
		if(!(s instanceof Player)){
			s.sendMessage("Player only!");
			return true;
		}
		Player p = (Player) s;
		if(!p.hasPermission("bombs.create") && !p.hasPermission("bombs.*")){
			p.sendMessage(prefix + Messages.getValue("no-permission"));
			return true;
		}
		// they are not holding anything
		if(p.getItemInHand().getType() == Material.AIR || p.getItemInHand() == null){
			p.sendMessage(prefix + Messages.getValue("create-bomb-not-holding"));
			return true;
		}
		if(args.length != 4){
			p.sendMessage(prefix + Messages.getValue("create-incorrect-args"));
			return true;
		}
		else{
			String name = args[0];
			for(Bomb b : BombsPlugin.instance.bombsStorage){
				if(name.equalsIgnoreCase(b.getName())){
					p.sendMessage(Messages.getValue(prefix + "create-name-taken"));
					return true;
				}
			}
			int explosion = 0;
			int fuseTime = 0;
			double velocity = 0;
			try{
				velocity = Double.parseDouble(args[3]);
				explosion = Integer.parseInt(args[1]);
				fuseTime = Integer.parseInt(args[2]);
			}catch (NumberFormatException e){
				p.sendMessage(prefix + Messages.getValue("create-not-a-number"));
				return true;
			}
			
			Bomb bomb = new Bomb(name, explosion, fuseTime, p.getInventory().getItemInHand().getType(), velocity);
			BombsPlugin.instance.bombsStorage.add(bomb);
			bomb.writeToFile();
			bomb.giveBomb(p, 1);
			p.sendMessage(prefix + Messages.getValue("created-bomb"));
		}
		return true;
	}

}
