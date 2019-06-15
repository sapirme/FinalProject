package flow.bgu.ac.il;

import DAL.DAL_InterfaceImpl;
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

public class BPServer {
    private Server server;


    public void init(String[] arguments) throws Exception {
        java.io.InputStream is = this.getClass().getResourceAsStream("program.properties");
        java.util.Properties p = new Properties();
        p.load(is);
        int localPort = Integer.parseInt(p.getProperty("local.port"));
        DAL_InterfaceImpl.setDB(p.getProperty("db.address"), Integer.parseInt(p.getProperty("db.port")));
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
        context.addServlet(new ServletHolder(new CreateLllusionServlet()), "/run");
        context.addServlet(new ServletHolder(new AllObjectsServlet()), "/allObjects");
        context.addServlet(new ServletHolder(new NextObjectsServlet()), "/NextObjects");
        context.addServlet(new ServletHolder(new PrevObjectsServlet()), "/PrevObjects");
        context.addServlet(new ServletHolder(new LoadObject()), "/LoadObject");
        context.addServlet(new ServletHolder(new AllSimilarServlet()), "/allSimilar");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new MyObjectsServlet()), "/myObjects");


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

        System.out.println(">> Go to http://localhost:" + localPort + "/mxgraph/javascript/examples/grapheditor/www/index.html");
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

    /**
     * Point your browser to the displayed URL
     */
    public static void main(String[] args) throws Exception {
        BPServer bpserver = new BPServer();
        bpserver.init(args);
        bpserver.start();
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://localhost:8090/mxgraph/javascript/examples/grapheditor/www/index.html"));
        }

        bpserver.join();
        bpserver.stop();
    }
}
