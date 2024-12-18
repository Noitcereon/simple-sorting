# Updating to New Minecraft Version

To update to a new Minecraft version you must do the following:

1. Update dependencies (files: `gradle.properties`, `fabric.mod.json`), e.g.
   * YetAnotherConfigLib (https://github.com/isXander/YetAnotherConfigLib)
   * Fabric (check these on https://fabricmc.net/develop)
2. Check Minecraft patch notes to see if there is anything that affects this mod. E.g. Java version upgrade.
3. Test if sorting still works (`gradlew.bat clean build runClient`)
4. Fix bugs, if any (and go back to step 3)
5. Release new version (both on GitHub and CurseForge)
