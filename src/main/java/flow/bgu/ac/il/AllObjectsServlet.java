package flow.bgu.ac.il;

import SystemObj.*;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllObjectsServlet extends HttpServlet {

	final static Logger LOG = LoggerFactory.getLogger(AllObjectsServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598336877581962216L;

	// A hack that only works if one program is running at a time!
	public static BProgram bprog;
	public static BProgramRunner rnr;

	private static Thread thread;

	/**
	 * Handles save request and prints XML.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Extract the XML fro the message
		/*BufferedReader br = request.getReader();
		String xml = IOUtils.toString(br);
		BLManager BPM = BLManagerImpl.getInstance();
		Enums.checkingAns ans = BPM.Decide(xml);
		switch (ans){
			case CAN:
				response.setStatus(HttpServletResponse.SC_OK);
				break;
			case CAN_NO_DB:
				response.setStatus(20);
				break;
			case CANT:
				response.setStatus(10);
				break;
			case TO_MANY_SHAPS:
				response.setStatus(50);
				break;
		}*/
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BLManager BPM = BLManagerImpl.getInstance();
		int ans = BPM.getAllobjects();

		response.setContentType("text/plain");
		response.getWriter().println(ans);
		response.setStatus(HttpServletResponse.SC_OK);

	}

}
