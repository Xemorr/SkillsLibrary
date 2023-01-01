# SkillsLibrary
My skills library for Superheroes2 and EnchantedBosses


## How do you install and use this library?

This library depends on ConfigurationData, so if you haven't installed it already, then go to this repository and follow the instructions.
https://github.com/Xemorr/ConfigurationData

__IntelliJ Instructions__
```
File -> New -> Project from Version Control 
Enter https://github.com/Xemorr/SkillsLibrary.git as the repository URL and click clone
Go to build.gradle.kts and either set an environment variable on your PC called "pluginFolder" that leads to the folder of your plugins folder or replace val folder = "your plugin folder directory".
Click the elephant reload icon in the top right, and wait for gradle to download / get the project ready.
When this is done, double hit LCTRL to bring up the command line executing window and type gradle publishToMavenLocal.
```

__Other__
```bash
Ensure you have an installation of gradle in the PATH on your computer
In a directory of your pleasing do,
git clone https://github.com/Xemorr/ConfigurationData.git
cd SkillsLibrary
gradle publishToMavenLocal
```

Now, it should be stored in your local maven repository!
You can depend on it in your own plugins like so
```xml
        <dependency>
            <groupId>me.xemor</groupId>
            <artifactId>SkillsLibrary</artifactId>
            <version>ENTER VERSION HERE</version>
            <scope>provided</scope>
        </dependency>
```

## How do you update the library?
Navigate back to where you initially installed the library (if you have forgotten, follow initial installation again)
Now execute 
```git
git pull
```
