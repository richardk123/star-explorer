package model;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.JsonNode;
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

		player.setDefAngleX(mobileData.get(Mobile.ROTATION_X).asDouble());
		player.setDefAngleY(mobileData.get(Mobile.ROTATION_Y).asDouble());
		player.setDefAngleZ(mobileData.get(Mobile.ROTATION_Z).asDouble());
		player.setGameStarted(true);
		player.setSpeed(0.01);
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

		double rotationX = mobileData.get(Mobile.ROTATION_X).asDouble();
		double rotationY = mobileData.get(Mobile.ROTATION_Y).asDouble();
		double rotationZ = mobileData.get(Mobile.ROTATION_Z).asDouble();

		player.setAngleX(-(rotationX - player.getDefAngleX()));
		player.setAngleY(rotationY - player.getDefAngleY() - Math.PI);
		player.setAngleZ(-(rotationZ - player.getDefAngleZ()));

		// set last mobile data
		player.setMobileData(mobileData);
	}

	/**
	 * calculate physic
	 *
	 * @param player
	 * @param timeInMilis
	 */
	public void calculatePlayerData(@Nonnull Player player,
									int timeInMilis)
	{
		player.setVy(-Math.sin(player.getAngleX()) * player.getSpeed());
		player.setVz(Math.cos(player.getAngleX()) * player.getSpeed());
//		player.setVx(Math.sin(player.getAngleZ()) * player.getSpeed());

		player.setX(player.getX() + player.getVx());
		player.setY(player.getY() + player.getVy());
		player.setZ(player.getZ() + player.getVz());
	}

}
