import java.util.Collection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import model.MessageService;
import model.Player;
import model.PlayerPredicate;
import model.PlayerService;
import model.PlayersHolder;
import play.Application;
import play.GlobalSettings;

/**
 * @author kolisekr
 * @created 21/12/15.
 */
public class Global extends GlobalSettings
{
	public final static int SIMULATION_TIME_MILIS = 10;

	@Override
	public void onStart(Application app)
	{
		Timer timer = new Timer();

		// resend every 10 miliseconds data to desktop clients
		timer.schedule(
				new TimerTask()
				{
					@Override
					public void run()
					{
						PlayerService playerService = new PlayerService();
						MessageService messageService = new MessageService();

						Collection<Player> all = PlayersHolder.getInstance().getAll();

						// filter started games
						all = FluentIterable.from(all)
								.filter(PlayerPredicate.GAME_STARTED(true))
								.toList();

						Map<Player, JsonNode> playerDataMap = Maps.newHashMap();

						// simulate universe
						for (Player player : all)
						{
							playerService.calculatePlayerData(player, SIMULATION_TIME_MILIS);
							JsonNode desktopData = messageService.createDesktopMessage(player);
							playerDataMap.put(player, desktopData);
						}

						// send all players data of all players
						for (Player p1 : all)
						{
							for (Player p2 : all)
							{
								JsonNode data = playerDataMap.get(p2);
								p1.getDesktopOut().write(data);
							}
						}
					}
				},
				0,
				SIMULATION_TIME_MILIS
		);

	}

}
