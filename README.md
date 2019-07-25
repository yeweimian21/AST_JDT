# AST_JDT
Parse the Java source code. Build Abstract Syntax Tree(AST) with Eclipse Java Development Tools (JDT).

The main function : 

1. The tool can parse the Java source code, get the information of the Java classes, and output as xml format.

2. The tool can obtain the relationships between Java classes.

## 1. The Directory

- input_dataset : The Java source code dataset to be parsed.

- output_result : The output result files.

- jar : The Java related dependent jar.

- src : The source code of project.

## 2. How to run

**Step 1**. Copy the Java source code dataset that you want to parse to `input_dataset` directory.

**Step 2**. Edit the variable `dataSetName` in `RunTool` class. Modify variable `dataSetName` to the name of dataset to be parse. 

The [RunTool](https://github.com/yeweimian21/AST_JDT/blob/master/JDT_AST/src/main/RunTool.java) class is in the `main` package.

``` Java
...
public class RunTool {

    public static void main(String[] args) {
        // the Java source code DataSet to be parsed
        String dataSetName = "iTrust_v21";
        ...
    }
}
```

**Step 3**. Just run the main method of `RunTool` class. You will get the result in the `output_result` directory.

## 3. The result

### 3.1 The information of Java class

The information of Java class is in `class_xml` directory.

 - class_xml : Parse the Java source code, get the information of the Java class, and output as xml format.

Example : `MonitorAdverseEventAction.xml`

``` xml

<Class>
    <Id>495</Id>
    <Package>edu.ncsu.csc.itrust.action</Package>
    <ClassName>MonitorAdverseEventAction</ClassName>
    <SuperClass></SuperClass>
    <SuperInterfaceList>
        <SuperInterface></SuperInterface>
    </SuperInterfaceList>
    <FieldList>
        ...
        <Field>
            <FieldName>loggedInMID</FieldName>
            <FieldType>long</FieldType>
        </Field>
        <Field>
            <FieldName>emailer</FieldName>
            <FieldType>EmailUtil</FieldType>
        </Field>
        <Field>
            <FieldName>messenger</FieldName>
            <FieldType>SendMessageAction</FieldType>
        </Field>
        ...
    </FieldList>
    <MethodList>
        ...
        <Method>
            <MethodName>getReports</MethodName>
            <ReturnType>AdverseEventBean</ReturnType>
            <ParameterList>
                <ParameterType>boolean</ParameterType>
                <ParameterType>String</ParameterType>
                <ParameterType>String</ParameterType>
            </ParameterList>
            <ThrowExceptionList>
                <ExceptionType>ITrustException</ExceptionType>
                <ExceptionType>FormValidationException</ExceptionType>
                <ExceptionType>SQLException</ExceptionType>
                <ExceptionType>ParseException</ExceptionType>
            </ThrowExceptionList>
        </Method>
        ...
    </MethodList>
</Class>
```

### 3.2 The relationship of Java classes

The relationship of Java classes is in `class_relationship` directory.

- class_relationship : The relationship between Java class.

---

The relationship type directory : 

- inherit : The class inherit superclass.

- implement : The class implement interface.

- attribute : The attribute in class.

- parameter : The parameter of method in class.

- return : The return of method in class.

- exception : The exception thrown by method in class.

---

The output format directory : 

- xml : The relatioinship expressed by xml format.

Example : `AddUserTest.xml`

``` xml
<RelationList>
    ...
    <RelationPair>
        <Class1>AddUserTest</Class1>
        <Class2>AuthDAO</Class2>
        <RelationType>attribute</RelationType>
    </RelationPair>
    ...
</RelationList>
```

Description : The class `AddUserTest` has an attribute which type is class `AuthDAO`.

- class_name : The relationship expressed by class name.

Example : `EditOfficeVisitAction.txt`

```
...

EditOfficeVisitAction HospitalBean return

...
```

Description : The class `EditOfficeVisitAction` has a method which return type is class `HospitalBean`.

- class_id : The relationship expressed by class id.

Example : `EditHealthHistoryAction.txt`

```
239 251 inherit
```

Description : The class(id = `239`) inherit the class(id = `251`). The class(id = `251`) is the superclass of the class(id = `239`).

