# Design Decisions

This file will contain brief overview of design decisions made, that should be heeded when continuing development of this mod.

## Keep it Simple
This mod should only be used to provide ONE simple sort mechanism with limited configuration. This is to make it easy
to migrate to new versions in case of breaking API changes, in addition to making it easy to maintain in general.

## Identifier Location

All identifiers (instances of the `Identifier` class) should either be in the class SimpleSortingMod or SimpleSortingModClient, depending
on whether the identifier is only client side or used both on both server and client. This makes it easy to get an
overview of which ids exist and helps with understanding where they're supposed to be used.

Below is an example from `SimpleSortingMod.java`:

```java
public static final Identifier INVENTORY_SORT_REQUEST_ID = new Identifier(ModInfo.MOD_ID, "inventory-sort-request");
```

## Versioning

The mod uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html) and appends Minecraft version at the end in the format:

`Major.Minor.Patch+MinecraftVersion`

Example: `0.7.0+1.20.1` is version 0.7.0 of the mod, which targets Minecraft version 1.20.1

The public API is defined as:
- Configuration options displayed to the user.
- Sort button defaults
- The sorting algorithm

Examples of breaking change:
- Removing a configuration option.
- Changing the way the sort button sorts inventory.
