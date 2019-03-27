package flow.bgu.ac.il;

import Shapes.*;
import SystemObj.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;

import il.ac.bgu.cs.bp.bpjs.model.BProgram;

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

		SvgObj svgobj = new SvgObj();
		String svgStr = svgobj.xml2svg(xml);
		boolean ans=SVGParser.decide(svgStr);

		if (ans)
			response.setStatus(HttpServletResponse.SC_OK);
		else
			response.setStatus(10);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Cache-control", "private, no-cache, no-store");
		response.setHeader("Expires", "0");

		// response.getWriter().println(createGraph(request));
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
