package flow.bgu.ac.il;

import Object3D.Obj3DFile;
import Shapes.*;
import SystemObj.*;

import java.io.*;
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

		//SvgObj svgobj = new SvgObj();
		//String svgStr = svgobj.xml2svg(xml);
		boolean ans = BPServer.illusionObj.Decide(xml);
		//System.out.println("1: "+BPServer.illusionObj.getViewPoint1());
		//System.out.println("1: "+BPServer.illusionObj.getViewPoint2());
		if (ans) {
			System.out.println("first : 1");
			response.setStatus(HttpServletResponse.SC_OK);
			String text = BPServer.illusionObj.createObject();

			BufferedWriter output = null;

			try {
                System.out.println("hey 222222222222222222222");
				File file = new File("C:\\Users\\sapir\\Desktop\\exampleSap.stl");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
			} catch ( IOException e ) {
				e.printStackTrace();
			} finally {
				if ( output != null ) {
					try {
						output.close();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
				}
			}
		}
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
