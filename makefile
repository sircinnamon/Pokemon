JFLAGS = -cp json.jar:.
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Pokemon.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class