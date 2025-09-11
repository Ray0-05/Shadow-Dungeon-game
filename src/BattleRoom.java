import bagel.util.Point;

import java.util.ArrayList;
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
    @Override
    public void resolveCollectibles(Player player) {
        if (objects == null) return;

        for (GameObject obj : objects) {
            if (obj instanceof TreasureBox) {
                TreasureBox t = (TreasureBox) obj;
                if (!t.isCollected() && player.getBoundingBox().intersects(t.getBoundingBox())) {
                    t.collect(player); // grants coins + hides itself
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

    // --- Initialisation of BattleRoom Specific stufss ---
    protected void initialiseBattleRoom(Properties p, String room) {
        Point[] walls   = IOUtils.parsePointList(p.getProperty("wall." + room));
        Point[] rivers  = IOUtils.parsePointList(p.getProperty("river." + room));
        Point[] enemies = IOUtils.parsePointList(p.getProperty("keyBulletKin." + room));

        Double riverDpf = parseDouble(p.getProperty("riverDamagePerFrame"));
        if (rivers.length > 0 && riverDpf == null) {
            throw new IllegalStateException("Missing riverDamagePerFrame for room: " + room);
        }

        ArrayList<GameObject> objs = new ArrayList<>();
        for (Point pt : walls)  objs.add(new Wall(pt));
        for (Point pt : rivers) objs.add(new River(pt, riverDpf));        // safe: checked above
        for (TreasureBox t : parseTreasureBoxes(p.getProperty("treasurebox." + room))) objs.add(t);

        setDoors(parseDoors(p, room));
        setObjects(objs.toArray(new GameObject[0]));
        setEnemies(buildKeyBulletKinList(enemies));
    }

    // --- Helpers (small) ---
    private Double parseDouble(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Double.parseDouble(s.trim()); } catch (NumberFormatException e) { return null; }
    }

    private TreasureBox[] parseTreasureBoxes(String raw) {
        if (raw == null || raw.isBlank() || raw.equals("0")) return new TreasureBox[0];
        String[] entries = raw.split(";");
        ArrayList<TreasureBox> out = new ArrayList<>(entries.length);
        for (String e : entries) {
            String[] p = e.trim().split(",");
            if (p.length < 3) continue;
            try {
                double x = Double.parseDouble(p[0].trim());
                double y = Double.parseDouble(p[1].trim());
                int coins = Integer.parseInt(p[2].trim());
                out.add(new TreasureBox(new Point(x, y), coins));
            } catch (NumberFormatException ignore) { /* skip malformed */ }
        }
        return out.toArray(new TreasureBox[0]);
    }

    private Door[] parseDoors(Properties p, String room) {
        ArrayList<Door> ds = new ArrayList<>(2);
        String a = p.getProperty("primarydoor." + room);
        String b = p.getProperty("secondarydoor." + room);
        if (a != null && !a.isBlank()) ds.add(new Door(a));
        if (b != null && !b.isBlank()) ds.add(new Door(b));
        return ds.toArray(new Door[0]);
    }

    private KeyBulletKin[] buildKeyBulletKinList(Point[] pts) {
        if (pts == null || pts.length == 0) return new KeyBulletKin[0];
        KeyBulletKin[] arr = new KeyBulletKin[pts.length];
        for (int i = 0; i < pts.length; i++) arr[i] = new KeyBulletKin(pts[i]);
        return arr;
    }

}
