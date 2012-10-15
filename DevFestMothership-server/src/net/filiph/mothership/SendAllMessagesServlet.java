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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendAllMessagesServlet extends BaseServlet {

	/**
	 * Processes the request to add a new message.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<String> devices = Datastore.getDevices();
		String status = "";
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			Queue queue = QueueFactory.getQueue("gcm");
			// send a multicast message using JSON
			// must split in chunks of 1000 devices (GCM limit)
			int total = devices.size();
			List<String> partialDevices = new ArrayList<String>(total);
			int counter = 0;
			int tasks = 0;
			String messageText = "[";
			if (req.getParameter("notify")!=null) {
				messageText += "n";
			}
			if (req.getParameter("vibrate")!=null) {
				messageText += "v";
			}
			if (req.getParameter("update")!=null) {
				messageText += "u";
			}
			messageText += "]";
			if (req.getParameter("message") != null) {
				messageText += req.getParameter("message");
			}			
			for (String device : devices) {
				counter++;
				partialDevices.add(device);
				int partialSize = partialDevices.size();
				if (partialSize == Datastore.MULTICAST_SIZE || counter == total) {
					String multicastKey = Datastore.createMulticast(partialDevices);
					logger.fine("Queuing " + partialSize + " devices on multicast " + multicastKey);
					TaskOptions taskOptions = TaskOptions.Builder.withUrl("/send")
							.param(SendMessageServlet.PARAMETER_MULTICAST, multicastKey)
							.param(SendMessageServlet.PARAMETER_MESSAGE_TEXT, messageText).method(Method.POST);
					queue.add(taskOptions);
					partialDevices.clear();
					tasks++;
				}
				status = "Queued tasks to send " + tasks + " multicast messages to " + total + " devices";
			}
		}
		req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
		getServletContext().getRequestDispatcher("/home").forward(req, resp);
	}

}
