package flow.bgu.ac.il;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


import IllusionSystem.BLManager;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.apache.commons.io.IOUtils;


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

            if (BPM.login(idToken,email))
                resp.setStatus(HttpServletResponse.SC_OK);
            else resp.setStatus(500);

        } catch (Exception e) { // cant verify
            resp.setStatus(500);
            System.out.println("not verify");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		//BLManager BPM = BLManagerImpl.getInstance();
		if (BPM.logout()){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            response.setStatus(500);
        }
    }
}