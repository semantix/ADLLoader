#ADLWorks

This Libaray loads ADL 2 Archetypes (in ADL format - .adl files) and returns in-memory objects.

Develper Note:
Apache Maven is used to create this library, and in maven environment
you get dependencies from a maven repository. Since ADL2 parser and
reference models were not available from a public maven repository, 
this project was created to access and use the parser in a user project.

The ADL Parser is available at 
https://github.com/openEHR/adl2-core

## Using ADL 2 Parser in this library involved:
* ADL 2 Parser and Reference Model project from its Github location. The project was compiled and jar libraries were created.
* These libraries were added to Mayo Clinic's public maven repository (as a third party library) : 

http://informatics.mayo.edu/maven

* To use the libararies directory (without this project ADLWorks), please add the following dependencies in your maven environment:
```
<dependency>
            <groupId>org.openehr.adl2-core</groupId>
            <artifactId>adl-parser</artifactId>
            <version>1.3.0</version>
        </dependency>
        <dependency>
            <groupId>org.openehr.adl2-core</groupId>
            <artifactId>model-am</artifactId>
            <version>1.3.0</version>
        </dependency>
        <dependency>
            <groupId>org.openEHR.adl2core</groupId>
            <artifactId>model-rm</artifactId>
            <version>1.3.0</version>
        </dependency>
        <dependency>
            <groupId>org.openEHR.adl2core</groupId>
            <artifactId>model-rm-opencimi</artifactId>
            <version>1.2.2</version>
        </dependency>
```
These libraries are availble from Maven Central https://repo1.maven.org/maven2/
These libraries also available at Mayo Clinic Maven Repository
```
<repository>
            <id>edu.informatics.maven.thirdparty</id>
            <name>Informatics Maven ThirdParty Repository</name>
            <url>http://informatics.mayo.edu/maven/content/repositories/thirdparty</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
</repository>
```
* All Other previous instructions are part of this library - ADLWorks.
