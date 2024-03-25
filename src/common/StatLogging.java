package common;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 *
 * @author DELL
 */
public class StatLogging {

    public StatLogging() {
        initLogger();
    }

    public static void initLogger() {
        try {
            String path = "D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\src\\System_events_log.txt";
            //HTMLLayout hTMLLayout = new HTMLLayout();
            PatternLayout patternLayout = new PatternLayout("%-5p %d{yyyy-MMM-dd HH:mm:ss } %C %M %m %n");
            RollingFileAppender appender = new RollingFileAppender(patternLayout, path);
            appender.setMaxFileSize("1MB");
            appender.setName("mylogger");
            appender.activateOptions();
            Logger.getRootLogger().addAppender(appender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void errorLog(UserDetails details, String errMessage) {
        initLogger();
        Logger log = Logger.getLogger("mylogger");
        log.error(details.getId() + " " + details.getRole() + " " + details.getFname() + " " + details.getLname() + " " + errMessage);
    }
    
    public void errorLog(String errMessage) {
        initLogger();
        Logger log = Logger.getLogger("mylogger");
        log.error(errMessage);
    }

    public void infoLog(UserDetails details, String infoMessage) {
        initLogger();
        Logger log = Logger.getLogger("mylogger");
        log.info(details.getId() + " " + details.getRole() + " " + details.getFname() + " " + details.getLname() + " " +infoMessage);
    }

    public static void main(String[] args) {
        initLogger();
        Logger log = Logger.getLogger("mylogger");
        log.error("Sysytem error found 1");
        log.info("Sysytem error found 2");
        log.warn("Sysytem error found 3");
        log.error("Sysytem error found 4");
    }
}
