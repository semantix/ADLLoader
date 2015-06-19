#ADLLoader

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
* These libraries were added to Mayo Clinic's public maven repository : http://informatics.mayo.edu/maven
* To use the libararies directory (without this project ADLLoader), please add the following dependencies in your maven environment:

<dependency>
            <groupId>org.openEHR.adl2core</groupId>
            <artifactId>adl-parser</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.openEHR.adl2core</groupId>
            <artifactId>model-am</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.openEHR.adl2core</groupId>
            <artifactId>model-rm</artifactId>
            <version>1.0.0</version>
</dependency> 

* 
