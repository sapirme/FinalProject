package flow.bgu.ac.il;

import Graph.*;
import Shapes.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;

import com.mxgraph.util.*;

import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;

import il.ac.bgu.cs.bp.bpjs.model.BProgram;

public class RunServlet extends HttpServlet {

	final static Logger LOG = LoggerFactory.getLogger(RunServlet.class);

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
		mxCodec codec = new mxCodec();
		String xml = IOUtils.toString(br);

		mxStylesheet stylesheet = new mxStylesheet();
// configure "figures" aka "vertex"
		{
			Map<String, Object> style = stylesheet.getDefaultVertexStyle();
			style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
			style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
			style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		}
// configure "lines" aka "edges"
		{
			Map<String, Object> style = stylesheet.getDefaultEdgeStyle();
			style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
			style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		}
		String[] shapes = new String[] {
				mxConstants.SHAPE_RECTANGLE,
				mxConstants.SHAPE_ELLIPSE,
				//mxConstants.SHAPE_DOUBLE_RECTANGLE,
				mxConstants.SHAPE_DOUBLE_ELLIPSE,
				mxConstants.SHAPE_RHOMBUS,
				mxConstants.SHAPE_LINE,
				mxConstants.SHAPE_IMAGE,
				mxConstants.SHAPE_ARROW,
				mxConstants.SHAPE_CURVE,
				mxConstants.SHAPE_LABEL,
				mxConstants.SHAPE_CYLINDER,
				mxConstants.SHAPE_SWIMLANE,
				mxConstants.SHAPE_CONNECTOR,
				mxConstants.SHAPE_ACTOR,
				mxConstants.SHAPE_CLOUD,
				mxConstants.SHAPE_TRIANGLE,
				mxConstants.SHAPE_HEXAGON,
		};
		Map<String, Map<String, Object>> styles = stylesheet.getStyles();
		for (String sh : shapes)
		{
			Map<String, Object> style = new HashMap<>();
			style.put(mxConstants.STYLE_SHAPE, sh);
			styles.put(sh, style);
		}

		mxGraph graph = new mxGraph(stylesheet);
		//System.out.println(xml);
		// Parses XML into graph
		Document doc = mxXmlUtils.parseXml(xml);
		codec = new mxCodec(doc);
		codec.decode(doc.getDocumentElement(), graph.getModel());

		// Renders graph to SVG
		mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer.drawCells(graph,
				null, 1, null, new mxCellRenderer.CanvasFactory()
				{
					public mxICanvas createCanvas(int width, int height)
					{
						return new mxSvgCanvas(mxDomUtils
								.createSvgDocument(width, height));
					}
				});
		String svgStr=mxXmlUtils.getXml(canvas.getDocument());
		//System.out.println(svgStr);
		//Pair<List<Shape>, List<Shape>>
		boolean ans=SVGParser.decide(svgStr);
		//response.getOutputStream().println("Succesfully deployed: sapir!!");
		//response.getOutputStream().println(functions);
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
