package edu.ncsu.csc.itrust.unit.exception;

import java.io.IOException;
import org.apache.jasper.runtime.JspWriterImpl;

public class MockJSPWriter extends JspWriterImpl {
	public String input = "";

	@Override
	public void print(String arg0) throws IOException {
		this.input += arg0;
	}
}
