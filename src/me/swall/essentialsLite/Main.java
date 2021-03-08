package me.swall.essentialsLite;

import org.bukkit.plugin.java.JavaPlugin;

import me.swall.essentialsLite.commands.Fly;
import me.swall.essentialsLite.commands.FlySpeed;
import me.swall.essentialsLite.commands.WalkSpeed;

public class Main extends JavaPlugin {
	@Override
	public void onEnable()
	{
		new Fly(this);
		new FlySpeed(this);
		new WalkSpeed(this);
	}
	
	public void onDisable()
	{
		
	}
}
