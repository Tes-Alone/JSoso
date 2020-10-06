package org.lightcodex.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lightcodex.Constants;
import org.llogger.LLogger;
import org.lxml.LXMLDocument;
import org.lxml.node.Node;
import org.lxml.node.NodeList;

public class ProjectManager {
	
	private Node root;
	
	public ProjectManager() {
		String file = Constants.Homes.configHome+"projects.xml";
		checkFile(file);
		try {
			root = new LXMLDocument(file).parse().getRoot();
		} catch (RuntimeException e) {
			root = new Node("projects");
			LLogger.getInstance().logWarning("Projects info load failed!");
		}
	}

	private void checkFile(String file) {
		File check = new File(file);
		if (!check.exists()) {
			root = new Node("projects");
			root.save(check.getAbsolutePath());
		}
	}
	
	public List<Project> listProjects() {
		List<Project> projects = new ArrayList<>();
		NodeList nodes = root.getNodeList();
		Node newRoot = new Node("projects");
		for (int i=0; i<nodes.size(); i++) {
			Node node = nodes.getNodeAt(i);
			String path = node.getAttrMap().getAttrValue("path");
			if (path != null) {
				Project project = new Project(path);
				if (project.exists()) {
					project.flush();
					projects.add(project);
					addProject(newRoot, project);
				} else {
					LLogger.getInstance().logWarning("Project '%s' not exists!", project.getFullPath());
				}
			}
		}
		root = newRoot;
		return projects;
	}
	
	private void addProject(Node n, Project project) {
		String path = project.getFullPath();
		Node node = n.getNodeList().getNodeByAttr("path", path);
		if (node == null) {
			node = new Node("project");
			node.getAttrMap().addAttr("path", path);
			n.getNodeList().addNode(node);
		}
	}
	
	public void addProject(Project project) {
		addProject(root, project);
	}
	
	public void removeProject(Project project) {
		String path = project.getFullPath();
		Node node = root.getNodeList().getNodeByAttr("path", path);
		if (node != null) {
			root.getNodeList().removeNode(node);
		}
	}
	
	public void save() {
		try {
			root.save(Constants.Homes.configHome+"projects.xml");
		} catch (RuntimeException e) {
			LLogger.getInstance().logWarning("Projects info save failed!");
		}
	}
	
	/*
	private static ProjectManager self;
	
	public static synchronized ProjectManager getInstance() {
		if (self == null) {
			self = new ProjectManager();
		}
		return self;
	}*/
}
