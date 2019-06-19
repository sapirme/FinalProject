package flow.bgu.ac.il;

import IllusionSystem.BLManager;
import IllusionSystem.BLManagerImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerEndpointConfig;
import java.awt.*;
import java.net.URI;
import java.util.Properties;

public class IllusionServer {
    private Server server;
    public BLManager blManager;
    public String url;

    private int useDefult()throws Exception {
        java.io.InputStream is = this.getClass().getResourceAsStream("program.properties");
        java.util.Properties p = new Properties();
        p.load(is);
        int localPort = Integer.parseInt(p.getProperty("local.port"));

        blManager = new BLManagerImpl(p.getProperty("db.address"), Integer.parseInt(p.getProperty("db.port")));
        return localPort;
    }

    public void init(String[] arguments) throws Exception {
        int localPort;
        if(arguments.length != 3) {
            localPort = useDefult();
        }
        else {
            try {
                localPort = Integer.parseInt(arguments[0]);
                blManager = new BLManagerImpl(arguments[1], Integer.parseInt(arguments[2]));
            }catch (Exception e){
                localPort = useDefult();
            }
        }

        //DAL_InterfaceImpl.setDB(p.getProperty("db.address"), Integer.parseInt(p.getProperty("db.port")));
		/*if(arguments.length != 3) {
			System.err.println("Syntax: java -jar <jar name> <local port> <db address> <db port>");
			System.exit(1);
		} else {*/
//			localPort = Integer.parseInt(arguments[0]);
//			DAL_InterfaceImpl.setDB(arguments[1], Integer.parseInt(arguments[2]));
//		}

        server = new Server(localPort);

        // Servlets
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new FlowOpenServlet()), "/open");
        context.addServlet(new ServletHolder(new CreateLllusionServlet(blManager)), "/run");
        context.addServlet(new ServletHolder(new AllObjectsServlet(blManager)), "/allObjects");
        context.addServlet(new ServletHolder(new NextObjectsServlet(blManager)), "/NextObjects");
        context.addServlet(new ServletHolder(new PrevObjectsServlet(blManager)), "/PrevObjects");
        context.addServlet(new ServletHolder(new LoadObject(blManager)), "/LoadObject");
        context.addServlet(new ServletHolder(new AllSimilarServlet(blManager)), "/allSimilar");
        context.addServlet(new ServletHolder(new LoginServlet(blManager)), "/login");
        context.addServlet(new ServletHolder(new MyObjectsServlet(blManager)), "/myObjects");


        ResourceHandler fileHandler = new ResourceHandler();
        fileHandler.setResourceBase(".");

        // Add javax.websocket support
        ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);

        // Add endpoint to server container
        ServerEndpointConfig cfg = ServerEndpointConfig.Builder.create(EventQueue.class, "/eventqueue").build();
        container.addEndpoint(cfg);

        HandlerList handlers = new HandlerList();


        handlers.setHandlers(new Handler[]{fileHandler, context});
        server.setHandler(handlers);
        url ="http://localhost:" + localPort + "/mxgraph/javascript/examples/grapheditor/www/index.html";
        System.out.println();
        System.out.println(">> Go to "+url);
        System.out.println();
    }

    public void start() throws Exception {

        server.start();

    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws Exception {
        server.join();
    }

    public void destroy() throws Exception {

    }

    public static void Openwin(String uri) throws Exception{
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://localhost:8090/mxgraph/javascript/examples/grapheditor/www/index.html"));
        }
    }

    /**
     * Point your browser to the displayed URL
     */
    public static void main(String[] args) throws Exception {
        IllusionServer myServer = new IllusionServer();
        myServer.init(args);
        try {
            myServer.start();
            Openwin(myServer.url);
            myServer.join();
            myServer.stop();
        }catch (Exception e){
            Openwin(myServer.url);
        }


    }
}
