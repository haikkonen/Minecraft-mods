package net.minecraft.src;

public class SMPManager{
    public static final int PACKET_C2S_PROP = 0;
    public static final int PACKET_C2S_REQUEST = 1;
    public static final int PACKET_S2C_PROP = 0;
    public static final int PACKET_S2C_MODULE = 1;
    public static final int PACKET_S2C_SEED = 2;

    public mod_OldDays core;

    public SMPManager(mod_OldDays c){
        core = c;
    }

    public void setSMPSettings(int id){
        OldDaysModule module = core.modules.get(id);
        for(int i = 0; i < module.properties.size(); i++){
            OldDaysProperty prop = module.getPropertyById(i + 1);
            if (!prop.allowedInSMP){
                prop.setSMPValue();
                module.last = prop.id;
                prop.onChange();
                core.texman.onTick();
            }
        }
    }
}