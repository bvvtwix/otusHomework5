import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Config.Key;

@Sources("classpath:config.properties")
public interface ConfigServer extends  Config{
    @Key("urlOtus")
    String urlOtus();
    @Key("urlDuckDuck")
    String urlDuckDuck();
    @Key("urlw3layouts")
    String urlw3layouts();
    @Key("email")
    String email();
    @Key("pass")
    String pass();
}
