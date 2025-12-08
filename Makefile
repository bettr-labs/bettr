export JAVA_HOME := $(shell /usr/libexec/java_home -v 17)

run:
	./gradlew :bettr-deployments:bettr-api:bootRun
