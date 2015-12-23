package model;

import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Mobile;

/**
 * @author kolisekr
 * @created 21/12/15.
 */
public class PlayerService
{

	/**
	 * calculate correlation data
	 * @param player
	 */
	public void initPlayerData(@Nonnull Player player)
	{
		assert player != null;

		// mobile data from phone
		JsonNode mobileData = player.getMobileData();

		player.setGameStarted(true);
		player.setAcceleration(Player.MAX_ACCELERATION);
	}

	/**
	 * set set angles from mobile data
	 * @param player
	 * @param mobileData
	 */
	public void updatePlayerData(@Nonnull Player player,
							     @Nonnull JsonNode mobileData)
	{

		assert player != null;
		assert mobileData != null;

		double rotationY = mobileData.get(Mobile.ROTATION_Y).asDouble();

		player.setAngle(rotationY);

		// set last mobile data
		player.setMobileData(mobileData);
	}

	public void shieldUp(@Nonnull final Player player)
	{
		ObjectNode node = new ObjectMapper().createObjectNode();
		node.put(Mobile.KEY_CONNECTOR, player.getKeyConnector());
		node.put(Mobile.TYPE_FIELD, Mobile.TYPE_SHIELD_UP);
		player.getDesktopOut().write(node);

		player.setShield(true);

		Timer timer = new Timer();
		timer.schedule(
				new TimerTask()
				{
					@Override
					public void run()
					{
						player.setShield(false);

						ObjectNode node = new ObjectMapper().createObjectNode();
						node.put(Mobile.TYPE_FIELD, Mobile.TYPE_SHIELD_DOWN);
						node.put(Mobile.KEY_CONNECTOR, player.getKeyConnector());
						player.getDesktopOut().write(node);
					}
				},
				Player.SHIELD_UP_MILLIS
		);
	}

	public void boostSpeed(@Nonnull final Player player)
	{
		player.setSpeedBoost(true);

		Timer timer = new Timer();
		timer.schedule(
				new TimerTask()
				{
					@Override
					public void run()
					{
						player.setSpeedBoost(false);
					}
				},
				Player.SPEED_BOOST_MILLIS
		);
	}

	public void fireGun(@Nonnull Player player)
	{

	}

	public void fireRocket(@Nonnull Player player)
	{

	}

	/**
	 * calculate physic
	 *
	 * @param player
	 */
	public void calculatePlayerData(@Nonnull Player player,
									int timeInMilis)
	{

		double speedBoost = player.isSpeedBoost() ? 3 : 1;

		double speed = player.getSpeed();

		player.setAcceleration((1 - (speed / (Player.MAX_SPEED * speedBoost))) * Player.MAX_ACCELERATION);

		player.setVx(player.getVx() + (Math.cos(player.getAngle()) * player.getAcceleration()));
		player.setVy(player.getVy() + (Math.sin(player.getAngle()) * player.getAcceleration()));

		player.setX(player.getX() + player.getVx());
		player.setY(player.getY() + player.getVy());
	}

}
