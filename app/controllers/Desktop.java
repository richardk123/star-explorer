package controllers;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import model.Player;
import model.PlayerService;
import model.PlayersHolder;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.desktop;

public class Desktop extends Controller {

    public static final String TYPE_FIELD = "type";
    public static final String TYPE_MOBILE_CONNECTED = "mobileConnected";
    public static final String TYPE_PLAYER_DATA = "playerData";
    public static final String TYPE_GAME_STARTED = "gameStarted";

    public Result index() {

        String keyConnector = UUID.randomUUID().toString();
        return ok(desktop.render(keyConnector.substring(0, 3)));
    }

    public Result wsJs(final String keyConnector) {

        return ok(views.js.desktop.render(keyConnector));
    }

    public WebSocket<JsonNode> ws(final String keyConnector) {
        return new WebSocket<JsonNode>() {

            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out)
            {
                // add desktop connection to holder
                final Player player = PlayersHolder.getInstance().addWSHolderDesktop(keyConnector, out);
                player.setKeyConnector(keyConnector);

                in.onMessage(
                        new F.Callback<JsonNode>()
                        {
                            @Override
                            public void invoke(JsonNode mobileData) throws Throwable
                            {
                                PlayerService service = new PlayerService();

                                switch (mobileData.get(TYPE_FIELD).asText())
                                {
                                    case TYPE_GAME_STARTED:
                                        service.initPlayerData(player);
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
