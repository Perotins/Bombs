package me.perotin.bombs.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import me.perotin.bombs.Bomb;
import me.perotin.bombs.BombsPlugin;

public class ThrowBombEvent implements Listener {

	HashMap<ItemStack, Bomb>catcher = new HashMap<>();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void throwBomb(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		ItemStack bomb = e.getItem();
		String name = bomb.getItemMeta().getDisplayName();
		if(Bomb.isBomb(bomb)){
			Item dropped = p.getWorld().dropItem(p.getLocation(), bomb);
			dropped.setVelocity(p.getLocation().getDirection().normalize().multiply(Bomb.getBomb(bomb).getVelocity()));
			bomb.setAmount(bomb.getAmount() - 1);
			p.getInventory().setItemInHand(bomb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(BombsPlugin.instance, new Runnable(){
				@Override
				public void run(){
					dropped.getWorld().createExplosion(dropped.getLocation(), Bomb.getBomb(name).getPower());
					dropped.remove();
				}
			}, 20*Bomb.getBomb(name).getFuseTime());
		}
		
		
	}
	
	@EventHandler
	public void inventory(InventoryClickEvent event){
		if(event.getInventory().getName() == "Bombs list"){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void pickUpBomb(PlayerPickupItemEvent e){
		if(Bomb.isBomb(e.getItem())) e.setCancelled(true);
		
	}
	
	@EventHandler
	public void placeBomb(BlockPlaceEvent e){
		if(Bomb.isBomb(e.getItemInHand())){
			e.setCancelled(true);
		}
	}
}
