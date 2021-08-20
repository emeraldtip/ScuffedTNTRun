package CpnSparklezSurprise.TntGame;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener{
	
	private World tntrun;
	private BukkitTask timer;
	private BukkitTask ticker;
	private boolean  bruh = false, running = false;
	private int count, ticcount, tickcounter, tntcounter = 1;
	//timing ticks (ik this is brain dead I don't have a brain ok)
	//Map<Player,List<Location>> playerlocs = new HashMap<>();
	ArrayList<Location> playerlocs = new ArrayList<>();
	ArrayList<String> playas = new ArrayList<>();

	
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription(); 
		Logger logger = Logger.getLogger("Minecraft");
		logger.info("[" + pdfFile.getName() + "] " + pdfFile.getName() + " has been enabled! " + "(v" + pdfFile.getVersion() + ")");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		tntrun = Bukkit.getServer().getWorld("tntrun");
		//blocc
		//bloc1 = new Location(world,1440,71,-561);
		//bloc2 = new Location(world,1441,71,-561);
		//bloc3 = new Location(world,1442,71,-561);
		//bloc4 = new Location(world,1443,71,-561);
		//bloc5 = new Location(world,1444,71,-561);
		//bloc6 = new Location(world,1445,71,-561);
	}
	
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.stopSound(Sound.MUSIC_DISC_CAT);
		}
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");
		logger.info("[" + pdfFile.getName() + "] " + pdfFile.getName() + " has been disabled! " + "(v" + pdfFile.getVersion() + ")");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("exteme.bruh.moment"))
		{
			if (cmd.getName().equalsIgnoreCase("test")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
			  		if (p.getWorld().getName().equals("tntrun")){
			  			p.playSound(p.getLocation(),Sound.MUSIC_DISC_CAT,10,1);
			  		}
				}
				BukkitScheduler scheduler = getServer().getScheduler();
				scheduler.scheduleSyncDelayedTask(this, new Runnable() {
	    			public void run() {
	    				bruh();
	    				tickcounter();
	    				running = true;
	    				
	    			}
	    		}, 40);
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("sotp")) {
				cancelEvent();
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("run")) 
		{
			Player p = (Player) sender;
			p.setFoodLevel(20);
			if (running)
			{
				sender.sendMessage("§4§lThe game is already running");
			}
			else
			{
				if(playas.contains(p.getName()))
				{
					sender.sendMessage("§4§lYou are already in the game");
				}
				else
				{
					playas.add(p.getName());
					p.setGameMode(GameMode.ADVENTURE);
					sender.sendMessage("§a§lGame joined");
				}
			}
		}
		return true;
		
	}
	public void cancelEvent()
	{
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.stopSound(Sound.MUSIC_DISC_CAT);
		}
		bruh = true;
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				bruh = true;
				running = false;
				playas.clear();
			}
		}, 40);
	}
	public void tickcounter()
	{
		for (Player p : Bukkit.getWorld("tntrun").getPlayers()) {
			//p.sendMessage(Integer.toString(playerlocs.size()));
			//playerlocs.put(p,new ArrayList());
	}
		ticcount = 4000;
		ticker = Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        {
			public void run()
			{
				if (ticcount==3320 || ticcount==2200 || ticcount==1000 || ticcount==960 || ticcount==920 || ticcount==680) 
				{
					for (Player p : Bukkit.getWorld("tntrun").getPlayers())
					{
						if(playas.contains(p.getName()))
						{
							tntrun.spawnEntity(p.getLocation().clone().add(0,5,0), EntityType.ARROW);
						}
					}
				}
				for (Player p : Bukkit.getWorld("tntrun").getPlayers()) {
					if(playas.contains(p.getName()))
					{
						playerlocs.add(p.getLocation());
						if(p.getLocation().getY() < 95)
						{
							playas.remove(p.getName());
							p.setGameMode(GameMode.SPECTATOR);
							p.sendTitle("§4You died", "§cbruh moment",20,60,20);
							if(playas.size()<1)
							{
								for (Player b : Bukkit.getWorld("tntrun").getPlayers())
								{
									b.stopSound(Sound.MUSIC_DISC_CAT);
									b.sendTitle("§4Game failed", "§cbruh moment",20,60,20);
								}
								cancelEvent();
								return;
							}
						}
							
					}
						//p.sendMessage(Integer.toString(playerlocs.size()));
						//playerlocs.put(p,p.getLocation());
				}
				if (ticcount<=0)
				{
					for (Player b : Bukkit.getWorld("tntrun").getPlayers())
					{
						b.sendTitle("§aYou won!", "§aPogchamp moment",20,60,20);
						
					}
					playas.clear();
					running = false;
					ticker.cancel();
					return;	
				}
				if (bruh == true)
				{
					bruh = false;
					ticker.cancel();
					return;
				}
				
				tickcounter ++;
				ticcount --;
				return;
				
			}
        
        }, 0L,1L);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		if(e.getPlayer().getWorld().getName() == "tntrun")
		{
			if(playas.contains(e.getPlayer().getName()))
			{
				playas.remove(e.getPlayer().getName());
				if(playas.size()<1)
				{
					for (Player b : Bukkit.getWorld("tntrun").getPlayers())
					{
						b.stopSound(Sound.MUSIC_DISC_CAT);
						b.sendTitle("§4Game failed", "§cbruh moment",20,60,20);
						
					}
					cancelEvent();
					return;
				}
			}
		}
		
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		if(e.getEntity().getPlayer().getWorld().getName() == "tntrun")
		{
			if(playas.contains(e.getEntity().getPlayer().getName()))
			{
				playas.remove(e.getEntity().getPlayer().getName());
				if(playas.size()<1)
				{
					for (Player b : Bukkit.getWorld("tntrun").getPlayers())
					{
						b.stopSound(Sound.MUSIC_DISC_CAT);
						b.sendTitle("§4Game failed", "§cbruh moment",20,60,20);
						
					}
					cancelEvent();
					return;
				}
			}
		}
	}
	@EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
		if(event.getEntity().getWorld() == tntrun)
		{
			event.setCancelled(true);
		}
    }
	public void bruh() 
	{
		count = 400;
		tntcounter = 1;
		timer = Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        {
			public void run()
			{
				if (count<=0)
				{
					timer.cancel();
					return;
				}
				if (bruh == true)
				{
					bruh = false;
					timer.cancel();
					return;
				}
				boolean check = count % 4 == 0;
				for (Player p: Bukkit.getWorld("tntrun").getPlayers())
				{
					if (check)
					{
						//p.sendMessage(Integer.toString(count));
					  	//p.sendMessage(Integer.toString(ticcount));
			  			//p.playSound(p.getLocation(),Sound.BLOCK_STONE_BUTTON_CLICK_ON,100,10);
						//playerlocs.get(p).getBlock().setType(Material.EMERALD_BLOCK);
					}
					else
		  			{
		  				//p.playSound(p.getLocation(),Sound.BLOCK_STONE_BUTTON_CLICK_OFF,100,1.5f);
		  			}
				}
				if (check)
				{
					tntcounter += 1;
					for(Location l : playerlocs)
	  				{
						Location loc = l.getBlock().getRelative(BlockFace.DOWN).getLocation();
						loc.getBlock().setType(Material.AIR);
	  					if(loc.clone().subtract(0,3,0).getBlock().getType() == Material.AIR)
	  					{
	  						if(loc.getX()-Math.floor(loc.getX()) < 0.5)
	  						{
	  							loc.clone().subtract(-1,1,0).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(-1,2,0).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(-1,3,0).getBlock().setType(Material.AIR);
	  						}
	  						if(loc.getX()-Math.floor(loc.getX()) > 0.5)
	  						{
	  							loc.clone().subtract(1,1,0).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(1,2,0).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(1,3,0).getBlock().setType(Material.AIR);
	  						}
	  						if(loc.getY()-Math.floor(loc.getY()) < 0.5)
	  						{
	  							loc.clone().subtract(0,1,-1).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(0,2,-1).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(0,3,-1).getBlock().setType(Material.AIR);
	  						}
	  						if(loc.getY()-Math.floor(loc.getY()) > 0.5)
	  						{
	  							loc.clone().subtract(0,1,1).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(0,2,1).getBlock().setType(Material.AIR);
	  		  					loc.clone().subtract(0,3,1).getBlock().setType(Material.AIR);
	  						}
	  						loc.clone().subtract(0,1,0).getBlock().setType(Material.AIR);
		  					loc.clone().subtract(0,2,0).getBlock().setType(Material.AIR);
		  					loc.clone().subtract(0,3,0).getBlock().setType(Material.AIR);
	  					}
	  				}
					playerlocs.clear();
				}
				if (tntcounter % 9 == 0)
				{
					tntcounter = 1;
					double distanc = 75*Math.sqrt(Math.random());
					double theta = Math.random()*2*Math.PI;
					Location locat = new Location(Bukkit.getWorld("tntrun"),distanc*Math.cos(theta),123,distanc*Math.sin(theta));
					//Bukkit.getPlayer("Emerald_tip").sendMessage(locat.toString());
					tntrun.spawnEntity(locat, EntityType.PRIMED_TNT);
				}
				count--;
				return;
				}	
			
				
		}, 0L,10L);
		
    }
}
