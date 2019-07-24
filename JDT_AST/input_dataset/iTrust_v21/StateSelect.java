package edu.ncsu.csc.itrust.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import edu.ncsu.csc.itrust.enums.State;

/**
 * JSP tag that adds an HTML select for states, from the enum.
 * 
 * @see {@link State}
 * 
 * Also selects the current state
 */
public class StateSelect implements Tag {
	private PageContext pageContext;
	private Tag parent;
	private String name;
	private String value;

	/**
	 * StateSelect
	 */
	public StateSelect() {
		super();
	}

	/**
	 * doStarTag
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.write("<select name=\"" + getName() + "\" >");
			if (name == null || "".equals(name))
				out.write("<option value=\"\">Select State</option>");
			for (State state : State.values()) {
				String selected = state.toString().equals(getValue()) ? "selected=selected" : "";
				out.write("<option value=\"" + state.toString() + "\" " + selected + ">" + state.getName()
						+ "</option>");
			}
			out.write("</select>");
		} catch (IOException e) {
			//TODO
		}
		return SKIP_BODY;
	}

	/**
	 * doEndTag
	 * @return SKIP_BODY
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		return SKIP_BODY;
	}

	/**
	 * release
	 */
	public void release() {
	}

	/**
	 * setPageContext
	 * @param pageContext pageContext
	 */
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	/**
	 * setParent
	 * @param parent parent
	 */
	public void setParent(Tag parent) {
		this.parent = parent;
	}

	/**
	 * getParent
	 */
	public Tag getParent() {
		return parent;
	}

	/**
	 * getName
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName
	 * @param name anem
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getValue
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * setValue
	 * @param value value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
