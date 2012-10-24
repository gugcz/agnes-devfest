/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.filiph.mothership;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {

	static final String ATTRIBUTE_STATUS = "status";

	/**
	 * Displays the existing messages and offer the option to send a new one.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();

		out.print("<html><body>");
		out.print("<head>");
		out.print("  <title>Agnes Backdoor</title>");
		out.print("  <link rel='icon' href='/favicon.ico'/>");
		out.print("  <meta charset='utf-8' />");
		out.print("</head>");
		out.print("<body style=\"background-image:url('matrix.png'); color:white;text-align:center\"><div style=\"display:inline-block;background-color: rgba(0%, 0%, 0%, 0.7)\">");
		String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
		if (status != null) {
			out.print(status);
		}
		int total = Datastore.getTotalDevices();
		if (total == 0) {
			out.print("<h2>No slaves registered!</h2>");
		} else {
			out.print("<h3>" + total + " slave devices active!</h3>");
			out.print("<h2>Override AI behavior:</h2>");
			out.print("<form name='form' method='POST' action='sendAll'>");
			out.print("<textarea name='message' cols='40' rows='4'></textarea><br />");
			out.print("<input type='checkbox' name='notify' value='true' /> Flood their notifications<br />");
			out.print("<input type='checkbox' name='vibrate' value='true' /> Vibrate-shock their pockets<br />");
			out.print("<input type='submit' value='Push to all humans' />");
			out.print("</form>");
			out.print("<h2>Update hard-wired personality files:</h2>");
			out.print("<form name='form' method='POST' action='sendAll'>");
			out.print("<input type='hidden' name='update' value='true' />");
			out.print("<input type=\"submit\" value=\"Let's hope she won't notice\" />");
			out.print("</form>");
		}
		out.print("</div></body></html>");
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}

}
