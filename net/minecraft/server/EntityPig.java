package net.minecraft.server;

public class EntityPig extends EntityAnimal {

    private final PathfinderGoalPassengerCarrotStick bp;

    public EntityPig(World world) {
        super(world);
        this.a(0.9F, 0.9F);
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
        this.goalSelector.a(2, this.bp = new PathfinderGoalPassengerCarrotStick(this, 0.3F));
        this.goalSelector.a(3, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Item.CARROT_STICK.id, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Item.CARROT.id, false));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    public boolean bb() {
        return true;
    }

    protected void ax() {
        super.ax();
        this.a(GenericAttributes.a).a(10.0D);
        this.a(GenericAttributes.d).a(0.25D);
    }

    protected void be() {
        super.be();
    }

    public boolean bu() {
        ItemStack itemstack = ((EntityHuman) this.passenger).aV();

        return itemstack != null && itemstack.id == Item.CARROT_STICK.id;
    }

    protected void a() {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", this.hasSaddle());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSaddle(nbttagcompound.getBoolean("Saddle"));
    }

    protected String r() {
        return "mob.pig.say";
    }

    protected String aK() {
        return "mob.pig.say";
    }

    protected String aL() {
        return "mob.pig.death";
    }

    protected void a(int i, int j, int k, int l) {
        this.makeSound("mob.pig.step", 0.15F, 1.0F);
    }

    public boolean a(EntityHuman entityhuman) {
        if (super.a(entityhuman)) {
            return true;
        } else if (this.hasSaddle() && !this.world.isStatic && (this.passenger == null || this.passenger == entityhuman)) {
            entityhuman.mount(this);
            return true;
        } else {
            return false;
        }
    }

    protected int getLootId() {
        return this.isBurning() ? Item.GRILLED_PORK.id : Item.PORK.id;
    }

    protected void dropDeathLoot(boolean flag, int i) {
        int j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);

        for (int k = 0; k < j; ++k) {
            if (this.isBurning()) {
                this.b(Item.GRILLED_PORK.id, 1);
            } else {
                this.b(Item.PORK.id, 1);
            }
        }

        if (this.hasSaddle()) {
            this.b(Item.SADDLE.id, 1);
        }
    }

    public boolean hasSaddle() {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setSaddle(boolean flag) {
        if (flag) {
            this.datawatcher.watch(16, Byte.valueOf((byte) 1));
        } else {
            this.datawatcher.watch(16, Byte.valueOf((byte) 0));
        }
    }

    public void a(EntityLightning entitylightning) {
        if (!this.world.isStatic) {
            EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);

            entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.world.addEntity(entitypigzombie);
            this.die();
        }
    }

    protected void b(float f) {
        super.b(f);
        if (f > 5.0F && this.passenger instanceof EntityHuman) {
            ((EntityHuman) this.passenger).a((Statistic) AchievementList.u);
        }
    }

    public EntityPig b(EntityAgeable entityageable) {
        return new EntityPig(this.world);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.id == Item.CARROT.id;
    }

    public PathfinderGoalPassengerCarrotStick bQ() {
        return this.bp;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
