package flow.bgu.ac.il;

import Algorithms.Enums;
import SystemObj.BLManager;
import SystemObj.BLManagerImpl;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

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
		String gsonAns = IOUtils.toString(br);
		Gson gson = new Gson();
		List<String> l= gson.fromJson(gsonAns, List.class);
		if (l==null || l.size()!=2) return;
		String xml = l.get(0);
		String svg = l.get(1);
		BLManager BPM = BLManagerImpl.getInstance();

		Enums.checkingAns ans = BPM.Decide(xml,svg);
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
