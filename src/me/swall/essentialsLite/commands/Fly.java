package me.swall.essentialsLite.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.swall.essentialsLite.Main;

public class Fly implements CommandExecutor
{
	private Main plugin;
	
	public Fly(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("fly").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("essentialsLite.fly"))
			{
				player.setAllowFlight(!player.getAllowFlight());
			}
		}
		else 
		{
			sender.sendMessage("This command can only be executed by in-game players.");
		}
		return false;
	}

}
