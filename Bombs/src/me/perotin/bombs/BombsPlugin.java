package me.perotin.bombs;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.perotin.bombs.commands.BombsCommand;
import me.perotin.bombs.commands.CreateBombCommand;
import me.perotin.bombs.commands.DeleteBombCommand;
import me.perotin.bombs.commands.GiveBombCommand;
import me.perotin.bombs.commands.ListBombCommand;
import me.perotin.bombs.events.ThrowBombEvent;

public class BombsPlugin extends JavaPlugin {

	// declaring variables
	static File b;
	public static FileConfiguration bombsFile;
	public HashSet<Bomb> bombsStorage;
	public static BombsPlugin instance;



	// on enable
	@Override
	public void onEnable(){
		saveDefaultConfig();
		instance = this;
		b = new File(getDataFolder(), "bombs.yml");
		bombsFile = YamlConfiguration.loadConfiguration(b);
		bombsStorage = new HashSet<>();
		if(getBombFile() == null){
			saveResource("bombs.yml", false);
		}
		loadBombs();
		getCommand("createbomb").setExecutor(new CreateBombCommand());
		getCommand("deletebomb").setExecutor(new DeleteBombCommand());
		getCommand("listbombs").setExecutor(new ListBombCommand());
		getCommand("givebomb").setExecutor(new GiveBombCommand());
		getCommand("bombs").setExecutor(new BombsCommand());
		Bukkit.getPluginManager().registerEvents(new ThrowBombEvent(), this);

	}


	public void listGui(Player toShow){
		// only use when we know there are bombs
		Inventory inv = Bukkit.createInventory(null, 54, "Bombs list");
		ItemStack itemToAdd = null;
		int slotCount = -1;
		for(Bomb bomb : getBombStorage()){
			itemToAdd = bomb.getItemStack();
			slotCount++;

			inv.setItem(slotCount, itemToAdd);
			toShow.openInventory(inv);
		}
		inv.setItem(slotCount, itemToAdd);
		toShow.openInventory(inv);
	}

	private HashSet<Bomb> getBombStorage() {
		// TODO Auto-generated method stub
		return this.bombsStorage;
	}


	//loading bombs after server starts up again
	public void loadBombs(){
		if(getBombFile() == null){
			saveResource("bombs.yml", false);
			Bukkit.getLogger().info("Bombs file has been generated!");
			return;
		}
		else{
			// List is null or empty, no bombs are present so ignoring it.
			if(getBombFile().getStringList("bomb-names") == null || getBombFile().getStringList("bomb-names").size() == 0){
				return;
			}else{
				// looping through each bomb name in list and gathering its data
				String name = "";
				Material m = Material.AIR;
				Integer explosionPower = 0;
				Integer fuseTime = 0;
				Double velocity = 0.0;
				for(String bombName : getBombFile().getStringList("bomb-names")){
					Bukkit.broadcastMessage(bombName);
					m = Material.matchMaterial(getBombFile().getString("bombs."+bombName+".material"));
					explosionPower = getBombFile().getInt("bombs."+bombName+".explosion-power");
					fuseTime = getBombFile().getInt("bombs."+bombName+".fuse-time");
					velocity = getBombFile().getDouble("bombs."+bombName+".velocity");
					name = bombName;
					bombsStorage.add(new Bomb(name, explosionPower, fuseTime, m, velocity));
				}
				

			}
		}
	}
	// returns bomb file
	public FileConfiguration getBombFile(){
		return bombsFile;
	}
	public void saveBombFile() {
		try {
			bombsFile.save(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}






}
