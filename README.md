# Simple Sorting
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![available-for-Fabric](https://img.shields.io/badge/Available%20for-Fabric-dbd0b4?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAcBAMAAACNPbLgAAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9TpX5UHMwgIpihOtlFRRxLFYtgobQVWnUwufQLmjQkKS6OgmvBwY/FqoOLs64OroIg+AHi6uKk6CIl/i8ptIj14Lgf7+497t4BQr3MNKsrAmi6bSZjUSmTXZUCr+iHiADG0Cszy4inFtPoOL7u4ePrXZhndT735xhQcxYDfBJxhBmmTbxBPLtpG5z3iUVWlFXic+JJky5I/Mh1xeM3zgWXBZ4pmunkPLFILBXaWGljVjQ14hnikKrplC9kPFY5b3HWylXWvCd/YTCnr6S4TnMUMSwhjgQkKKiihDJshGnVSbGQpP1oB/+I60+QSyFXCYwcC6hAg+z6wf/gd7dWfnrKSwpGge4Xx/kYBwK7QKPmON/HjtM4AfzPwJXe8lfqwNwn6bWWFjoCBreBi+uWpuwBlzvA8JMhm7Ir+WkK+TzwfkbflAWGboG+Na+35j5OH4A0dbV8AxwcAhMFyl7v8O6e9t7+PdPs7wd+dXKrd9SjeQAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+cLFAcgIbOcUjoAAAAbUExURQAAAB0tQTg0KoB6bZqSfq6mlLyynMa8pdvQtJRJT6UAAAABdFJOUwBA5thmAAAAAWJLR0QB/wIt3gAAAF5JREFUGNN10FENwCAMhOFqOQuzMAtYOAtYqGw6mkEvhL59yR9Ca5YDqyOC465eKYqQm6LoCkVwnwQOBYKdeA5l51zhFtrsnPmg6m3Z2akk15dFH1lWFQVxlUFv+2sAJlA9O7NwQRQAAAAASUVORK5CYII=)](https://fabricmc.net/)

Simple Sorting is a Minecraft mod - made with [Fabric](https://github.com/FabricMC/fabric) - which aims to add one function to Minecraft: inventory sorting.

Note: If you want to use this mod on a dedicated server, the server needs to have the the mod installed as well.

## Installation

There are two ways to install Simple Sorting: manual or semi-automatic with CurseForge.

### CurseForge
If you don't want to manually install it, you can use CurseForge: https://www.curseforge.com/minecraft/mc-mods/noits-simple-sorting

### Manual
This mod requires Fabric Loader and Fabric API. You can get them here: https://fabricmc.net/ if you haven't already.

In addition, since v0.7.0+1.20.1, it also requires [YetAnotherConfigLib-v3](https://github.com/isXander/YetAnotherConfigLib).

1. Go to the [latest release](https://github.com/Noitcereon/simple-sorting/releases).
2. Download the `noits-simple-sorting-<VERSION>.jar`
3. Put it into your mods folder 
   - The mods folder should be located in your .minecraft folder (search for %appdata% and go into the .minecraft folder from there)
         - If the mods folder doesn't exist create it. It MUST be named mods exactly. 
         - ![Mods folder in .minecraft folder](./docs/readme-assets/create-mod-folder.png)

   - The mods folder should contain, at least, 3 files like this:
   - ![Mods Folder with the 3 minimum required files](./docs/readme-assets/mods-folder-content.png)
    
4. Run the game with your installed Fabric version.

## Usage

Once you've installed it, you should see a Sort button appear in chests and barrels like so:

![image](https://user-images.githubusercontent.com/40148361/184692781-0f81b868-616b-49d9-83c9-838f1a5f162a.png)

By default, the button appear on the right side of the screen and near the top. (70% away from the left, 5% away from the top). 

Sort button location can be changed in the configuration, which is opened by pressing F4 (by default) and should look like this:

![config-menu.png](docs/readme-assets/config-menu.png)


## Maintainer
[Noitcereon](https://github.com/Noitcereon)

## Contributions
Pull Requests are welcome, however please try to make [Atomic Commits](https://www.aleksandrhovhannisyan.com/blog/atomic-git-commits/) when doing so.

Be sure to take a look at the [dev documentation](./docs/dev-documentation), especially the [design-decisions](./docs/dev-documentation/design-decisions.md), where the [public api](https://github.com/Noitcereon/simple-sorting/blob/main/docs/dev-documentation/design-decisions.md#versioning) is also defined.

## License
[MIT](https://github.com/Noitcereon/simple-sorting/blob/1.19/LICENSE) © Thomas "Noit" A.
