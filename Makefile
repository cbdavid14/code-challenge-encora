# Makefile.

dev:
	docker-compose -f docker-compose.dev.yml up -d
dev-down:
	docker-compose -f docker-compose.dev.yml down

qa:
	docker-compose -f docker-compose.qa.yml up -d

qa-down:
	docker-compose -f docker-compose.qa.yml down

build:
	./gradlew build
