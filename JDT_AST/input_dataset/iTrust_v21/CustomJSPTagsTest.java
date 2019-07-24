package edu.ncsu.csc.itrust.unit.serverutils;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createControl;
import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import junit.framework.TestCase;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.tags.ICD9CMLink;
import edu.ncsu.csc.itrust.tags.PatientNavigation;
import edu.ncsu.csc.itrust.tags.StateSelect;

/**
 * 
 *  
 * 
 */
public class CustomJSPTagsTest extends TestCase {

	// This class uses an advanced concept not taught in CSC326 at NCSU called Mock Objects.
	// Feel free to take a look at how this works, but know that you will not need to know mock objects
	// to do nearly everything in iTrust. Unless your assignment mentions mock objects somewhere, you should
	// not need them.
	//
	// But, if you are interested in a cool unit testing concept, feel free to look at this code as an
	// example.
	//
	// This class uses the EasyMock library to manage the mock objects. http://easymock.org/

	private PageContext mockContext;
	private IMocksControl ctrl;
	private JspWriter mockWriter;
	private Tag mockParent;

	@Override
	protected void setUp() throws Exception {
		ctrl = createControl();
		mockContext = ctrl.createMock(PageContext.class);
		mockWriter = ctrl.createMock(JspWriter.class);
		mockParent = ctrl.createMock(Tag.class);
		expect(mockContext.getOut()).andReturn(mockWriter).anyTimes();
	}

	public void testPatientNavHappy() throws Exception {
		PatientNavigation nav = new PatientNavigation();
		nav.setPageContext(mockContext);
		// we're okay with any strings written here - normally you want to be more specific
		mockWriter.write((String) anyObject());
		expectLastCall().anyTimes();
		ctrl.replay();

		String str = "Health Records";
		nav.setThisTitle(str);
		assertSame(str, nav.getThisTitle());
		assertEquals(Tag.SKIP_BODY, nav.doStartTag());
		assertEquals(Tag.SKIP_BODY, nav.doEndTag());
		nav.setParent(mockParent);
		assertSame(mockParent, nav.getParent());
		nav.release();

		ctrl.verify();
	}

	public void testPatientNavException() throws Exception {
		PatientNavigation nav = new PatientNavigation();
		nav.setPageContext(mockContext);
		// we're okay with any strings written here - normally you want to be more specific
		mockWriter.write((String) anyObject());
		expectLastCall().andThrow(new IOException()); // nothing done but stacktrace
		ctrl.replay();

		assertEquals(Tag.SKIP_BODY, nav.doStartTag());

		ctrl.verify();
	}

	public void testICD9CMLink() throws Exception {
		ICD9CMLink tag = new ICD9CMLink();
		tag.setPageContext(mockContext);
		// we're okay with any strings written here - normally you want to be more specific
		mockWriter.write((String) anyObject());
		expectLastCall().anyTimes();
		ctrl.replay();

		assertEquals(Tag.SKIP_BODY, tag.doStartTag());
		assertEquals(Tag.SKIP_BODY, tag.doEndTag());
		tag.setParent(mockParent);
		assertSame(mockParent, tag.getParent());
		String code = "100.1";
		tag.setCode(code);
		assertSame(code, tag.getCode());
		tag.setCode(100.1);
		assertEquals(code, tag.getCode());
		tag.release();

		ctrl.verify();
	}

	public void testICD9CMLinkException() throws Exception {
		ICD9CMLink tag = new ICD9CMLink();
		tag.setPageContext(mockContext);
		// we're okay with any strings written here - normally you want to be more specific
		mockWriter.write((String) anyObject());
		expectLastCall().andThrow(new IOException()); // nothing done but stacktrace
		ctrl.replay();

		assertEquals(Tag.SKIP_BODY, tag.doStartTag());

		ctrl.verify();
	}

	public void testStateSelect() throws Exception {
		StateSelect tag = new StateSelect();
		tag.setPageContext(mockContext);
		// we're okay with any strings written here - normally you want to be more specific
		mockWriter.write((String) anyObject());
		expectLastCall().anyTimes();
		ctrl.replay();

		assertEquals(Tag.SKIP_BODY, tag.doStartTag());
		assertEquals(Tag.SKIP_BODY, tag.doEndTag());
		tag.setParent(mockParent);
		assertSame(mockParent, tag.getParent());
		String name = "Something!";
		tag.setName(name);
		assertSame(name, tag.getName());
		String value = "Something!!";
		tag.setValue(value);
		assertSame(value, tag.getValue());
		tag.setName("");
		assertEquals(Tag.SKIP_BODY, tag.doStartTag());
		tag.setValue("NC");
		assertEquals(Tag.SKIP_BODY, tag.doStartTag());
		tag.release();

		ctrl.verify();
	}

	public void testStateSelectException() throws Exception {
		StateSelect tag = new StateSelect();
		tag.setPageContext(mockContext);
		// we're okay with any strings written here - normally you want to be more specific
		mockWriter.write((String) anyObject());
		expectLastCall().andThrow(new IOException()); // nothing done but stacktrace
		ctrl.replay();

		assertEquals(Tag.SKIP_BODY, tag.doStartTag());

		ctrl.verify();
	}
}
