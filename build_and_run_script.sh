#!/usr/bin/env sh

passed_port=$1
default_port=8080
port=${passed_port:-$default_port}

./gradlew build && java -jar build/libs/ticket-booking-app-0.0.1.jar --server.port=$((port))
