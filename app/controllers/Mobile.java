package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.MessageService;
import model.Player;
import model.PlayerService;
import model.PlayersHolder;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.mobile;

/**
 * @author kolisekr
 * @created 19/12/15.
 */
public class Mobile extends Controller
{
	public static final String TYPE_FIELD = "type";

	public static final String TYPE_SENSOR_DATA = "sensorData";
	public static final String TYPE_FIRE_ROCKET = "fire-rocket";
	public static final String TYPE_FIRE_GUN = "fire-gun";
	public static final String TYPE_SHIELD_UP = "shield-up";
	public static final String TYPE_SHIELD_DOWN = "shield-down";
	public static final String TYPE_BOOST_SPEED = "boost-speed";

	public static final String KEY_CONNECTOR = "keyConnector";

	public static final String ROTATION_X = "rotationX";
	public static final String ROTATION_Y = "rotationY";
	public static final String ROTATION_Z = "rotationZ";

	public Result index(String keyConnector) {
		return ok(mobile.render(keyConnector));
	}

	public Result wsJs(final String keyConnector) {

		return ok(views.js.mobile.render(keyConnector));
	}

	public WebSocket<JsonNode> ws(final String keyConnector) {
		return new WebSocket<JsonNode>() {

			@Override
			public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {

				// add mobile connection to holder
				final Player player = PlayersHolder.getInstance().addWSHolderMobile(keyConnector, out);

				// send desktop message that mobile was connected
				MessageService messageService = new MessageService();
				JsonNode mobileConnected = messageService.createMobileConnectedMessage(player);
				if (player.getDesktopOut() != null)
				{
					player.getDesktopOut().write(mobileConnected);
				}

				// handle
				in.onMessage(
						new F.Callback<JsonNode>()
						{
							@Override
							public void invoke(JsonNode mobileData) throws Throwable
							{
								PlayerService service = new PlayerService();

								switch (mobileData.get(TYPE_FIELD).asText())
								{
									case TYPE_SENSOR_DATA: service.updatePlayerData(player, mobileData); break;
									case TYPE_SHIELD_UP: service.shieldUp(player); break;
									case TYPE_BOOST_SPEED: service.boostSpeed(player); break;
									case TYPE_FIRE_GUN: service.fireGun(player); break;
									case TYPE_FIRE_ROCKET: service.fireRocket(player); break;
								}

							}
						}
				);

				in.onClose(
						new F.Callback0()
						{
							@Override
							public void invoke() throws Throwable
							{
								PlayersHolder.getInstance().remove(keyConnector);
							}
						}
				);
			}

		};
	}

}
