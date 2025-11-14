# CS3560-Prog3-API_Integration_Project

## How To Run

### 1. Clone the repository
```bash
git clone https://github.com/chablades/CS3560-Prog3-API_Integration_Project.git
cd CS3560-Prog3-API_Integration_Project
```

### 2. Create the resources folder
The project expects a configuration file at
```
src/main/resources/config.properties
```
If it does not exist, create the folder
```
src/
  main/
    java/
    resources/      <- create this folder
```

### 3. Create `config.properties`
Create the `config.properties` file inside the resources folder. Then add your **Spotify API** credentials in this format:
```properties
spotify.client.id=YOUR_CLIENT_ID_HERE
spotify.client.secret=YOUR_CLIENT_SECRET_HERE
```
Make sure **NOT** to include quotes when putting your credentials.
This file is gitignored, and will be protected

### 4. Run the project from root
Since this is a Maven project, run main using this command:
```bash
mvn clean compile exec:java
```

