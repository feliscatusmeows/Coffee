package me.x150.sipprivate.feature.module.impl.combat;

import me.x150.sipprivate.CoffeeClientMain;
import me.x150.sipprivate.feature.module.Module;
import me.x150.sipprivate.feature.module.ModuleType;
import me.x150.sipprivate.helper.event.EventType;
import me.x150.sipprivate.helper.event.Events;
import me.x150.sipprivate.helper.event.events.MouseEvent;
import me.x150.sipprivate.helper.util.Utils;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.Objects;


public class ArmorSwitch extends Module {
    //paste atomic
    public ArmorSwitch() {
        super("ArmorSwitch", "Allows you to put armor on with right click even if you have armor on", ModuleType.COMBAT);
        Events.registerEventHandler(EventType.MOUSE_EVENT, event -> {
            if (!this.isEnabled() || CoffeeClientMain.client.currentScreen != null) {
                return;
            }
            MouseEvent me = (MouseEvent) event;
            if (me.getButton() == 1 && me.getAction() == 1) {
                putArmor();
            }
        });
    }

    void putArmor() {
        // ashleydev
        ItemStack selected = Objects.requireNonNull(CoffeeClientMain.client.player).getInventory().getMainHandStack();
        int slotToPut = -1;
        if (selected.getItem() instanceof ArmorItem ai) {
            switch (ai.getSlotType()) {
                case HEAD -> slotToPut = 5;
                case CHEST -> slotToPut = 6;
                case LEGS -> slotToPut = 7;
                case FEET -> slotToPut = 8;
            }
        } else if (Arrays.stream(new Item[]{Items.CREEPER_HEAD, Items.DRAGON_HEAD, Items.PLAYER_HEAD, Items.ZOMBIE_HEAD}).anyMatch(item -> item == selected.getItem())) {
            slotToPut = 5;
        } else if (selected.getItem() == Items.ELYTRA) {
            slotToPut = 6;
        }
        if (slotToPut == -1) {
            return;
        }
        Utils.Inventory.moveStackToOther(Utils.Inventory.slotIndexToId(CoffeeClientMain.client.player.getInventory().selectedSlot), slotToPut);

    }

    @Override public void tick() {

    }

    @Override public void enable() {

    }

    @Override public void disable() {

    }

    @Override public String getContext() {
        return null;
    }

    @Override public void onWorldRender(MatrixStack matrices) {

    }

    @Override public void onHudRender() {

    }
}
