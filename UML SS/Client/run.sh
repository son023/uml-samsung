#!/bin/bash

echo "===================================="
echo "Study Client Application"
echo "===================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

echo "Building application..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Build failed"
    exit 1
fi

echo ""
echo "Build successful!"
echo ""
echo "Starting application..."
echo "Default API URL: http://localhost:8080"
echo "To use a different URL, pass as argument: ./run.sh http://your-server:8080"
echo ""

# Run the application
if [ -z "$1" ]; then
    java -jar target/study-client-1.0.0.jar
else
    java -jar target/study-client-1.0.0.jar "$1"
fi
