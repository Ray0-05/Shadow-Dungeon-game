import bagel.util.Point;

import java.util.Properties;

public abstract class BattleRoom extends Room {
    // World objects that affect movement/visuals (e.g., walls, water)
    protected GameObject[] objects = new GameObject[0];

    // Enemies (overlappable; die on touch; doors unlock when all dead)
    protected Enemy[] enemies = new Enemy[0];

    public BattleRoom(Properties gameProps, String nameLabel) {
        super(gameProps, nameLabel);
        initialiseBattleRoom(gameProps, nameLabel);
    }

    // ---- Getters & Setters ----
    public void setObjects(GameObject[] objects) {
        this.objects = (objects != null) ? objects : new GameObject[0];
    }
    public GameObject[] getObjects() { return objects; }

    public void setEnemies(Enemy[] enemies) {
        this.enemies = (enemies != null) ? enemies : new Enemy[0];
    }
    public Enemy[] getEnemies() { return enemies; }

    // ---- Overriding the general room implementation ----
    @Override
    protected GameObject[] getCollidableObjects() {
        return objects;
    }

    @Override
    public boolean hasEnemy() {
        if (enemies == null) return false;
        for (Enemy e : enemies) {
            if (e != null && !e.isDefeated()) return true;
        }
        return false;
    }

    @Override
    public void resolveEnemyTouches(Player player) {
        if (enemies == null) return;
        boolean anyKilled = false;
        for (Enemy e : enemies) {
            if (e != null && !e.isDefeated()
                    && player.getBoundingBox().intersects(e.getBoundingBox())) {
                e.kill();
                anyKilled = true;
            }
        }
        if (anyKilled && !hasEnemy()) {
            unlockAllDoors();
        }
    }

    @Override
    public void resolveHazards(Player player) {
        if (objects == null) return;

        for (GameObject obj : objects) {
            if (obj instanceof River) {
                if (player.getBoundingBox().intersects(obj.getBoundingBox())) {
                    player.takeDamage(((River) obj).getDamagePerFrame());
                }
            }
        }
    }



    // ---- Rendering ----
    @Override
    public void render(){
        // Background
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0);

        // Objects (e.g., walls, water)
        if (objects != null) {
            for (GameObject obj : objects) obj.render();
        }

        // Enemies (alive ones only)
        if (enemies != null) {
            for (Enemy e : enemies) {
                if (!e.isDefeated()) e.render();
            }
        }

        // Doors last (so they appear above floor objects)
        for (Door door : getDoors()) {
            door.render();
        }
    }

    protected void initialiseBattleRoom(Properties gameProps, String labelOfRoom){
        // Read optional walls list from properties (semicolon-separated x,y pairs)
        String wallsCoordsRaw = gameProps.getProperty("wall."+ labelOfRoom);
        Point[] wallCoords = IOUtils.parsePointList(wallsCoordsRaw);

        // Read optional Water list from properties
        String riverTilesCoordsRaw = gameProps.getProperty("river." + labelOfRoom);
        Point[] riverTilesCoords = IOUtils.parsePointList(riverTilesCoordsRaw);
        double riverDamagePerFrame = Double.parseDouble(gameProps.getProperty("riverDamagePerFrame"));

        // Read in Enemies
        Enemy[] enemies = buildKeyBulletKinList(IOUtils.parsePointList(gameProps.getProperty("keyBulletKin." +
                ""+labelOfRoom)));

        // Initialise the game objects
        GameObject[] objs = new GameObject[wallCoords.length + riverTilesCoords.length];
        // Add each wall into the Room object list
        for (int i = 0; i < wallCoords.length; i++) {
            objs[i] = new Wall(wallCoords[i]);
        }
        // Add each river into the Room object list
        for (int i = wallCoords.length; i < wallCoords.length + riverTilesCoords.length; i++){
            objs[i]= new River(riverTilesCoords[i], riverDamagePerFrame);
        }

        // Initialise BattleRoom specific displays and attributes
        setDoors(new Door[] {new Door(gameProps.getProperty("primarydoor." + labelOfRoom)),
                new Door(gameProps.getProperty("secondarydoor." + labelOfRoom))});
        setObjects(objs);
        setEnemies(enemies);
    }

    private KeyBulletKin[] buildKeyBulletKinList(Point[] coordinates){
        KeyBulletKin[] KeyBulletKins = new KeyBulletKin[coordinates.length];
        int i = 0;
        for (Point coordinate : coordinates){
            KeyBulletKins[i++] = new KeyBulletKin(coordinate);
        }

        return KeyBulletKins;
    }
}
