package model;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import play.mvc.WebSocket;

/**
 * @author kolisekr
 * @created 19/12/15.
 */
public class PlayersHolder
{

	private Map<String, Player> playerMap = Maps.newConcurrentMap();

	private final static PlayersHolder connector = new PlayersHolder();

	public synchronized static PlayersHolder getInstance()
	{
		return connector;
	}

	public Player addWSHolderMobile(String key, WebSocket.Out<JsonNode> mobileOut)
	{
		Player player = playerMap.get(key);
		if (player == null)
		{
			player = new Player();
			playerMap.put(key, player);
		}

		player.setMobileOut(mobileOut);
		return player;
	}

	public Player addWSHolderDesktop(String key, WebSocket.Out<JsonNode> desktopOut)
	{
		Player player = playerMap.get(key);
		if (player == null)
		{
			player = new Player();
			playerMap.put(key, player);
		}

		player.setDesktopOut(desktopOut);
		return player;
	}

	@Nullable
	public Player get(String key)
	{
		return playerMap.get(key);
	}

	public Collection<Player> getAll()
	{
		return playerMap.values();
	}

	public void remove(String key)
	{
		playerMap.remove(key);
	}


}
