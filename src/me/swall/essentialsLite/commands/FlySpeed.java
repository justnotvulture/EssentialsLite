package me.swall.essentialsLite.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.swall.essentialsLite.Main;

public class FlySpeed implements CommandExecutor 
{
	private Main plugin;
	
	public FlySpeed(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("flySpeed").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{	
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command must be executed by an in-game player.");
			return true;
		}
		else 
		{	
			Player player = (Player) sender;
			if (player.hasPermission("essentialsLite.flySpeed"))
			{				
				//check the first argument to ensure it is an integer
				boolean hasExtTarget = false;
				for (int i = 0; i < args[0].length(); i++)
				{
					if (!Character.isDigit(args[0].charAt(i)))
					{
						hasExtTarget = true;
					}
				}
				
				if (!hasExtTarget)
				{
					float flySpeed = (float)(Integer.parseInt(args[0]) / 10.0);
					if (Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 0)
					{
						sender.sendMessage("The speed argument must be a whole number between 0 and 10.");
					}
					else
					{
						//set the sender's fly speed to the value provided as the first argument
						player.setFlySpeed(flySpeed);
						sender.sendMessage("Your fly speed was set to " + args[0] + ".");
					}
				}
				else 
				{
					float flySpeed = (float)(Integer.parseInt(args[1]) / 10.0);
					Bukkit.getLogger().info(player.getDisplayName() + " targeted " + args[0] + " with the flySpeed command.");
					if (Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 0)
					{
						sender.sendMessage("The speed argument must be a whole number between 0 and 10.");
					}
					else
					{
						//set the target player's fly speed to the value provided as the second argument
						Player targetPlayer = Bukkit.getPlayerExact(args[0]);
						Bukkit.getLogger().info("targetPlayer was initialized.");
						if (targetPlayer != null && player.isOnline())
						{
							targetPlayer.setFlySpeed(flySpeed);
							Bukkit.getLogger().info("The fly speed for user " + player.getDisplayName() 
							+ " has been set to" + args[0]);
						}
						else
						{
							sender.sendMessage("That player was not found.");
						}
					}
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
