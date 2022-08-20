package commons;

import org.aeonbits.owner.Config;

@Config.Sources({ "classpath:environmentconfig/${env}.properties" })
public interface Environment extends Config {
    @Key("url")
    String URL();

    @Key("username")
    String USER_NAME();

    @Key("password")
    String PASSWORD();
}
