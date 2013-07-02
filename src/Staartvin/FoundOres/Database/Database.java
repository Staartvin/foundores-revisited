package Staartvin.FoundOres.Database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "fo_blocks")
public class Database {

	@Id
    private int id;
    
    @NotNull
    private String playerName;
    
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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getBrokenStone() {
		return brokenStone;
	}

	public void setBrokenStone(int brokenStone) {
		this.brokenStone = brokenStone;
	}

	public int getBrokenCoal() {
		return brokenCoal;
	}

	public void setBrokenCoal(int brokenCoal) {
		this.brokenCoal = brokenCoal;
	}

	public int getBrokenIron() {
		return brokenIron;
	}

	public void setBrokenIron(int brokenIron) {
		this.brokenIron = brokenIron;
	}

	public int getBrokenGold() {
		return brokenGold;
	}

	public void setBrokenGold(int brokenGold) {
		this.brokenGold = brokenGold;
	}

	public int getBrokenRedstone() {
		return brokenRedstone;
	}

	public void setBrokenRedstone(int brokenRedstone) {
		this.brokenRedstone = brokenRedstone;
	}

	public int getBrokenLapisLazuli() {
		return brokenLapisLazuli;
	}

	public void setBrokenLapisLazuli(int brokenLapisLazuli) {
		this.brokenLapisLazuli = brokenLapisLazuli;
	}

	public int getBrokenDiamond() {
		return brokenDiamond;
	}

	public void setBrokenDiamond(int brokenDiamond) {
		this.brokenDiamond = brokenDiamond;
	}

	public int getBrokenEmerald() {
		return brokenEmerald;
	}

	public void setBrokenEmerald(int brokenEmerald) {
		this.brokenEmerald = brokenEmerald;
	}

	public int getBrokenNetherQuartz() {
		return brokenNetherQuartz;
	}

	public void setBrokenNetherQuartz(int brokenNetherQuartz) {
		this.brokenNetherQuartz = brokenNetherQuartz;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}
}
