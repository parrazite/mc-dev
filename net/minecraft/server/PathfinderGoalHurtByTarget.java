package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    boolean a;

    public PathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag) {
        super(entitycreature, false);
        this.a = flag;
        this.a(1);
    }

    public boolean a() {
        return this.a(this.c.getLastDamager(), false);
    }

    public void c() {
        this.c.setGoalTarget(this.c.getLastDamager());
        if (this.a) {
            double d0 = this.f();
            List list = this.c.world.a(this.c.getClass(), AxisAlignedBB.a().a(this.c.locX, this.c.locY, this.c.locZ, this.c.locX + 1.0D, this.c.locY + 1.0D, this.c.locZ + 1.0D).grow(d0, 10.0D, d0));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.c != entitycreature && entitycreature.getGoalTarget() == null) {
                    entitycreature.setGoalTarget(this.c.getLastDamager());
                }
            }
        }

        super.c();
    }
}
