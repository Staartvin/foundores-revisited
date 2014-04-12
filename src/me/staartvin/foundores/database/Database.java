package me.staartvin.foundores.database;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import me.staartvin.foundores.database.DatabaseConnector.blockTypes;

import com.sun.istack.internal.NotNull;

@Entity()
@Table(name = "fo_blocks")
public class Database {

	@Id
    private int id;
    
    @NotNull
    private String UUID;
    
    @NotNull
    private String world;
    
    @NotNull
    private int brokenStone;
    
    @NotNull
    private int brokenCoal;
    
    @NotNull
    private int brokenIron;
    
    @NotNull
    private int brokenGold;
    
    @NotNull
    private int brokenRedstone;
    
    @NotNull
    private int brokenLapisLazuli;
    
    @NotNull
    private int brokenDiamond;
    
    @NotNull
    private int brokenEmerald;
    
    @NotNull
    private int brokenNetherQuartz;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUUID() {
		return UUID;
	}
	
	public UUID getRealUUID() {
		return java.util.UUID.fromString(UUID);
	}

	public void setUUID(String uuid) {
		this.UUID = uuid;
	}
	
	public void setBrokenStone(int count) {
		brokenStone = count;
	}
	
	public void setBrokenCoal(int count) {
		brokenCoal = count;
	}
	
	public void setBrokenIron(int count) {
		brokenIron = count;
	}
	
	public void setBrokenGold(int count) {
		brokenGold = count;
	}
	
	public void setBrokenRedstone(int count) {
		brokenRedstone = count;
	}
	
	public void setBrokenLapisLazuli(int count) {
		brokenLapisLazuli = count;
	}
	
	public void setBrokenDiamond(int count) {
		brokenDiamond = count;
	}
	
	public void setBrokenEmerald(int count) {
		brokenEmerald = count;
	}
	
	public void setBrokenNetherQuartz(int count) {
		brokenNetherQuartz = count;
	}
	
	public int getBrokenStone() {
		return brokenStone;
	}
	
	public int getBrokenCoal() {
		return brokenCoal;
	}
	
	public int getBrokenIron() {
		return brokenIron;
	}
	
	public int getBrokenGold() {
		return brokenGold;
	}
	
	public int getBrokenRedstone() {
		return brokenRedstone;
	}
	
	public int getBrokenLapisLazuli() {
		return brokenLapisLazuli;
	}
	
	public int getBrokenDiamond() {
		return brokenDiamond;
	}
	
	public int getBrokenEmerald() {
		return brokenEmerald;
	}
	
	public int getBrokenNetherQuartz() {
		return brokenNetherQuartz;
	}
	
	
	public int getBrokenCount(blockTypes blockType) {
		switch(blockType) {
		case STONE:
			return getBrokenStone();
		case COAL:
			return getBrokenCoal();
		case IRON:
			return getBrokenIron();
		case GOLD:
			return getBrokenGold();
		case LAPIS_LAZULI:
			return getBrokenLapisLazuli();
		case EMERALD:
			return getBrokenEmerald();
		case REDSTONE:
			return getBrokenRedstone();
		case DIAMOND:
			return getBrokenDiamond();
		case NETHER_QUARTZ:
			return getBrokenNetherQuartz();
		default:
			return -1;
		}
	}
	
	public void setBrokenCount(blockTypes blockType, int count) {
		switch(blockType) {
		case STONE:
			setBrokenStone(count);
			return;
		case COAL:
			setBrokenCoal(count);
			return;
		case IRON:
			setBrokenIron(count);
			return;
		case GOLD:
			setBrokenGold(count);
			return;
		case LAPIS_LAZULI:
			setBrokenLapisLazuli(count);
			return;
		case EMERALD:
			setBrokenEmerald(count);
			return;
		case REDSTONE:
			setBrokenRedstone(count);
			return;
		case DIAMOND:
			setBrokenDiamond(count);
			return;
		case NETHER_QUARTZ:
			setBrokenNetherQuartz(count);
			return;
		default:
			return;	
		}
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}
}
