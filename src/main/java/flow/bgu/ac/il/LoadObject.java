package flow.bgu.ac.il;

import Algorithms.Enums;
import SystemObj.BLManager;
import SystemObj.BLManagerImpl;
import com.google.gson.Gson;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class LoadObject extends HttpServlet {

	final static Logger LOG = LoggerFactory.getLogger(LoadObject.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598336877581962216L;

	// A hack that only works if one program is running at a time!
	public static BProgram bprog;
	public static BProgramRunner rnr;

	private static Thread thread;

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
		BLManager BPM = BLManagerImpl.getInstance();
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
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BLManager BPM = BLManagerImpl.getInstance();
		List<Integer> lst = BPM.getNextObjects();
		if (lst == null){
			response.setStatus(20);
		}
		else{
			String ans = new Gson().toJson(lst);

			response.setContentType("text/plain");
			response.getWriter().println(ans);
			response.setStatus(HttpServletResponse.SC_OK);
		}


	}*/

}
