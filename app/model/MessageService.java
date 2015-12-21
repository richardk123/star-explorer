package model;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Desktop;
import controllers.Mobile;

/**
 * @author kolisekr
 * @created 21/12/15.
 */
public class MessageService
{

	public JsonNode createMobileConnectedMessage(@Nonnull Player player)
	{
		assert player != null;

		ObjectNode node = new ObjectMapper().createObjectNode();

		node.put(Desktop.TYPE_FIELD, Desktop.TYPE_MOBILE_CONNECTED);
		node.put(Mobile.KEY_CONNECTOR, player.getKeyConnector());

		return node;
	}

	/**
	 *
	 * @param player
	 * @return
	 */
	public JsonNode createDesktopMessage(@Nonnull Player player)
	{
		assert player != null;

		ObjectNode node = new ObjectMapper().createObjectNode();

		node.put(Desktop.TYPE_FIELD, Desktop.TYPE_PLAYER_DATA);
		node.put(Mobile.KEY_CONNECTOR, player.getKeyConnector());

		node.put(Player.ANGLE_X, player.getAngleX());
		node.put(Player.ANGLE_Y, player.getAngleY());
		node.put(Player.ANGLE_Z, player.getAngleZ());

		node.put(Player.POS_X, player.getX());
		node.put(Player.POS_Y, player.getY());
		node.put(Player.POS_Z, player.getZ());

		node.put(Player.SPEED_X, player.getVx());
		node.put(Player.SPEED_Y, player.getVy());
		node.put(Player.SPEED_Z, player.getVz());

		return node;
	}

}
