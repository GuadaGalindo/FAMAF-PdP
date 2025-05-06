package utils;

public class Config {
    private boolean printFeed = false;
    private String computeNamedEntities = null;
    private boolean help = false;
    private String feedKey = null;
    private String statsFormat = null;

    public Config(boolean printFeed, String computeNamedEntities, boolean help, String feedKey, String statsFormat) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.help = help;
        this.feedKey = feedKey;
        this.statsFormat = statsFormat;
    }

    public boolean getPrintFeed() {
        return printFeed;
    }

    public String getComputeNamedEntities() {
        return computeNamedEntities;
    }

    public boolean getHelp() {
        return help;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public String getStatsFormat() {
        return statsFormat;
    }

}
