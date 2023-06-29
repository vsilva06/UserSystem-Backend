package cl.losguindos.UserSystemBackend.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder {
    public MyPasswordEncoder() {
    }

    public static String encode(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(10, 65536, 1, password.toCharArray());
    }
}
