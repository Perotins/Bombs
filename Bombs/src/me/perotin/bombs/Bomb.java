package me.perotin.bombs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.perotin.bombs.utils.Messages;


public class Bomb {

	// name of bomb
	String name;
	// power of explosion
	int powerOfExplosion;
	// fuse time in seconds
	int fuseTime;
	// material of bomb
	Material bombMaterial;
	
	Double velocity;
	
	//constructor
	public Bomb(String name, int powerOfExplosion, int fuseTime, Material bombMaterial, double velocity){
		this.name = name;
		this.powerOfExplosion = powerOfExplosion;
		this.fuseTime = fuseTime;
		this.bombMaterial = bombMaterial;
		this.velocity = velocity;
	}
	
	// getter methods
	
	public Double getVelocity(){
		return velocity;
	}
	public String getName() {
		return name;
	}
	
	public Integer getPower(){
		return powerOfExplosion;
	}
	
	public Integer getFuseTime(){
		return fuseTime;
	}
	
	public Material getBombMaterial(){
		return bombMaterial;
	}

	// writes the bomb object to a file to retrieve later
	public void writeToFile() {
		// setting bomb name in a list so we can use to for-loop when retreiving 
		FileConfiguration file = BombsPlugin.instance.getBombFile();
		List<String>bombNames = file.getStringList("bomb-names");
		bombNames.add(getName());
		file.set("bomb-names", bombNames);
		// setting fields
		file.set("bombs."+getName()+".explosion-power", getPower());
		file.set("bombs."+getName()+".fuse-time", getFuseTime());
		file.set("bombs."+getName()+".material", getBombMaterial().toString());
		file.set("bombs."+getName()+".velocity", getVelocity());
		BombsPlugin.instance.saveBombFile();


	}
	
	public static Bomb getBomb(ItemStack i){
		for(Bomb b : BombsPlugin.instance.bombsStorage){
			if(i.isSimilar(b.getItemStack())){
				return b;
			}
		}
		return null; 
	}
	public static Bomb getBomb(String name){
		for (Bomb b : BombsPlugin.instance.bombsStorage){
			if(b.getName().equalsIgnoreCase(name)){
				return b;
			}
		}
		return null;
	}
	public void delete(){
		FileConfiguration file = BombsPlugin.instance.getBombFile();
		file.getStringList("bomb-names").remove(getName());
		file.set("bombs."+getName(), null);
		BombsPlugin.instance.saveBombFile();
		BombsPlugin.instance.bombsStorage.remove(this);
	}
	public void giveBomb(Player p, int amount){
		String name = ChatColor.translateAlternateColorCodes('&', getName());
		ItemStack bomb = new ItemStack(getBombMaterial());
		ItemMeta bm = bomb.getItemMeta();
		bm.setDisplayName(name);
		ArrayList<String> lores = new ArrayList<>();
		lores.add(0, Messages.getValue("create-first-line-lore"));
		lores.add(1,ChatColor.GRAY + "Explosion-power : " + getPower().toString());
		lores.add(2,ChatColor.GRAY +"Fuse-time : "+ getFuseTime().toString());
		lores.add(3,ChatColor.GRAY + "Velocity : " +getVelocity().toString());
		bm.setLore(lores);
		bomb.setItemMeta(bm);
		for(int i =0; i <amount; i++){
			p.getInventory().addItem(bomb);
		}
	}
	public static boolean isBomb(ItemStack i){
		for(Bomb b : BombsPlugin.instance.bombsStorage) {
			if(i.isSimilar(b.getItemStack())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isBomb(Item i){
		for(Bomb b : BombsPlugin.instance.bombsStorage) {
			if(i.getItemStack().isSimilar(b.getItemStack())){
				return true;
			}
		}
		return false;
	}
	public ItemStack getItemStack(){
		String name = ChatColor.translateAlternateColorCodes('&', getName());
		ItemStack bomb = new ItemStack(getBombMaterial());
		ItemMeta bm = bomb.getItemMeta();
		bm.setDisplayName(name);
		ArrayList<String> lores = new ArrayList<>();
		lores.add(0, Messages.getValue("create-first-line-lore"));
		lores.add(1,ChatColor.GRAY + "Explosion-power : " + getPower().toString());
		lores.add(2,ChatColor.GRAY +"Fuse-time : "+ getFuseTime().toString());
		lores.add(3,ChatColor.GRAY + "Velocity : " +getVelocity().toString());
		bm.setLore(lores);
		bomb.setItemMeta(bm);
		return bomb;
	}
	
}
