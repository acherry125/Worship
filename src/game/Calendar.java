package game;

public class Calendar {
    private final GodSim g;
    private final int epoch;
    int second;
    private final int MILLIS_PER_MONTH = 9000;
    private final int DAYS_PER_MONTH = 30;
    private final int MILLIS_PER_DAY = MILLIS_PER_MONTH / DAYS_PER_MONTH;
    private final String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "September", "October", "November", "December"};
    private static Calendar ourInstance;
    public static void create(GodSim g) {
        ourInstance = new Calendar(g);
    }
    public static Calendar single() {
        return ourInstance;
    }
    private Calendar(GodSim g) {
        this.g = g;
        epoch = g.frameCount / 60;
    }
    public int millis() {
        return (g.frameCount*1000 / 60) - epoch;
    }
    public String getMonth() {
        return months[(millis() / MILLIS_PER_MONTH) % months.length];
    }
    public String getDay() {
        return Integer.toString((millis() / MILLIS_PER_DAY) % DAYS_PER_MONTH);
    }
}
