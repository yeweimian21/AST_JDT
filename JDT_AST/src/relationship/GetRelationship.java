package relationship;

public class GetRelationship {

    public static void getClassRelationship() {

        // get the inherit relationship between class
        ClassInheritRelation classInheritRelation = new ClassInheritRelation();
        classInheritRelation.writeClassInheritRelationshipToXmlFile();
        classInheritRelation.getInheritRelationshipClassName();
        classInheritRelation.getInheritRelationshipClassId();

        // get the implement relationship between class
        ClassImplementRelationship classImplementRelationship = new ClassImplementRelationship();
        classImplementRelationship.writeClassImplementRelationshipToXmlFile();
        classImplementRelationship.getImplementRelationshipClassName();
        classImplementRelationship.getImplementRelationshipClassId();

        // get the attribute relationship between class
        ClassAttributeRelation classAttributeRelation = new ClassAttributeRelation();
		classAttributeRelation.writeClassAttributeRelationshipToXmlFile();
		classAttributeRelation.getAttributeRelationshipClassName();
		classAttributeRelation.getAttributeRelationshipClassId();

        // get the parameter relationship between class
        ClassParameterRelationship classParameterRelationship = new ClassParameterRelationship();
        classParameterRelationship.writeClassParameterRelationshipToXmlFile();
        classParameterRelationship.getParameterRelationshipClassName();
        classParameterRelationship.getParameterRelationshipClassId();

        // get the return relationship between class
        ClassReturnRelationship classReturnRelationship = new ClassReturnRelationship();
        classReturnRelationship.writeClassReturnRelationshipToXmlFile();
        classReturnRelationship.getReturnRelationshipClassName();
        classReturnRelationship.getReturnRelationshipClassId();

        // get the exception relationship between class
        ClassExceptionRelationship classExceptionRelationship = new ClassExceptionRelationship();
        classExceptionRelationship.writeClassExceptionRelationshipToXmlFile();
        classExceptionRelationship.getExceptionRelationshipClassName();
        classExceptionRelationship.getExceptionRelationshipClassId();
    }
}
