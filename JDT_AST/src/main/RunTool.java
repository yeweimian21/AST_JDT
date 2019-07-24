package main;

import parse.ParseCode;
import relationship.GetRelationship;
import util.common.ClassUtil;

import java.io.File;

public class RunTool {

    public static void main(String[] args) {
        // the Java source code DataSet to be parsed
        String dataSetName = "iTrust_v21";

        ClassUtil.setup(dataSetName);

        // parse the Java code, output as the xml format
        ParseCode.parseSourceCode();

        // get the relationship between class
        GetRelationship.getClassRelationship();

    }
}
