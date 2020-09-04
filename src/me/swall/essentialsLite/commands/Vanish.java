package me.swall.essentialsLite.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.swall.essentialsLite.Main;

public class Vanish implements CommandExecutor, Listener
{
	private Main plugin;
	public Vanish(Main plugin)
	{
		this.plugin = plugin;
	}
	private boolean visibility = true;
	
	private boolean getPlayerVisibility(Player p) throws FileNotFoundException, IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader("essentialsLite/playerStates.txt"));
		String line = reader.readLine();
		if (line.charAt(0) == '-')
		{
			String[] args = line.split(" ");
			if (args.length > 1)
			{
				if (args[1].equalsIgnoreCase(p.getDisplayName()))
				{
						
				}
			}
		}
		return false;
	}
	
	private void setPlayerVisibility(Player p, boolean v)
	{
		
	}
	
	@EventHandler
	public void onPlayerJoin()
	{
		
	}
	
	@EventHandler
	public void onPlayerQuit()
	{
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = (Player) sender;
		if (sender instanceof Player)
		{
			PotionEffect effect = new PotionEffect(PotionEffectType.getByName("invisibility"), Integer.MAX_VALUE,
					1, false, false);
			boolean isVisible = true;
			
			try
			{
				 isVisible = getPlayerVisibility(player);
			}
			catch (FileNotFoundException e)
			{
				Bukkit.getLogger().warning("playerStates.txt wasn't found when " + player.getDisplayName() +" attempted to vanish.");
			}
			catch (IOException i)
			{
				Bukkit.getLogger().warning("there was a problem opening playerStates.txt when " + player.getDisplayName() +" attempted to vanish.");
			}
			
			if (isVisible)
			{
				player.addPotionEffect(effect);
				setPlayerVisibility(player, false);
			}
			else
			{
				player.removePotionEffect(PotionEffectType.getByName("Invisibility"));
				setPlayerVisibility(player, false);
			}
		}
		else
		{
			sender.sendMessage("This command can only be executed by players.");
		}
		return false;
	}
	
	
}
