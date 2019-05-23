package flow.bgu.ac.il;

import Algorithms.Enums;
import SystemObj.BLManager;
import SystemObj.BLManagerImpl;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class CreateLllusionServlet extends HttpServlet {

	final static Logger LOG = LoggerFactory.getLogger(CreateLllusionServlet.class);

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
		BufferedReader br = request.getReader();
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
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Cache-control", "private, no-cache, no-store");
		response.setHeader("Expires", "0");

		// response.getWriter().println(createGraph(request));
		response.setStatus(HttpServletResponse.SC_OK);*/
		System.out.println("handle");
		BLManager BPM = BLManagerImpl.getInstance();
		boolean ans = BPM.CreateObject();
		if (ans){
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else{
			response.setStatus(20);
		}

	}

}
