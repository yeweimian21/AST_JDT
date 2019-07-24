package edu.ncsu.csc.itrust.unit.serverutils;

import static org.easymock.classextension.EasyMock.*;
import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import junit.framework.TestCase;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.dao.ProductionConnectionDriver;

/**
 * 
 *  
 * 
 */
public class ProductionConnectionDriverTest extends TestCase {

	// This class uses an advanced concept not taught in CSC326 at NCSU called Mock Objects.
	// Feel free to take a look at how this works, but know that you will not need to know mock objects
	// to do nearly everything in iTrust. Unless your assignment mentions mock objects somewhere, you should
	// not need them.
	//
	// But, if you are interested in a cool unit testing concept, feel free to look at this code as an
	// example.
	//
	// This class uses the EasyMock library to manage the mock objects. http://easymock.org/

	private IMocksControl ctrl;
	private InitialContext mockContext;
	private Connection mockConnection;
	private DataSource mockDataSource;

	@Override
	protected void setUp() throws Exception {
		ctrl = createControl();
		mockContext = ctrl.createMock(InitialContext.class);
		mockConnection = ctrl.createMock(Connection.class);
		mockDataSource = ctrl.createMock(DataSource.class);
	}

	public void testProductionConnnectionDriver() throws Exception {
		ProductionConnectionDriver pcd = new ProductionConnectionDriver(mockContext);
		expect(mockContext.lookup("java:comp/env")).andReturn(mockContext).once();
		expect(mockContext.lookup("jdbc/itrust")).andReturn(mockDataSource).once();
		expect(mockDataSource.getConnection()).andReturn(mockConnection).once();
		ctrl.replay();

		assertSame(mockConnection, pcd.getConnection());

		ctrl.verify();
	}

}
