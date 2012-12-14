/*
 *
 * Future Code Here
 *
 */
public class PotionDetect {
 private ItemStack potion;
 private short durability;
 private Player player;
 //TODO: Continue adding transition methods based on REF POST (http://forums.bukkit.org/threads/potion-durability-damage-value-from-projectilehitevent.114082/)
 public PotionDetect(ProjectileHitEvent e) {
   if (e.getEntityType() != EntityType.SPLASH_POTION)
            return ;
  this.player = ((ThrownPotion)event.getEntity()).getShooter();
   EntityPotion ent = (EntityPotion)((CraftEntity)e.getEntity()).getHandle();
        try {
            Field d = EntityPotion.class.getDeclaredField("d");
            d.setAccessible(true);
            potion = new CraftItemStack((net.minecraft.server.ItemStack) d.get(ent));
        }
        catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        catch (SecurityException e1) {
            e1.printStackTrace();
        }
        catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        
        if (potion != null){
            this.durability = potion.getDurability();
        }
 }
}
