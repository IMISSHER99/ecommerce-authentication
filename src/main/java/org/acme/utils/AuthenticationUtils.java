package org.acme.utils;


import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.EntityConstants;
import org.acme.entity.Role;
import org.acme.exceptions.AccessDeniedException;
import org.eclipse.microprofile.jwt.Claims;
import org.hibernate.annotations.Comment;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class AuthenticationUtils {


    /**
     * Extract client id and secrets which are base64 encoded
     * @param authorizationHeader Authorization Header
     * @return Client ID and Secret
     */
    public String[] extractCredentials(String authorizationHeader) {
        if(Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            return credentials.split(":");
        }
        throw new AccessDeniedException("Client Authentication Failed Access Denied");
    }

    /**
     * Generate JWT token
     * @param roles Role of a user
     * @param username username
     * @return JWT TOKEN
     * @throws Exception Throws Exception
     */
    public String generateAuthorizationCode(Set<Role> roles, String username) throws Exception {
        Set<String> groups = roles.stream().map(i -> i.getRoleName().name()).collect(Collectors.toSet());
        return Jwt.claim(EntityConstants.USER_ID, username)
                .issuer("Amith")
                .groups(groups)
                .subject(username)
                .expiresAt(System.currentTimeMillis() + (TimeUnit.MINUTES.toSeconds(5) * 1000))
                .sign(readPrivateKey("/privateKeyPkcs8.pem"));
    }


    /**
     * Reading private key from classpath
     * @param pemResName file name
     * @return PrivateKey
     * @throws Exception FileNotFoundException
     */
    private PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = AuthenticationUtils.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        }
    }

    /**
     * Decode Private key
     * @param pemEncoded file name
     * @return PrivateKey
     * @throws Exception FileNotFoundException
     */
    private PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    /**
     * Base64 decoder
     * @param pemEncoded string
     * @return byte array
     */
    private byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    /**
     * Remove Comments from keys
     * @param pem string
     * @return string
     */
    private String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    /**
     * @return the current time in seconds since epoch
     */
    private int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}
