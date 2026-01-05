run:
	docker compose up -d
	./gradlew :bettr-deployments:bettr-api:bootRun
