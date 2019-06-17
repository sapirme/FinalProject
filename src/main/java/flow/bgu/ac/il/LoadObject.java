package flow.bgu.ac.il;

import IllusionSystem.BLManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class LoadObject extends HttpServlet {

	//final static Logger LOG = LoggerFactory.getLogger(LoadObject.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598336877581962216L;

	// A hack that only works if one program is running at a time!
	//public static BProgram bprog;
	//public static BProgramRunner rnr;

	//private static Thread thread;

	private BLManager BPM;
	public LoadObject(BLManager bpm){
		this.BPM=bpm;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Extract the XML fro the message
		BufferedReader br = request.getReader();

		int index=-1;
		try {
			index = Integer.parseInt(IOUtils.toString(br));
		} catch(Exception e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String xml = BPM.loadObject(index);

		if (xml == null){
			response.setStatus(20);
		}
		else {
			response.setContentType("text/plain");
			response.getWriter().println(xml);
			response.setStatus(HttpServletResponse.SC_OK);
		}

	}


}
