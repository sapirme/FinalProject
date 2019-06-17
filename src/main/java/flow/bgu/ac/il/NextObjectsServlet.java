package flow.bgu.ac.il;

import IllusionSystem.BLManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NextObjectsServlet extends HttpServlet {

	//final static Logger LOG = LoggerFactory.getLogger(NextObjectsServlet.class);

	/**
	 * 
	 */
	//private static final long serialVersionUID = -1598336877581962216L;

	// A hack that only works if one program is running at a time!
	////public static BProgram bprog;
	//public static BProgramRunner rnr;

	//private static Thread thread;

	private BLManager BPM;
	public NextObjectsServlet(BLManager bpm){
		this.BPM=bpm;
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//BLManager BPM = BLManagerImpl.getInstance();
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


	}

}
