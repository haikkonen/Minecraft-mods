package net.minecraft.src;

public class mod_OldDaysMobs extends mod_OldDays{
    public void load(){
        registerModule(this, 3);
        addProperty(1, "Mob AI",              4,     2,     "AI",            "", new String[]{"Infdev", "Alpha", "Beta", "1.2"});
        addProperty(2, "Animal panic",        true,  false, "AnimalsFlee",      "");
        addProperty(3, "Sheep eat grass",     true,  true,  "SheepEatGrass",    "");
        addProperty(4, "Spiders climb walls", true,  true,  "SpidersCanClimb",  "");
        addProperty(5, "Survival Test mobs",  false, false, "SurvivalTestMobs", "");
        addProperty(6, "Old skeleton fire",   false, false, "FastSkeletons",    "");
        addProperty(7, "Old mob health",      false, false, "OldHealth",        "");
        addProperty(8, "Allowed mobs",        11,    11,    "Mobs",             "", new String[]{"Classic", "Indev", "Alpha 1.0.8", "Alpha 1.0.11", "Alpha 1.0.14", "Alpha 1.2.0",
                                                                                                 "Beta 1.2", "Beta 1.7.3", "Beta 1.8.1", "1.1", "1.2"});
        addProperty(9, "Slime spawning",      4,     4,     "Slimes",           "", new String[]{"OFF", "Alpha 1.0.11", "Beta", "1.0"});
        addProperty(10,"Unnerfed endermen",   false, false, "Endermen",         "");
        loadModuleProperties();
    }

    public void callback (int i){
        switch(i){
            case 1: setBool(net.minecraft.src.EntityLiving.class, "newai", AI>3);
                    setBool(net.minecraft.src.EntityCreeper.class, "fixai", AI<4);
                    setBool(net.minecraft.src.EntitySkeleton.class, "fixai", AI<4);
                    setBool(net.minecraft.src.EntitySnowman.class, "fixai", AI<4);
                    setBool(net.minecraft.src.EntitySheep.class, "fixai", AI<4);
                    setBool(net.minecraft.src.EntityCreature.class, "jump", AI<3);
                    setBool(net.minecraft.src.EntityLiving.class, "infdevai", AI<2); break;
            case 2: setBool(net.minecraft.src.EntityAIPanic.class, "disablePanic", !AnimalsFlee);
                    setBool(net.minecraft.src.EntityCreature.class, "nopanic", !AnimalsFlee); break;
            case 3: setBool(net.minecraft.src.EntitySheep.class, "caneatgrass", SheepEatGrass); break;
            case 4: setBool(net.minecraft.src.EntitySpider.class, "canclimb", SpidersCanClimb); break;
            case 5: setBool(net.minecraft.src.EntityCreeper.class, "survivaltest", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntityCreature.class, "fastzombies", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntityZombie.class, "burns", !SurvivalTestMobs);
                    setBool(net.minecraft.src.EntitySkeleton.class, "survivaltest", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntitySpider.class, "survivaltest", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntitySheep.class, "survivaltest", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntitySheep.class, "hungry", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntityPig.class, "survivaltest", SurvivalTestMobs);
                    setBool(net.minecraft.src.EntityLiving.class, "survivaltest", SurvivalTestMobs); break;
            case 6: setBool(net.minecraft.src.EntitySkeleton.class, "fast", FastSkeletons); break;
            case 7: setBool(net.minecraft.src.EntitySheep.class, "oldhealth", OldHealth);
                    setBool(net.minecraft.src.EntitySpider.class, "oldhealth", OldHealth);
                    setBool(net.minecraft.src.EntityEnderman.class, "oldhealth", OldHealth); break;
            case 8: setInt(net.minecraft.src.EntityLiving.class, "nonewmobs", Mobs-1);
                    int color = 0;
                    if (Mobs>6){
                        color = 1;
                    }
                    if (Mobs>7){
                        color = 2;
                    }
                    setInt(net.minecraft.src.EntitySheep.class, "color", color);
                    setBool(net.minecraft.src.EntityOcelot.class, "allow", Mobs>=11);
                    setBool(net.minecraft.src.EntitySquid.class, "allow", Mobs>=7);
                    setBool(net.minecraft.src.EntitySlime.class, "allow", Mobs>=4); break;
            case 9: setInt(net.minecraft.src.EntitySlime.class, "slimeSpawn", Slimes-1); break;
            case 10:setBool(net.minecraft.src.EntityEnderman.class, "oldPicking", Endermen); break;
        }
    }

    public static int AI = 2;
    public static boolean AnimalsFlee = false;
    public static boolean SheepEatGrass = true;
    public static boolean SpidersCanClimb = true;
    public static boolean SurvivalTestMobs;
    public static boolean JumpingMobs = true;
    public static boolean FastSkeletons;
    public static boolean OldHealth = true;
    public static int Mobs = 11;
    public static int Slimes = 4;
    public static boolean Endermen;
}