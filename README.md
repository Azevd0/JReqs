# JReqs

JReqs is an API development platform built with Java SE for sending HTTP requests such as **GET, POST, PUT, and others**.

The project uses Java's built-in `java.net.http` API together with Jakarta JSON for working with JSON data. The repository is organized as a Java module named `ClientRequestSender`. citeturn1file0turn4file0

## Requirements

Before building the project, make sure you have:

- **Java Development Kit (JDK)** installed, with both `javac`, `jar`, and `java` available in your terminal.
- A terminal opened at the **root of the cloned repository**.

The required JSON libraries are already included in `src/libs`, so they do not need to be downloaded separately. citeturn5file0

## Clone the repository

```bash
git clone https://github.com/Azevd0/JReqs.git
cd JReqs
```

## Build the application

The build process below compiles the Java source files into `.class` files and then packages them into `JReqs.jar`.

### 1. Create a build directory

Create a directory in the root of the repository to store the compiled `.class` files:

```bash
mkdir jreqsApp
```

You may use another folder name, but the same name must be used in the commands below.

### 2. Compile the Java source files

From the repository root, run:

```bash
javac -d jreqsApp --module-path src/libs src/module-info.java src/httpService/*.java src/main/*.java src/menu/*.java src/utils/*.java
```

This command compiles the project's Java source files and places the generated `.class` files inside the `jreqsApp` directory.

### 3. Build the JAR file

Create the executable JAR:

```bash
jar --create --file JReqs.jar --main-class main.App -C jreqsApp .
```

The generated `JReqs.jar` file will be created in the repository root.

### 4. Run the application

Start JReqs with:

```bash
java --module-path JReqs.jar:src/libs --module ClientRequestSender/main.App
```

> **Windows note:** On Windows, replace `:` in the module path with `;`:
>
> ```powershell
> java --module-path JReqs.jar;src/libs --module ClientRequestSender/main.App
> ```

## Project structure

The main source code is located under `src/` and is divided into the following packages: citeturn3file0

```text
JReqs/
├── src/
│   ├── httpService/   # HTTP request functionality
│   ├── libs/          # External JAR dependencies
│   ├── main/          # Application entry point
│   ├── menu/          # Application menu / interface
│   ├── utils/         # Utility classes
│   └── module-info.java
├── jreqsApp/          # Generated build output (created locally)
└── JReqs.jar          # Generated executable JAR
```

The `src/libs` directory currently contains the Jakarta JSON API and Parsson JARs used by the application. citeturn5file0

## Cleaning the build

To remove the generated build directory and JAR file, run:

```bash
rm -rf jreqsApp JReqs.jar
```

On Windows PowerShell:

```powershell
Remove-Item -Recurse -Force jreqsApp
Remove-Item JReqs.jar
```

After cleaning, repeat the build steps above to compile the application again.

## Notes

- Run all commands from the repository root.
- The project is a modular Java application, and `module-info.java` declares the `java.net.http` and `jakarta.json` module dependencies. citeturn4file0
- The build commands assume the dependency JARs remain in `src/libs`. citeturn5file0
