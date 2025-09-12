Shadow Dungeon — Quick README

A tiny Bagel-based 2D dungeon. Move between rooms, grab treasure, avoid rivers, and clear enemies to unlock doors.

Play
Move: W A S D
Face: move mouse left/right of the player
Restart: ENTER (in Prep/End rooms)
Quit: ESC



------------------------Gameplay loop----------------------------

PrepRoom: safe lobby (optionally open its door with R).

BattleRoom: contains walls/rivers/treasure/enemies.

You spawn inside the entry door; enemies are hidden.
After you step off the door, enemies appear and the entry door closes (if enemies exist).
Touch enemies to defeat them. When all are gone, doors unlock.

EndRoom: shows Victory (via door) or Defeat (on death). ENTER to restart.

---------------------------Key objects---------------------------

Door: handles room transitions + entry-door logic.

Wall: solid. River: hurts while overlapping.

TreasureBox: overlap to collect coins.

Enemy (KeyBulletKin): overlap to defeat.

-----------------------------Code map-----------------------

ShadowDungeon (main loop) → GameMaster (state & transitions)

Room base + PrepRoom / BattleRoom / EndRoom

Player, GameObject, Door, Wall, River, TreasureBox, Enemy