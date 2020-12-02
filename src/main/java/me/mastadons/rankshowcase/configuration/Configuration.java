package me.mastadons.rankshowcase.configuration;

import lombok.Getter;
import me.mastadons.flag.FlagManager;
import me.mastadons.rankshowcase.RankShowcase;
import me.mastadons.rankshowcase.database.DatabaseConfiguration;
import me.mastadons.rankshowcase.file.FileManager;
import me.mastadons.rankshowcase.structure.TrackConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@FlagManager.FlaggedClass
public class Configuration {

    public static Configuration INSTANCE;

    @Getter private static Yaml configurationYaml;
    @Getter private static File configurationFile;

    @FlagManager.FlaggedMethod(flag = RankShowcase.PLUGIN_LOAD_FLAG, priority = 0)
    public static void load() throws FileNotFoundException {
        RankShowcase.INSTANCE.getLogger().info("Loading configuration...");

        configurationYaml = new Yaml(new CustomClassLoaderConstructor(Configuration.class.getClassLoader()));
        configurationYaml.setBeanAccess(BeanAccess.FIELD);
        configurationFile = FileManager.getServerFile("configuration.yml", "configuration.yml");
        FileReader configurationReader = new FileReader(configurationFile);

        INSTANCE = configurationYaml.loadAs(configurationReader, Configuration.class);
        RankShowcase.INSTANCE.getLogger().info("Loaded configuration!");
    }

    @FlagManager.FlaggedMethod(flag = RankShowcase.PLUGIN_SAVE_FLAG, priority = Integer.MAX_VALUE)
    public static void save() throws IOException {
        configurationFile = FileManager.getServerFile("configuration.yml", "configuration.yml");
        FileWriter configurationWriter = new FileWriter(configurationFile, false);

        configurationYaml.dump(INSTANCE, configurationWriter);
    }

    public List<String> ignoredPlayers;

    public TrackConfiguration trackConfiguration;

    public long updateFrequency;

    public DatabaseConfiguration databaseConfiguration;
}
