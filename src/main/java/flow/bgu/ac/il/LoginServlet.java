package flow.bgu.ac.il;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


import SystemObj.BLManager;
import SystemObj.BLManagerImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpSession;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;


@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private BLManager BPM;
    public LoginServlet(BLManager bpm){
        this.BPM=bpm;
    }

    @Override
    protected void doPost (HttpServletRequest req,
                           HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");

        try {
            BufferedReader br = req.getReader();
            String idToken = IOUtils.toString(br);

            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String email = payLoad.getEmail();


            //BLManager BPM = BLManagerImpl.getInstance();
            BPM.login(idToken,email);

        } catch (Exception e) { // cant verify
            System.out.println("not verify");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		//BLManager BPM = BLManagerImpl.getInstance();
		BPM.logout();
    }
}