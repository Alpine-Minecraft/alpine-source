# **Documentation**

![GitHub all releases](https://img.shields.io/github/downloads/Alpine-Minecraft/alpine-source/total) 
![GitHub repo size](https://img.shields.io/github/repo-size/Alpine-Minecraft/alpine-source)

Contains the source for Alpine Client

Development Team: iSTeeWx_, Audi

## Introduction

Hello And Welcome to the documentation of Alpine! 
Here's how to use it!

This guide has all the necessary information to modify Apline to your needs, and even make it your own custom version!

### Setting up the project in IntelliJ IDEA
#### 1. Setting up the files
1. Download mcp918.zip from http://www.modcoderpack.com/
2. Extract it in a folder preferably named Alpine.
3. Run Decompile.bat if on Windows, or decompile.sh if on Linux.
4. Close the terminal window when it is at the "Decompiling" step.
5. Remove all folders and files except "jars" and "eclipse".
6. Cut ".classpath" and ".project" located in "eclipse/Client.
7. Paste them into a new folder called Alpine in the project root.
8. Delete the folder "eclipse" and its content.
9. Open a new terminal in the project root.
10. Make sure you have git installed.
11. Run "git clone https://github.com/Alpine-Minecraft/alpine-source.git".
12. Move the content of "alpine-source" into the project root (do not forget the ".git" folder as by default it is hidden).
13. Delete the now empty folder "alpine-source".
#### 2. Setting up the project
1. Open IntelliJ IDEA.
2. Press Ctrl+Shift+A to show the action menu.
3. Select "Import Project from Existing Sources"
4. Select the "Alpine" folder in the project root and press OK.
5. Tick "Import project from external model"
6. Select "Eclipse" and then press "Next"
7. Press "Next" again.
8. Select "Client" if it's not already selected and press "Next".
9. Press "Next" again.
10. Select a 1.8 JDK or download one by clicking the "+".
11. Then click "Create", don't worry if it says that the project refers to unknown JDK
12. Go into File > Project Structure, or press Ctrl+Alt+Shift+S
13. Go in category Modules > Sources
14. Delete all the content roots (on the right)
15. Click "Add Content Root"
16. Select the folder "minecraft" in "./src" and click "OK".
#### 3. Fixing the dependencies
1. Go into tab "Dependencies"
2. Put "Project SDK" in the dropdown "Module SDK".
3. Double click 1.8.8.jar
4. Click the "+" and control click on "1.8.8-natives" and "1.8.8.jar" in "./jars/versions/1.8.8"
5. Press "OK" if both the files are selected
6. Press "OK" again to close the config of the library
7. Click "Libraries" on the left pane
8. Click the plus and select "Java"
9. Select "lombok-xxxxx.jar" in "./libs" and click "OK"
10. Do the same for the "vecmath" library
11. Press "OK" to close the project structure settings
12. You can now delete the files ".classpath" and ".project"
#### 4. Setting the run configuration
1. Add a new Run configuration
2. Chose application and name the configuration "Alpine"
3. Set "Start" as the main class
4. Set the jars folder as the working environement.
5. Save you configuration.
#### 5. Setting up git integration
1. Go into File > Settings, or press Ctrl+Alt+S
2. Go into Version Control > Directory Mappings
3. Click the plus and select the project root in "Directory"
4. Select "Git" as the VCS
5. Press "OK" twice to close the settings 
### You are done !! You can now run the project.

