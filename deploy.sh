#!/bin/bash

# Simple version - assumes docker-compose.yml is in current directory

echo "Stopping and removing existing containers..."
docker-compose down

echo "Building images with --no-cache..."
docker-compose build --no-cache

echo "Starting services in detached mode..."
docker-compose up -d

echo "Done! Current status:"
docker-compose ps