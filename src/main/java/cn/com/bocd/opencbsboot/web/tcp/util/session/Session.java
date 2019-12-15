package cn.com.bocd.opencbsboot.web.tcp.util.session;

import java.util.EnumMap;
import java.util.Map;

public class Session {
    private static ThreadLocal<Session> sessionHolder;

    static {
        sessionHolder = new ThreadLocal<>();
    }

    private Map<SessionDef, Object> sessionProperties;

    private Session() {
        sessionProperties = new EnumMap<>(SessionDef.class);
    }

    public static Session getSession() {
        Session s = sessionHolder.get();
        if (s == null) {
            s = new Session();
            sessionHolder.set(s);
        }
        return s;
    }

    public static<T> T get(SessionDef sd){
        return (T)Session.getSession().getValue(sd);
    }

    public static void clearSession() {
        sessionHolder.remove();
    }

    public <T> T getValue(SessionDef sd) {
        return (T)sessionProperties.get(sd);
    }

    public void set(SessionDef sd, Object value) {
        sessionProperties.put(sd, value);
    }
}
