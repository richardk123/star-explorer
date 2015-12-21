package model;

import javax.annotation.Nonnull;

import com.google.common.base.Predicate;

/**
 * @author kolisekr
 * @created 21/12/15.
 */
public class PlayerPredicate
{

	@Nonnull
	public static Predicate<Player> GAME_STARTED(final boolean started)
	{
		return player -> player.isGameStarted() == started;
	}

}
