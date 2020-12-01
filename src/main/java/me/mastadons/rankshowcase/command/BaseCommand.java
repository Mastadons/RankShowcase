package me.mastadons.rankshowcase.command;

import me.mastadons.command.BasicCommandListener;
import me.mastadons.command.CommandManager;
import me.mastadons.flag.DefinedFlag;
import me.mastadons.flag.FlagManager;
import me.mastadons.rankshowcase.RankShowcase;
import org.bukkit.entity.Player;

@FlagManager.FlaggedClass
public class BaseCommand extends BasicCommandListener {

    @FlagManager.FlaggedMethod(flag = DefinedFlag.PLUGIN_LOAD, priority = 10)
    public static void load() {
        CommandManager.registerCommand(RankShowcase.INSTANCE, new BaseCommand());
    }

    public BaseCommand() {
        super("rankshowcase", "rankshowcase.info", "rs");
        addSubCommandListener(new AddGroupCommand());
        addSubCommandListener(new AddLocationCommand());
        addSubCommandListener(new AddRankLineCommand());
        addSubCommandListener(new AddTrackLineCommand());
        addSubCommandListener(new ClearLocationsCommand());
        addSubCommandListener(new CreateTrackCommand());
        addSubCommandListener(new CreateRankCommand());
        addSubCommandListener(new InsertRankLineCommand());
        addSubCommandListener(new InsertTrackLineCommand());
        addSubCommandListener(new ListGroupsCommand());
        addSubCommandListener(new ListLinesCommand());
        addSubCommandListener(new ListLocationsCommand());
        addSubCommandListener(new ListRankLinesCommand());
        addSubCommandListener(new ReloadCommand());
        addSubCommandListener(new RemoveGroupCommand());
        addSubCommandListener(new RemoveLocationCommand());
        addSubCommandListener(new RemoveRankLineCommand());
        addSubCommandListener(new RemoveTrackLineCommand());
        addSubCommandListener(new SaveCommand());
        addSubCommandListener(new SetRankLineCommand());
        addSubCommandListener(new SetTrackLineCommand());
        addSubCommandListener(new SetTrackLocationCommand());
        addSubCommandListener(new UpdateCommand());
    }

    @CommandHandler
    public void onCommand(Player player, String[] arguments) {
        player.sendMessage("Rank showcase by Mastadons");
    }
}
