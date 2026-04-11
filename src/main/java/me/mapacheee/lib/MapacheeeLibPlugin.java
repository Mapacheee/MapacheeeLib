package me.mapacheee.lib;

import com.thewinterframework.paper.PaperWinterPlugin;
import com.thewinterframework.plugin.WinterBootPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WinterBootPlugin
public class MapacheeeLibPlugin extends PaperWinterPlugin {

    private static Logger LOGGER = LoggerFactory.getLogger(MapacheeeLibPlugin.class);

    @Override
    public void onPluginEnable() {
        LOGGER.info("MapacheeeLibPlugin is enabled!");
    }

    @Override
    public void onPluginDisable() {
        LOGGER.info("MapacheeeLibPlugin is disabled!");
    }
}
