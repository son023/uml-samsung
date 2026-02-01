@echo off
echo ====================================
echo Study Client Application
echo ====================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo Building application...
call mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed
    pause
    exit /b 1
)

echo.
echo Build successful!
echo.
echo Starting application...
echo Default API URL: http://localhost:8080
echo To use a different URL, edit this script or pass as argument
echo.

REM Run the application
if "%1"=="" (
    java -jar target\study-client-1.0.0.jar
) else (
    java -jar target\study-client-1.0.0.jar %1
)

pause
