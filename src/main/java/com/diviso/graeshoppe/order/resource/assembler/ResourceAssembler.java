package com.diviso.graeshoppe.order.resource.assembler;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diviso.graeshoppe.order.client.bpmn.api.TasksApi;

@Component
public class ResourceAssembler {

	@Autowired
	TasksApi tasksApi;

	public CommandResource toResource(String processInstanceId) {

		@SuppressWarnings("unchecked")
		List<LinkedHashMap<String, String>> list = ((List<LinkedHashMap<String, String>>) tasksApi
				.getTasks(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
						null, null, null, processInstanceId, null, null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null, null, null, null, null, null)
				.getBody().getData());
		String taskId = null;
		String taskName = null;
		if (list.size() != 0) {
			taskId = list.get(0).get("id");
			taskName = list.get(0).get("name");
		}
		CommandResource commandResource = new CommandResource();
		commandResource.setNextTaskId(taskId);
		commandResource.setNextTaskName(taskName);
		return commandResource;
	}

}
