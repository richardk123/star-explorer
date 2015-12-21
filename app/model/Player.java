package model;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.WebSocket;

/**
 * @author kolisekr
 * @created 21/12/15.
 */
public class Player
{
	private WebSocket.Out<JsonNode> mobileOut;
	private WebSocket.Out<JsonNode> desktopOut;

	public static final String POS_X = "x";
	public static final String POS_Y = "y";
	public static final String POS_Z = "z";

	public static final String SPEED_X = "vX";
	public static final String SPEED_Y = "vY";
	public static final String SPEED_Z = "vZ";

	public static final String ANGLE_X = "angleX";
	public static final String ANGLE_Y = "angleY";
	public static final String ANGLE_Z = "angleZ";

	private boolean gameStarted = false;
	private JsonNode mobileData;

	private String keyConnector;

	private double x = 0;
	private double y = 0;
	private double z = 0;

	private double speed = 0;

	private double vx = 0;
	private double vy = 0;
	private double vz = 0;

	private double defAngleX = 0;
	private double defAngleY = 0;
	private double defAngleZ = 0;

	private double angleX = 0;
	private double angleY = 0;
	private double angleZ = 0;

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

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = z;
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

	public double getVz()
	{
		return vz;
	}

	public void setVz(double vz)
	{
		this.vz = vz;
	}

	public double getDefAngleX()
	{
		return defAngleX;
	}

	public void setDefAngleX(double defAngleX)
	{
		this.defAngleX = defAngleX;
	}

	public double getDefAngleY()
	{
		return defAngleY;
	}

	public void setDefAngleY(double defAngleY)
	{
		this.defAngleY = defAngleY;
	}

	public double getDefAngleZ()
	{
		return defAngleZ;
	}

	public void setDefAngleZ(double defAngleZ)
	{
		this.defAngleZ = defAngleZ;
	}

	public double getAngleX()
	{
		return angleX;
	}

	public void setAngleX(double angleX)
	{
		this.angleX = angleX;
	}

	public double getAngleY()
	{
		return angleY;
	}

	public void setAngleY(double angleY)
	{
		this.angleY = angleY;
	}

	public double getAngleZ()
	{
		return angleZ;
	}

	public void setAngleZ(double angleZ)
	{
		this.angleZ = angleZ;
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

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
}
