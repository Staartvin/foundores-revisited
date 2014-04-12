package me.staartvin.foundores.util.uuid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Manages everything related to UUIDs
 * <p>
 * Date created: 17:13:57 2 apr. 2014
 * 
 * @author Staartvin
 * 
 */
public class UUIDManager {

	private Map<String, UUID> foundUUIDs = new HashMap<String, UUID>();
	private Map<UUID, String> foundPlayers = new HashMap<UUID, String>();

	// This hashmap stores the cached values of uuids for players. 
	// Player names are always lowercase.
	private HashMap<String, UUID> cachedUUIDs = new HashMap<String, UUID>();

	// This hashmap stores what the time is that of the latest cache for a certain player.
	// This is used to see if the cached UUID was older than 12 hours. If it is older than 12 hours,
	// it will be renewed.
	// Player names are always lowercase.
	private HashMap<String, Long> lastCached = new HashMap<String, Long>();

	// This the time that one cached value is valid (in hours).
	private int maxLifeTime = 12;

	public Map<String, UUID> getUUIDs(final List<String> names) {

		// Clear maps first
		foundUUIDs.clear();

		// A new map to store cached values
		HashMap<String, UUID> uuids = new HashMap<String, UUID>();

		// This is used to check if we need to use the lookup from the mojang website.
		boolean useInternetLookup = true;

		// Check if we have cached values
		for (String playerName : names) {
			playerName = playerName.toLowerCase();

			// If cached value is still valid, use it.
			if (!shouldUpdateValue(playerName)) {
				//System.out.print("Using cached value of uuid for " + playerName);
				uuids.put(playerName, cachedUUIDs.get(playerName));
			}
		}

		// All names were retrieved from cached values
		// So we don't need to do a lookup to the Mojang website.
		if (uuids.entrySet().size() == names.size()) {
			useInternetLookup = false;
		}

		// No internet lookup needed.
		if (!useInternetLookup) {
			// Return all cached values.
			return uuids;
		}

		// From here on we know that didn't have all uuids as cached values.
		// So we need to do a lookup.
		// We have to make sure we only lookup the players that we haven't got cached values of yet.

		// Remove players that don't need to be looked up anymore. 
		// Just for performance sake.
		for (Entry<String, UUID> entry : uuids.entrySet()) {
			names.remove(entry.getKey());
		}

		// Now we need to lookup the other players

		Thread fetcherThread = new Thread(new Runnable() {

			public void run() {
				UUIDFetcher fetcher = new UUIDFetcher(names);

				Map<String, UUID> response = null;

				try {
					response = fetcher.call();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (response != null) {
					foundUUIDs = response;
				}
			}
		});

		fetcherThread.start();

		if (fetcherThread.isAlive()) {
			try {
				fetcherThread.join(1000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Update cached entries
		for (Entry<String, UUID> entry : foundUUIDs.entrySet()) {
			String playerName = entry.getKey().toLowerCase();
			UUID uuid = entry.getValue();

			// Add found uuids to the list of uuids to return
			uuids.put(playerName, uuid);

			if (shouldUpdateValue(playerName)) {
				// Update cached values
				cachedUUIDs.put(playerName, uuid);
				lastCached.put(playerName, System.currentTimeMillis());
			} else {
				// Do not update if it is not needed.
				continue;
			}
		}

		// Thread stopped now, collect results
		return uuids;
	}

	public Map<UUID, String> getPlayers(final List<UUID> uuids) {
		// Clear names first
		foundPlayers.clear();

		// A new map to store cached values
		HashMap<UUID, String> players = new HashMap<UUID, String>();

		// This is used to check if we need to use the lookup from the mojang website.
		boolean useInternetLookup = true;

		// Check if we have cached values
		for (UUID uuid : uuids) {

			String playerName = null;

			for (Entry<String, UUID> entry : cachedUUIDs.entrySet()) {
				if (entry.getValue().equals(uuid)) {
					playerName = entry.getKey().toLowerCase();
				}
			}

			if (playerName != null) {
				// If cached value is still valid, use it.
				if (!shouldUpdateValue(playerName)) {
					//System.out.print("Using cached value of playername for " + playerName);
					players.put(uuid, playerName);
				}
			}
		}

		// All names were retrieved from cached values
		// So we don't need to do a lookup to the Mojang website.
		if (players.entrySet().size() == uuids.size()) {
			useInternetLookup = false;
		}

		// No internet lookup needed.
		if (!useInternetLookup) {
			// Return all cached values.
			return players;
		}

		// From here on we know that didn't have all uuids as cached values.
		// So we need to do a lookup.
		// We have to make sure we only lookup the players that we haven't got cached values of yet.

		// Remove uuids that don't need to be looked up anymore. 
		// Just for performance sake.
		for (UUID entry : players.keySet()) {
			uuids.remove(entry);
		}

		// Now we need to lookup the other players

		Thread fetcherThread = new Thread(new Runnable() {

			public void run() {
				NameFetcher fetcher = new NameFetcher(uuids);

				Map<UUID, String> response = null;

				try {
					response = fetcher.call();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (response != null) {
					foundPlayers = response;
				}
			}
		});

		fetcherThread.start();

		if (fetcherThread.isAlive()) {
			try {
				fetcherThread.join(1000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Update cached entries
		for (Entry<UUID, String> entry : foundPlayers.entrySet()) {
			String playerName = entry.getValue().toLowerCase();
			UUID uuid = entry.getKey();

			// Add found players to the list of players to return
			players.put(uuid, playerName);

			if (shouldUpdateValue(playerName)) {
				// Update cached values
				cachedUUIDs.put(playerName, uuid);
				lastCached.put(playerName, System.currentTimeMillis());
			} else {
				// Do not update if it is not needed.
				continue;
			}
		}

		// Thread stopped now, collect results
		return players;
	}

	/**
	 * Get the Minecraft name of the player that is hooked to this Mojang
	 * account UUID.
	 * 
	 * @param uuid the UUID of the Mojang account
	 * @return the name of player or null if not found.
	 */
	public String getPlayerFromUUID(UUID uuid) {
		if (uuid == null)
			return null;

		Map<UUID, String> players = getPlayers(Arrays.asList(uuid));

		if (players == null)
			return null;

		if (players.isEmpty())
			return null;

		return players.get(uuid);
	}

	/**
	 * Get the UUID of the Mojang account associated with this player name
	 * 
	 * @param playerName Name of the player
	 * @return UUID of the associated Mojang account or null if not found.
	 */
	public UUID getUUIDFromPlayer(String playerName) {
		if (playerName == null) {
			return null;
		}

		Map<String, UUID> uuids = getUUIDs(Arrays.asList(playerName));

		if (uuids == null) {
			return null;
		}

		if (uuids.isEmpty()) {
			return null;
		}

		return uuids.get(playerName.toLowerCase());
	}

	private boolean shouldUpdateValue(String playerName) {

		playerName = playerName.toLowerCase();

		// Never cached, so cache now.
		if (!lastCached.containsKey(playerName)
				|| !cachedUUIDs.containsKey(playerName))
			return true;

		// Incorrectly cached, so cache now.
		if (lastCached.get(playerName) == null
				|| cachedUUIDs.get(playerName) == null)
			return true;

		long lastCacheTime = lastCached.get(playerName);

		long currentTime = System.currentTimeMillis();

		long lifeTime = currentTime - lastCacheTime;

		// The cached value is older than it ought to be.
		if ((lifeTime / 3600000) > maxLifeTime) {
			return true;
		}

		return false;
	}
}
