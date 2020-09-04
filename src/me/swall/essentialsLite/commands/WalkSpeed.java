package me.swall.essentialsLite.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.swall.essentialsLite.Main;

public class WalkSpeed implements CommandExecutor
{
	private Main plugin;
	public WalkSpeed(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("walkSpeed").setExecutor(this);
	}
	
	private boolean isNumber(String in)
	{
		boolean isNumber = true;
		for (int i = 0; i < in.length(); i++)
		{
			char c = in.charAt(i);
		    if (!Character.isDigit(c))
			{
				isNumber = false;
			}
		}
		return isNumber;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command must be executed by an in-game player.");
			return true;
		}
		else 
		{	
			Player player = (Player) sender;
			if (player.hasPermission("essentialsLite.walkSpeed"))
			{	
				if (args.length > 0 && isNumber(args[0]))
				{
					if (args.length == 1)
					{
						float walkSpeed = (Float.parseFloat(args[0]) / (float)10.0);
						player.setWalkSpeed(walkSpeed);
					}
					else if (args[1].length() > 0)
					{
						float walkSpeed = (Float.parseFloat(args[0]) / (float)10.0);
						Player targetPlayer = Bukkit.getPlayerExact(args[1]);
						if (targetPlayer == null)
						{
							player.sendMessage("That player could not be found.");
						}
						else
						{
							targetPlayer.setWalkSpeed(walkSpeed);
						}
					}
				}
				else
				{
					player.sendMessage(args[0] + " is not a valid number.");
				}
			}
			else 
			{
				player.sendMessage("You do not have permission to use that command!");
			}		
		}
		return false;
	}
}
