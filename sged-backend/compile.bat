@echo off
REM Script para compilar SGED Backend con Maven

setlocal enabledelayedexpansion

REM Configurar PATH de Maven
set MAVEN_HOME=C:\Program Files\Maven\apache-maven-3.9.x
set PATH=%MAVEN_HOME%\bin;%PATH%

REM Navegar al directorio del backend
cd /d "%~dp0"

echo.
echo ====================================
echo  Compilando SGED Backend
echo ====================================
echo.

REM Limpiar compilaciones previas y compilar
call mvn clean compile

REM Verificar si la compilación fue exitosa
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Compilación completada exitosamente
    echo.
    echo Próximo paso: mvn package (para crear JAR)
) else (
    echo.
    echo ✗ Error en la compilación
    pause
    exit /b 1
)

endlocal
pause
