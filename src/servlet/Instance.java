package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dataset.TaskDistribution;
import datastructure.Task;

@WebServlet("/Task")
public class Instance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Instance() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Task task = TaskDistribution.getInstance().getATask();
		
		JSONObject instJSON = constructInstJSON(task);
		
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().print(instJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	private JSONObject constructInstJSON(Task task) {
		JSONObject instJSON = new JSONObject();

		instJSON.put("Word", task.getWord());

		JSONArray sentenceListArray = constructSentenceListArray(task.getWord(), task.getSentenceList());
		instJSON.put("SentenceList", sentenceListArray);

		return instJSON;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray constructSentenceListArray(String word, List<String> sentenceList) {
		JSONArray sentenceListArray = new JSONArray();
		for(String sentence : sentenceList) {
			String sentenceShow = sentence.replace(word, "<font class='keyword'>"+word+"</font>");
			sentenceListArray.add(sentenceShow);
		}
		return sentenceListArray;
	}
}
