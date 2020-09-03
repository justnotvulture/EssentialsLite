package me.swall.essentialsLite;

import org.bukkit.plugin.java.JavaPlugin;
import me.swall.essentialsLite.commands.Fly;
import me.swall.essentialsLite.commands.FlySpeed;

public class Main extends JavaPlugin {
	@Override
	public void onEnable()
	{
		new Fly(this);
		new FlySpeed(this);
	}
}
