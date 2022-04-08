import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Config.Key;

@Sources("classpath:config.properties")
public interface ConfigServer extends  Config{
    @Key("url")
    String url();
    @Key("email")
    String email();
    @Key("pass")
    String pass();
}
