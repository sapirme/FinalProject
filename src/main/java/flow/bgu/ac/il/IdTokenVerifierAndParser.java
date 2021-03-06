package flow.bgu.ac.il;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

public class IdTokenVerifierAndParser {

    private static final String GOOGLE_CLIENT_ID = "592702752072-pbf5j8q2t3bg5mu1htisjqhm95ms2ljr.apps.googleusercontent.com";

    public static GoogleIdToken.Payload getPayload (String tokenString) throws Exception {

        JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier googleIdTokenVerifier =
                new GoogleIdTokenVerifier(new NetHttpTransport(), jacksonFactory);

        GoogleIdToken token = GoogleIdToken.parse(jacksonFactory, tokenString);

        if (googleIdTokenVerifier.verify(token)) {
            GoogleIdToken.Payload payload = token.getPayload();
            if (!GOOGLE_CLIENT_ID.equals(payload.getAudience())) {
                System.out.println("1");
                throw new IllegalArgumentException("Audience mismatch");
            } else if (!GOOGLE_CLIENT_ID.equals(payload.getAuthorizedParty())) {
                System.out.println("2");
                throw new IllegalArgumentException("Client ID mismatch");
            }
            return payload;
        } else {
            System.out.println("3");
            throw new IllegalArgumentException("id token cannot be verified");
        }


    }

}


