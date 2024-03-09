# Cohabify
This is the repository for the PWA Cohabify.

# Backend
This project contains mainly the Spring Boot 3 backend for the PWA Cohabify.

# Set up instructions

## Requirements

1. Java 17
2. Maven  3.9.5 or later (The project is bundled with Maven Wrapper, that is, a Maven executable mvnw and mvnw.cmd for linux/mac-os and windows, so feel free to use that instead of installing a system wide maven).
3. MongoDB 7
3. An IDE of your choice
4. A Git client

## General project set up instructions

1. Clone the project in a folder of your choice.
2. Install with Maven
3. Rename application.properties.example to remove ".example" and change the MongoDB uri in the application.properties, if needed.
4. Now you are ready to execute this Spring Boot application

You can follow either the command line set up or one of the IDEs.

## Command line

1. Go to the backend folder
2. Installing: mvnw install -e
3. Properties: Rename as you like.
4. Running: mvnw spring-boot:run

## IDE GUI specific instructions

### Visual Studio Code

The following extensions are recommended but not necessary:
- Extension pack for Java
- Spring Boot Dashboard

It is recommended to execute the command line commands in a terminal instead of using the GUI.

1. Cloning: Command (Open with Ctrl+Shift+P) "Git: clone".
2. Installing (Two options, extensions required):  
    - Command: "Maven: Execute commands..." and select install.
    - Command: "Explorer: Focus on maven view", in the opened maven view right click the project, run maven commands and install.
3. Properties: Rename as you like.
4. Running (Three options, extensions required):  
    - Open the Spring Boot Dashboard view and press run.
    - Command: "Spring Boot Dashboard: Run...".
    - Command: "Maven: Execute commands...", select "custom..." and input "spring-boot:run".

### Eclipse for Java developers

The following marketplace solutions are recommended but not necessary:
- Spring Boot Dashboard

Instructions: 
1. Cloning:  
    - Open Git Repositories view and clone the repository (Don't import it yet, but copy the clone directory destination or set one different yourself).
    - Import as "Existing Maven Projects" using the copied clone directory destination.
2. Installing: Right click the project and Run as "Maven install".
3. Properties: Rename as you like.
4. Running (Three options, extensions required):  
    - Open the Boot Dashboard view and press start.
    - Right click the project and Run as "9 Spring Boot App".
    - Right click the class "CohabifyApplication.java" and Run as "Java application".

# Frontend
The Vue.js frontend project for the PWA Cohabify is located in the folder "Frontend".

# Set up instructions

## Requirements

1. Node.js v20.10.0 or newer
2. NPM 10.2.3 or newer (No need to install as it is part of Node.js)

## General project set up instructions

1. Install with npm.
2. Rename .env.example to remove ".example" and change any needed properties.
3. Now you are ready to execute this Vue.js application.

You can follow either the command line set up or one of the IDEs.

## Command line

1. Go to frontend folder
2. Installing: npm install
3. Environment: Rename as you like.
4. Running: npm run dev

## IDE GUI specific instructions

### Visual Studio Code

The following extensions are recommended but not necessary:
- Vue Language Features (Volar)

Instructions:
- Follow the command line instructions in a terminal.

