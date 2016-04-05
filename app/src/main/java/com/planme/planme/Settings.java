package com.planme.planme;

/**
 * Class to hold settings
 *
 * Created by Danny Finkelstein on 4/5/16.
 */
public class Settings {

    private GPSMode gpsMode;
    private NotificationMode notificationMode;

    private enum GPSMode {
        On, Off
    }

    private enum NotificationMode {
        On, Off, Important_Only
    }

    public GPSMode getGpsMode() {
        return gpsMode;
    }

    public void setGpsMode(GPSMode gpsMode) {
        this.gpsMode = gpsMode;
    }

    public NotificationMode getNotificationMode() {
        return notificationMode;
    }

    public void setNotificationMode(NotificationMode notificationMode) {
        this.notificationMode = notificationMode;
    }
}
