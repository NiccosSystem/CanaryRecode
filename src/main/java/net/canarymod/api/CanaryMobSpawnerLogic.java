package net.canarymod.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.canarymod.Canary;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.nbt.BaseTag;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.nbt.ListTag;
import net.minecraft.server.MobSpawnerBaseLogic;
import net.minecraft.server.NBTTagCompound;

/**
 * Implementation of MobSpawnerLogic
 *
 * @author Somners
 */
public class CanaryMobSpawnerLogic implements MobSpawnerLogic {

    private MobSpawnerBaseLogic logic;

    public CanaryMobSpawnerLogic(MobSpawnerBaseLogic msbl) {
        logic = msbl;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String[] getSpawns() {
        List<String> spawns = new ArrayList<String>();
        CanaryCompoundTag tag = new CanaryCompoundTag(new NBTTagCompound());

        logic.b(tag.getHandle());
        ListTag list = tag.getListTag("SpawnPotentials");

        if (list.isEmpty()) {
            return new String[]{logic.e()};
        }

        for (int i = 0; i < list.size(); i++) {
            spawns.add(((CompoundTag) list.get(i)).getString("id"));
        }
        return spawns.toArray(new String[spawns.size()]);
    }

    @Override
    public void setDelay(int delay) {
        logic.b = delay;
    }

    @Override
    public int getMinDelay() {
        return logic.g;
    }

    @Override
    public void setMinDelay(int delay) {
        logic.g = delay;
    }

    @Override
    public int getMaxDelay() {
        return logic.h;
    }

    @Override
    public void setMaxDelay(int delay) {
        logic.h = delay;
    }

    @Override
    public int getSpawnCount() {
        return this.logic.i;
    }

    @Override
    public void setSpawnCount(int count) {
        this.logic.i = count;
    }

    @Override
    public int getMaxNearbyEntities() {
        return this.logic.k;
    }

    @Override
    public void setMaxNearbyEntities(int entities) {
        this.logic.k = entities;
    }

    @Override
    public int getRequiredPlayerRange() {
        return this.logic.l;
    }

    @Override
    public void setRequiredPlayerRange(int range) {
        this.logic.l = range;
    }

    @Override
    public int getSpawnRange() {
        return this.logic.m;
    }

    @Override
    public void setSpawnRange(int range) {
        this.logic.m = range;
    }

    @Override
    public void setSpawnedEntities(MobSpawnerEntry... entries) {
        CanaryCompoundTag toSet = new CanaryCompoundTag(new NBTTagCompound());
        logic.b(toSet.getHandle());
        ListTag<BaseTag> list = Canary.factory().getNBTFactory().newListTag("SpawnPotentials");
        for (MobSpawnerEntry entry : entries) {
            list.add(entry.getSpawnPotentialsTag());
        }
        toSet.remove("SpawnPotentials");
        toSet.put("SpawnPotentials", list);
        logic.a(toSet.getHandle());
    }

    @Override
    public void addSpawnedEntities(MobSpawnerEntry... entries) {
        MobSpawnerEntry[] array = this.getSpawnedEntities();
        List<MobSpawnerEntry> list = new ArrayList<MobSpawnerEntry>();
        list.addAll(Arrays.asList(array));
        list.addAll(Arrays.asList(entries));
        this.setSpawnedEntities((MobSpawnerEntry[]) list.toArray(new MobSpawnerEntry[list.size()]));
    }

    @Override
    public MobSpawnerEntry[] getSpawnedEntities() {
        CanaryCompoundTag toSet = new CanaryCompoundTag(new NBTTagCompound());
        ArrayList<MobSpawnerEntry> array = new ArrayList<MobSpawnerEntry>();

        logic.b(toSet.getHandle());
        if (toSet.containsKey("SpawnPotentials")) {
            ListTag<?> list = toSet.getListTag("SpawnPotentials");
            CanaryCompoundTag tag;
            for (int i = 0; i < list.size(); i++) {
                tag = (CanaryCompoundTag) list.get(i);
                net.minecraft.server.Entity ent = ((CanaryEntity) Canary.factory().getEntityFactory().newEntity(tag.getString("id"))).getHandle();
                ent.setNBTProperties(((CanaryCompoundTag) tag.getCompoundTag("Properties")).getHandle());
                array.add(new CanaryMobSpawnerEntry(ent.getCanaryEntity()));
            }
        }
        return array.toArray(new MobSpawnerEntry[array.size()]);
    }
}
