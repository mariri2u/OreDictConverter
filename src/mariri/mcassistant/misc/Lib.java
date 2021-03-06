package mariri.mcassistant.misc;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Lib {
	public static EnumToolMaterial getMaterial(String material){
		EnumToolMaterial[] marr = EnumToolMaterial.values();
		EnumToolMaterial mm = null;
		for(EnumToolMaterial m : marr){
			if(m.name().equals(material)){
				mm = m;
			}
		}
		return mm;
	}
	
	public static EnumToolMaterial getMaterial(Item item){
		EnumToolMaterial m = null;
		if(item != null){
			if(item instanceof ItemTool){
				m = getMaterial(((ItemTool)item).getToolMaterialName());
			}else if(item instanceof ItemHoe){
				m = getMaterial(((ItemHoe)item).getMaterialName());
			}else if(item instanceof ItemSword){
				m = getMaterial(((ItemSword)item).getToolMaterialName());
			}
		}
		return m;
	}
	
	public static boolean compareCurrentToolLevel(EntityPlayer player, int level){
		boolean result = false;
		try{
			EnumToolMaterial material = getMaterial(player.getCurrentEquippedItem().getItem());
			result = material.getHarvestLevel() >= level;
		}catch(NullPointerException e){}
		return result;
	}
	
	public static int getPotionAffectedLevel(EntityLivingBase entity, int id){
		int result = 0;;
		PotionEffect effect = entity.getActivePotionEffect(Potion.potionTypes[id]);
		if(effect != null){
			result |= effect.getAmplifier() + 1;
		}
		return result;
	}
	
	public static boolean isPotionAffected(EntityLivingBase entity, int id, int lv){
		boolean result = false;;
		PotionEffect effect = entity.getActivePotionEffect(Potion.potionTypes[id]);
		if(lv <= 0){
			result |= true;
		}else{
			result |= getPotionAffectedLevel(entity, id) >= lv;
		}
		return result;
	}
	
	public static boolean isPotionAffected(EntityLivingBase entity, int[] pot){
		if(pot != null && pot.length == 2){
			return isPotionAffected(entity, pot[0], pot[1]);
		}else{
			return true;
		}
	}
	
	public static void spawnItem(World world, double x, double y, double z, ItemStack itemstack){
	    float f = 0.7F;
	    double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	    double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	    double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	    EntityItem entityitem = 
	    		new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, itemstack);
	    entityitem.delayBeforeCanPickup = 10;
	    world.spawnEntityInWorld(entityitem);
	}
	
	public static void spawnItem(World world, double x, double y, double z, List<ItemStack> itemstack){
		for(ItemStack item : itemstack){
			spawnItem(world, x, y, z, item);
		}
	}
	
    public static String[] splitAndTrim(String str, String separator){
        String[] aaa = str.split(separator);
        String[] ids = new String[aaa.length];
        if("".equals(str)){
        	ids = null;
        }else{
	        for(int i = 0; i < aaa.length; i++){
	        	ids[i] = aaa[i].trim();
	        }
        }
        return ids;
    }

    public static int[] stringToInt(String str, String separator) throws NumberFormatException{
        String[] aaa = str.split(separator);
        int[] ids = new int[aaa.length];
        if("".equals(str)){
        	ids = null;
        }else{
	        for(int i = 0; i < aaa.length; i++){
	        	ids[i]= Integer.parseInt(aaa[i].trim());
	        }
        }
        return ids;
    }
    
    public static int[][] stringToInt(String str, String separator1, String separator2) throws NumberFormatException{
    	String[] aaa = str.split(separator1);
    	int[][] ids = new int[aaa.length][];
        if("".equals(str)){
        	ids = null;
        }else{
	        for(int i = 0; i < aaa.length; i++){
	    		int[] s = stringToInt(aaa[i], separator2);
	    		ids[i] = new int[2];
	    		ids[i][0] = s[0];
	    		ids[i][1] = (s.length >= 2) ? s[1] : 0;
	    	}
        }
    	return ids;
    }
}
