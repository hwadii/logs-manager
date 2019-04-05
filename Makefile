JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $*.java

CLASSES = anneau/tp3/*.java

build: $(CLASSES:.java=.class)

rmic: 
	rmic anneau.tp3.GestionnaireAnneau; rmic anneau.tp3.ProgrammeSite; rmic anneau.tp3.ServeurCentral

clean:
	$(RM) anneau/tp3/*.class