# SkillsLibrary
My skills library for Superheroes2 and EnchantedBosses


## How do you install and use this library?

This library depends on ConfigurationData, so if you haven't installed it already, then go to this repository and follow the instructions.
https://github.com/Xemorr/ConfigurationData

```bash
In a directory of your pleasing do,
git clone https://github.com/Xemorr/SkillsLibrary.git
cd SkillsLibrary
mvn clean package install
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
