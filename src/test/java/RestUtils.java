import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {

    public static String getRandomUserName(){
        return "Rez"+RandomStringUtils.randomAlphabetic(3);
    }
}
