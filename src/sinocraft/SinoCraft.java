package sinocraft;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.ForgeDirection;

import sinocraft.core.SCCreativeTab;
import sinocraft.core.SCWorldGenerator;
import sinocraft.core.proxy.ServerProxy;
import sinocraft.core.register.SCBlocks;
import sinocraft.core.register.SCConfig;
import sinocraft.core.register.SCCraftingRecipes;
import sinocraft.core.register.SCFurnaceRecipes;
import sinocraft.core.register.SCItems;
import sinocraft.core.register.SCRenderer;
import sinocraft.plants.blocks.BlockChrysanthemum;
import sinocraft.plants.blocks.BlockPeony;
import sinocraft.plants.blocks.BlockPrunusMumeBranch;
import sinocraft.plants.blocks.BlockReed;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Item;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "SinoCraft", name = "SinoCraft", version = "1.0.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
/** 
 * MOD的主类
 * @author HopeAsd, Liong
 */
public class SinoCraft 
{
	public static SCConfig config;

	public static Logger log = FMLLog.getLogger();

	public static CreativeTabs sct = new SCCreativeTab("SC");
	
	@Instance("SinoCraft")
	public static SinoCraft instance;

	@SidedProxy(clientSide = "sinocraft.core.proxy.ClientProxy", serverSide = "sinocraft.core.proxy.ServerProxy")
	public static ServerProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		ModLoader.addLocalization("itemGroup.SC", "zh_CN", "龙腾东方：中华文化");
		ModLoader.addLocalization("itemGroup.SC", "en_US", "SinoCraft");
		
		config = new SCConfig(event.getSuggestedConfigurationFile());
		
		proxy.preLoad(event);
	}
	
	@Init
	public void init(FMLInitializationEvent event) 
	{
		SCRenderer.load(config);
		SCBlocks.load(config);
		SCItems.load(config);
		SCCraftingRecipes.load();
		SCFurnaceRecipes.load();
		
		GameRegistry.registerWorldGenerator(new SCWorldGenerator());
		
		proxy.load(event);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		config.SaveConfig();
		proxy.postLoad(event);
	}
}
