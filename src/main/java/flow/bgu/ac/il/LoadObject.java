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
		/*
		String str = "<mxGraphModel grid=\"1\" gridSize=\"10\" guides=\"1\" tooltips=\"1\" connect=\"1\" arrows=\"0\" fold=\"1\" page=\"1\" pageScale=\"1\" pageWidth=\"827\" pageHeight=\"1169\" background=\"#ffffff\"><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><mxCell id=\"2\" value=\"\" style=\"rounded=0;endArrow=none;html=1;\" edge=\"1\" parent=\"1\"><mxGeometry width=\"50\" height=\"50\" relative=\"1\" as=\"geometry\"><mxPoint y=\"240\" as=\"sourcePoint\"/><mxPoint x=\"825\" y=\"240\" as=\"targetPoint\"/></mxGeometry></mxCell></root></mxGraphModel>";
		response.setContentType("text/plain");
		response.getWriter().println(str);
		response.setStatus(HttpServletResponse.SC_OK);
		return;*/
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
