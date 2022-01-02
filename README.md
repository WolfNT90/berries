# Updating this soon!
---
# BerriesJar
Sweet and prickly tweaks for your modern Minecraft server

![Issues](https://img.shields.io/github/issues/WolfNT90/berriesjar) ![Forks](https://img.shields.io/github/forks/WolfNT90/berriesjar) ![Stars](https://img.shields.io/github/stars/WolfNT90/berriesjar) ![License](https://img.shields.io/github/license/WolfNT90/berriesjar) ![GitHub Workflow Status](https://img.shields.io/github/workflow/status/WolfNT90/berriesjar/Java%20CI%20with%20Maven)
----
BerriesJar is a simple plugin for Bukkit-based Minecraft servers, made to bring simple fixes and tweaks to the vanilla experience.

#### What kind of tweaks?
 - [x] Skip the night when the majority of online players are asleep
 - [x] Spawn skeletons that wield weapons other than bows
 - [x] ^ optionally with axes
 - [x] Spawn zombies with a speed boost on them
 - [x] Disable baby zombies, because they're annoying
 - [x] A unique kick message for when players are kicked as opposed to voluntarily leaving the game
 - [x] Sound effects for players chatting, leaving, joining, being kicked and crafting.
 - [ ] Command to make your pet(s) glow, in case you're having trouble finding them
 - [ ] Command to manually teleport your pet(s) to you

```yml
gameplayTweaks:
   # Whether or not sleeping should forward time to day when half of the online players are asleep
   simplifySleep: true
   # Whether we should also spawn skeletons that use melee weapons
   melee-skeletons: true
   # Whether or not those skeletons should ever spawn with axes
   melee-skeletons-with-axes: false
   # If some zombies should spawn with a speed potion effect
   faster-zombies: false
   # Disable baby zombies from spawning
   no-zombie-babies: true
   # Add a unique chat message for manually disconnected players
   unique-kick-message: true

sounds:
   chatMessages: true
   joinMessages: true
   leaveMessages: false
   kickMessages: false
   crafting: true
   crafting-in-inventory: false
```

