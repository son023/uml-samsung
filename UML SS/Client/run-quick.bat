@echo off
echo ====================================
echo Study Client Application - Quick Run
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

REM Check if JAR exists
if not exist "target\study-client-1.0.0.jar" (
    echo JAR file not found. Building first...
    call run.bat %1
    exit /b %errorlevel%
)

echo Starting application...
echo API URL: %1
echo.

REM Run the application
if "%1"=="" (
    java -jar target\study-client-1.0.0.jar
) else (
    java -jar target\study-client-1.0.0.jar %1
)

pause
