package edu.ncsu.csc.itrust.unit.dao.family;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.FamilyMemberBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class FamilyMembersTest extends TestCase implements Comparator<FamilyMemberBean> {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private FamilyDAO familyDAO = factory.getFamilyDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		gen.icd9cmCodes();
		gen.family();
	}

	public void testGetNoParents() throws Exception {
		assertEquals(0, familyDAO.getParents(5).size());
	}

	public void testGetBothParents() throws Exception {
		List<FamilyMemberBean> parents = familyDAO.getParents(3);
		assertEquals(2, parents.size());
		Collections.sort(parents, this);
		assertEquals("Dad", parents.get(0).getFirstName());
		assertEquals("", parents.get(0).getLastName());
		assertEquals("Dad ", parents.get(0).getFullName());
		assertEquals("Mom", parents.get(1).getFirstName());
		assertEquals("Parent", parents.get(0).getRelation());
	}

	public void testGetAllSiblings() throws Exception {
		List<FamilyMemberBean> siblings = familyDAO.getSiblings(3);
		assertEquals(3, siblings.size());
		Collections.sort(siblings, this);
		assertEquals("Sib1", siblings.get(0).getFirstName());
		assertEquals("Sib2", siblings.get(1).getFirstName());
		assertEquals("Sib3", siblings.get(2).getFirstName());
		assertEquals("Sibling", siblings.get(0).getRelation());
	}

	public void testGetChildrenWithPerson() throws Exception {
		List<FamilyMemberBean> children = familyDAO.getChildren(3);
		assertEquals(2, children.size());
		Collections.sort(children, this);
		assertEquals("Kid1", children.get(0).getFirstName());
		assertEquals("Kid2", children.get(1).getFirstName());
		assertEquals("Child", children.get(0).getRelation());
	}

	public void testGetChildrenWithMom() throws Exception {
		// Note that you don't get Patient 9
		assertEquals(3, familyDAO.getChildren(4).size());
	}

	public int compare(FamilyMemberBean o1, FamilyMemberBean o2) {
		return o1.getFirstName().compareTo(o2.getFirstName());
	}
}