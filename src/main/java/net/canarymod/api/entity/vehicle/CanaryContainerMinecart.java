package net.canarymod.api.entity.vehicle;

import java.util.Arrays;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.config.Configuration;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityMinecartContainer;
import net.minecraft.server.ItemStack;

/**
 * @author Somners
 */
public abstract class CanaryContainerMinecart extends CanaryMinecart implements ContainerMinecart {
    private boolean openRemote;

    public CanaryContainerMinecart(EntityMinecart minecart) {
        super(minecart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(ItemType type) {
        this.addItem(type.getId(), 1, (short) 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId) {
        this.addItem(itemId, 1, (short) 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId, short damage) {
        this.addItem(itemId, 1, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId, int amount) {
        this.addItem(itemId, amount, (short) 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(ItemType type, int amount) {
        this.addItem(type.getId(), amount, (short) 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId, int amount, short damage) {
        this.addItem(new CanaryItem(itemId, amount, damage));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(ItemType type, int amount, short damage) {
        this.addItem(type.getId(), amount, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(Item item) {
        this.insertItem(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseItemStackSize(int itemId, int amount) {
        Item[] items = getContents();
        int remaining = amount;

        for (Item item : items) {
            if (item.getId() == itemId) {
                if (item.getAmount() == remaining) {
                    removeItem(item.getSlot());
                    return;
                } else if (item.getAmount() > remaining) {
                    item.setAmount(item.getAmount() - remaining);
                    setSlot(item.getSlot(), item);
                    return;
                } else {
                    removeItem(item.getSlot());
                    remaining -= item.getAmount();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseItemStackSize(int itemId, int amount, short damage) {
        this.decreaseItemStackSize(new CanaryItem(itemId, amount, damage));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseItemStackSize(Item item) {
        Item[] items = getContents();
        int remaining = item.getAmount();

        for (Item it : items) {
            if (it.getId() == item.getId() && it.getDamage() == item.getDamage()) {
                if (it.getAmount() == remaining) {
                    removeItem(it.getSlot());
                    return;
                } else if (it.getAmount() > remaining) {
                    it.setAmount(it.getAmount() - remaining);
                    setSlot(it.getSlot(), it);
                    return;
                } else {
                    removeItem(it.getSlot());
                    remaining -= it.getAmount();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEmptySlot() {
        for (int index = 0; index < getSize(); index++) {
            if (getSlot(index) == null) {
                return index;
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInventoryName() {
        return this.getHandle().b();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInventoryStackLimit() {
        return this.getHandle().d();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(ItemType type) {
        return this.getItem(type.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(int id) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && toCheck.getId() == id) {
                toCheck.setSlot(index);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(ItemType type, int amount) {
        return this.getItem(type.getId(), amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(int id, int amount) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && toCheck.getId() == id && toCheck.getAmount() == amount) {
                toCheck.setSlot(index);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(int id, int amount, short damage) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && toCheck.getId() == id && toCheck.getAmount() == amount && toCheck.getDamage() == damage) {
                toCheck.setSlot(index);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(ItemType type, int amount, short damage) {
        return this.getItem(type.getId(), amount, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.getHandle().j_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getSlot(int index) {
        ItemStack stack = getHandle().a(index);

        return stack != null ? stack.getCanaryItem() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(int itemId) {
        for (int index = 0; index < getSize(); index++) {
            if (getSlot(index) != null && getSlot(index).getId() == itemId) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(ItemType type) {
        return this.hasItem(type.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(ItemType type, short damage) {
        return this.hasItem(type.getId(), damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(int itemId, short damage) {
        for (int index = 0; index < getSize(); index++) {
            Item item = getSlot(index);

            if (item != null && item.getId() == itemId && item.getDamage() == damage) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(ItemType type, int amount) {
        return this.hasItemStack(type.getId(), amount, 64);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(int itemId, int amount) {
        return this.hasItemStack(itemId, amount, 64);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(ItemType type, int amount, int damage) {
        return this.hasItemStack(type.getId(), amount, 64, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(int itemId, int amount, int damage) {
        return hasItemStack(itemId, amount, 64, damage);
    }

    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // public boolean hasItemStack(int itemId, int minAmount, int maxAmount) {
    // for (int index = 0; index < getSize(); index++) {
    // Item toCheck = getSlot(index);
    //
    // if (toCheck != null && toCheck.getId() == itemId) {
    // int am = toCheck.getAmount();
    //
    // if (am > minAmount && am < maxAmount) {
    // return true;
    // }
    // }
    // }
    // return false;
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(int itemId, int minAmount, int maxAmount, int damage) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && toCheck.getId() == itemId && toCheck.getDamage() == damage) {
                int am = toCheck.getAmount();

                if (am > minAmount && am < maxAmount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertItem(Item item) {
        int amount = item.getAmount();
        Item itemExisting;
        int maxAmount = item.getMaxAmount();

        while (amount > 0) {
            // Get an existing item with at least 1 spot free
            itemExisting = this.getItem(item.getId(), maxAmount - 1, (short) item.getDamage());

            // Add the items to the existing stack of items
            if (itemExisting != null && (!item.isEnchanted() || Configuration.getServerConfig().allowEnchantmentStacking())) {
                // Add as much items as possible to the stack
                int k = Math.min(maxAmount - itemExisting.getAmount(), item.getAmount());

                this.setSlot(item.getId(), itemExisting.getAmount() + k, (short) item.getDamage(), itemExisting.getSlot());
                amount -= k;
                continue;
            }
            // We still have slots, but no stack, create a new stack.
            int eslot = this.getEmptySlot();

            if (eslot != -1) {
                CanaryCompoundTag nbt = new CanaryCompoundTag("");

                ((CanaryItem) item).getHandle().b(nbt.getHandle());
                Item tempItem = new CanaryItem(item.getId(), amount, -1, item.getDamage());

                ((CanaryItem) tempItem).getHandle().c(nbt.getHandle());
                this.setSlot(eslot, tempItem);
                amount = 0;
                continue;
            }

            // No free slots, no incomplete stacks: full
            // make sure the stored items are removed
            item.setAmount(amount);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int itemId, int amount, int slot) {
        this.setSlot(itemId, amount, (short) 0, slot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int itemId, int amount, int damage, int slot) {
        CanaryItem item = new CanaryItem(itemId, 1, damage);

        item.setSlot(slot);
        this.setSlot(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(ItemType type, int amount, int slot) {
        this.setSlot(type.getId(), amount, (short) 0, slot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(ItemType type, int amount, int damage, int slot) {
        this.setSlot(type.getId(), amount, damage, slot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(Item item) {
        this.setSlot(item.getSlot(), item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int index, Item value) {
        this.getHandle().a(index, ((CanaryItem) value).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(Item item) {
        return this.removeItem(item.getId(), (short) item.getDamage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(int id) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && toCheck.getId() == id) {
                setSlot(index, null);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(int id, int damage) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && toCheck.getId() == id && toCheck.getDamage() == damage) {
                setSlot(index, null);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(ItemType type) {
        return this.removeItem(type.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(ItemType type, short damage) {
        return this.removeItem(type.getId(), damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContents() {
        Arrays.fill(this.getHandle().a, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(this.getHandle().a, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(this.getHandle().a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContents(Item[] items) {
        System.arraycopy(CanaryItem.itemArrayToStackArray(items), 0, this.getHandle().a, 0, getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInventoryName(String value) {
        this.getHandle().a(value);
    }

    @Override
    public void update() {
        this.getHandle().k_();
    }

    public boolean canOpenRemote() {
        return openRemote;
    }

    public void setCanOpenRemote(boolean remote) {
        openRemote = remote;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityMinecartContainer getHandle() {
        return (EntityMinecartContainer) entity;
    }
}
