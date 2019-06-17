package Illusion;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.io.mxCodec;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Svg {

    private mxStylesheet stylesheet = new mxStylesheet();
    private mxGraph graph;

    private String xml;
    private String svg;

    public String getSvg() {return svg;}
    public String getXml() {return xml;}

    public void setSvg(String svg){
        this.svg=svg;
    }
    public void setXml(String xml){
        this.xml=xml;
    }

    private String[] shapes = new String[] {
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



    public Svg(){
        figures();
        lines();
        Map<String, Map<String, Object>> styles = stylesheet.getStyles();
        for (String sh : shapes)
        {
            Map<String, Object> style = new HashMap<>();
            style.put(mxConstants.STYLE_SHAPE, sh);
            styles.put(sh, style);
        }

        graph = new mxGraph(stylesheet);
    }

    private void figures(){
        // configure "figures" aka "vertex"
        Map<String, Object> style = stylesheet.getDefaultVertexStyle();
        style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    }

    private void lines(){
        // configure "lines" aka "edges"
        Map<String, Object> style = stylesheet.getDefaultEdgeStyle();
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    }

    public String xml2svg(String xml){
        System.out.println("xml: \n"+xml);
        this.xml=xml;
        Document doc = mxXmlUtils.parseXml(xml);
        mxCodec codec = new mxCodec(doc);
        codec.decode(doc.getDocumentElement(), graph.getModel());
        mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer.drawCells(graph,
                null, 1, null, new mxCellRenderer.CanvasFactory()
                {
                    public mxICanvas createCanvas(int width, int height)
                    {
                        return new mxSvgCanvas(mxDomUtils
                                .createSvgDocument(width, height));
                    }
                });
        this.svg =  mxXmlUtils.getXml(canvas.getDocument());
        return this.svg;
    }




}
