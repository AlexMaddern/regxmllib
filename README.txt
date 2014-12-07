 __   ___  __                        __  
|__) |__  / _` \_/ |\/| |    |    | |__) 
|  \ |___ \__> / \ |  | |___ |___ | |__) 


INTRODUCTION
============

regxmllib is a collection of tools and low-level libraries for the creation of 
RegXML (SMPTE ST 2001-1) representations of MXF header metadata
(SMPTE ST 377-1). 

regxmllib is implemented in pure Java. Netbeans 8.0 is used for development.

ARCHITECTURE
============

At the heart of regxmllib is the FragmentBuilder.fragmentFromTriplet() method
that creates an XML fragment from a KLV group given a context consisting of
a RegXML metadictionary and a collection of KLV groups from which strong
references are resolved. The rules engine implement in 
FragmentBuilder.fragmentFromTriplet() is intended to follow the rules specified
in ST 2001-1 as closely as possible.

The RegXML dictionary can be read from an XML file or created from SMPTE 
Metadata Registers.

The SMPTE Metadata Registers can be read from a set of XML files or imported 
from the latest SMPTE Register draft (as an Excel spreadsheet.)

regxmllib includes a minimal MXF and KLV parser.


PACKAGES
========

com.sandflow.smpte.klv : Classes for parsing KLV triplets and associated
    structures (ST 336)
    
com.sandflow.smpte.mxf: Classes for parsing MXF structures (ST 377-1)

com.sandflow.smpte.register: Classes for building, writing and reading SMPTE
    metadata registers (ST 335, ST 395, ST 400, ST 2003)
    
com.sandfow.smpte.regxml: Classes for managing RegXML metadictionaries and
    creating RegXML representations of MXF structures

TOOLS
=====

RegXMLDump: dumps either the first Essence Descriptor or the entire header
            metadata of an MXF file as a RegXML structure
            
XLSRegistersToXML: converts SMPTE registers contained in a spreadsheet to an
                   XML-based representation.
                   
XMLRegistersToDict: converts XML-based registers to a RegXML metadictionary

GenerateXMLSchemaDocuments: generates XSDs for the Registers
                            created by XLSRegistersToXML


BUILD TARGETS
=============

build-regxml-schemas: generates a RegXML XSD at /dict/RegXML.xsd based on
                      the output of build-regxml-dict

build-xml-registers: generates XML-based Registers at /register based on
                     the contents of register/input (see DEPENDENCIES)
                     
build-regxml-dict: generates a RegXML Metadictionary at /dict/RegXML.xml
                   based on the ouput of build-xml-registers
                   
build-register-schemas: generates at /register/schemas the XSDs for the
                        Registers created by build-xml-registers 
                            
KNOWN ISSUES
============

Issues are tracked at [1]

[1] https://github.com/palemieux/regxml/issues


DIRECTORIES AND NOTABLE FILES
=============================

/regxmllib                              Source code and build artifacts

/regxmllib/build.xml                    Build script (Ant)

/regxmllib/build.properties             Constants, e.g. directory paths, used
                                        by the build script

/regxmllib/nbproject                    Netbeans project files

/regxmllib/src/repoversion.properties   Java properties file containing the 
                                        repo version (generated at build time)

/dict                                   Stub directory containing the RegXML
                                        dictionaries used by the library

/register                               Stub directory containing the XML
                                        Registers used and generated by the
                                        library 

/register/input                         Stub directory for source CSV registers 
                                        extracted from [1]
                                        
/xsl/regxml-dict-to-xsd.xsl             XSL Stylesheet used to generate a RegXML
                                        XSD from a RegXML Metadictionary
           
           
DEPENDENCIES
============

The build system expects the following files to be present under
"register/input". These files are used to generate corresponding XML Registers
(under "/register") and, ultimately, the RegXML MetaDictionary at 
"/dict/regxml.xml".

groups-smpte-ra-frozen-20140304.2118.csv
labels-smpte-ra-frozen-20140304.2118.csv
types-smpte-ra-frozen-20140304.2118.csv
elements-smpte-ra-frozen-20140304.2118.csv

These files are generated by exporting as CSV the four registers at [1].

[1] https://kws.smpte.org/kws/groups/30mr10wg-def/download.php/27754/smpte-ra-frozen-20140304.2118.xls