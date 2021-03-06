package flow.bgu.ac.il;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import IllusionSystem.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class AllSimilarServlet extends HttpServlet {

	//final static Logger LOG = LoggerFactory.getLogger(AllSimilarServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598336877581962216L;

	// A hack that only works if one program is running at a time!
	//public static BProgram bprog;
	//public static BProgramRunner rnr;

	//private static Thread thread;

	private BLManager BPM;
	public AllSimilarServlet(BLManager bpm){
		this.BPM=bpm;
	}

	/**
	 * Handles save request and prints XML.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			BufferedReader br = request.getReader();
			String gsonAns = IOUtils.toString(br);
			Gson gson = new Gson();
			List<String> l = gson.fromJson(gsonAns, List.class);
			if (l == null || l.size() != 2) return;
			String xml = l.get(0);
			String svg = l.get(1);

			//BLManager BPM = BLManagerImpl.getInstance();
			List<Integer> lst = BPM.getSimilarObjects(xml, svg);
			if (lst == null) {
				response.setStatus(20);
			} else {
				String ans = new Gson().toJson(lst);

				response.setContentType("text/plain");
				response.getWriter().println(ans);
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}catch (Exception e){
			response.setStatus(500);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
