package model;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.WebSocket;

/**
 * @author kolisekr
 * @created 21/12/15.
 */
public class Player
{
	public static final int SHIELD_UP_MILLIS = 10000;
	public static final int SPEED_BOOST_MILLIS = 5000;
	public static double MAX_SPEED = 0.01;
	public static final double MAX_ACCELERATION = 0.001;

	private WebSocket.Out<JsonNode> mobileOut;
	private WebSocket.Out<JsonNode> desktopOut;

	public static final String POS_X = "x";
	public static final String POS_Y = "y";

	public static final String SPEED_X = "vX";
	public static final String SPEED_Y = "vY";

	public static final String ANGLE = "angle";

	private boolean gameStarted = false;
	private JsonNode mobileData;

	private String keyConnector;

	private double angle = 0;

	private double x = 0;
	private double y = 0;

	private double acceleration = 0;

	private double vx = 0;
	private double vy = 0;

	// is shield up?
	private boolean shield;

	private boolean speedBoost = false;

	public WebSocket.Out<JsonNode> getMobileOut()
	{
		return mobileOut;
	}

	public void setMobileOut(WebSocket.Out<JsonNode> mobileOut)
	{
		this.mobileOut = mobileOut;
	}

	public WebSocket.Out<JsonNode> getDesktopOut()
	{
		return desktopOut;
	}

	public void setDesktopOut(WebSocket.Out<JsonNode> desktopOut)
	{
		this.desktopOut = desktopOut;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getVx()
	{
		return vx;
	}

	public void setVx(double vx)
	{
		this.vx = vx;
	}

	public double getVy()
	{
		return vy;
	}

	public void setVy(double vy)
	{
		this.vy = vy;
	}

	public double getSpeed()
	{
		return Math.sqrt((getVx() * getVx()) + (getVy() * getVy()));
	}

	public String getKeyConnector()
	{
		return keyConnector;
	}

	public void setKeyConnector(String keyConnector)
	{
		this.keyConnector = keyConnector;
	}

	public boolean isGameStarted()
	{
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted)
	{
		this.gameStarted = gameStarted;
	}

	public JsonNode getMobileData()
	{
		return mobileData;
	}

	public void setMobileData(JsonNode mobileData)
	{
		this.mobileData = mobileData;
	}

	public double getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(double acceleration)
	{
		this.acceleration = acceleration;
	}

	public boolean isShield()
	{
		return shield;
	}

	public void setShield(boolean shield)
	{
		this.shield = shield;
	}

	public boolean isSpeedBoost()
	{
		return speedBoost;
	}

	public void setSpeedBoost(boolean speedBoost)
	{
		this.speedBoost = speedBoost;
	}

	public double getAngle()
	{
		return angle;
	}

	public void setAngle(double angle)
	{
		this.angle = angle;
	}
}
