package rajouai.adil.reservationplatform.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenProvider {

    private static final int DEFAULT_EXPIRATION_DURATION_HOURS = 1;

    private final Algorithm algo;
    private final JWTVerifier verifier;

    public TokenProvider() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Dotenv dotenv = Dotenv
                .configure()
                .load();
        String privateKeyBase64 = dotenv.get("RSA_PRIVATE_KEY");
        byte[] privateKeyBytes = Base64
                .getDecoder()
                .decode(privateKeyBase64);
        String publicKeyBase64 = dotenv.get("RSA_PUBLIC_KEY");
        byte[] publicKeyBytes = Base64
                .getDecoder()
                .decode(publicKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec rsaPrivateKey =
                new PKCS8EncodedKeySpec(privateKeyBytes);
        X509EncodedKeySpec rsaPublicKey = new X509EncodedKeySpec(publicKeyBytes);
        algo = Algorithm.RSA256((RSAPublicKey) keyFactory.generatePublic(rsaPublicKey), (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivateKey));
        verifier = JWT
                .require(algo)
                .build();
    }

    public String generateAccessToken(UserDetails user) {
        return JWT
                .create()
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationDate())
                .sign(algo);
    }

    public String validateToken(String token) {
        return verifier
                .verify(token)
                .getSubject();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (long) DEFAULT_EXPIRATION_DURATION_HOURS * 60 * 60 * 1000);
    }
}
