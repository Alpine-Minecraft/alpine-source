package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import java.lang.reflect.Field;
import java.util.List;

public class Reflector {
    public static ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
    public static ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
    public static ReflectorClass ResourcePackRepository = new ReflectorClass(ResourcePackRepository.class);
    public static ReflectorField ResourcePackRepository_repositoryEntries = new ReflectorField(ResourcePackRepository, List.class, 1);

    public static Object getFieldValue(Object p_getFieldValue_0_, ReflectorField p_getFieldValue_1_) {
        try {
            Field field = p_getFieldValue_1_.getTargetField();

            if (field == null) {
                return null;
            } else {
                return field.get(p_getFieldValue_0_);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static boolean setFieldValue(Object p_setFieldValue_0_, ReflectorField p_setFieldValue_1_, Object p_setFieldValue_2_) {
        try {
            Field field = p_setFieldValue_1_.getTargetField();

            if (field == null) {
                return false;
            } else {
                field.set(p_setFieldValue_0_, p_setFieldValue_2_);
                return true;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

    public static boolean matchesTypes(Class[] p_matchesTypes_0_, Class[] p_matchesTypes_1_) {
        if (p_matchesTypes_0_.length != p_matchesTypes_1_.length) {
            return false;
        } else {
            for (int i = 0; i < p_matchesTypes_1_.length; ++i) {
                Class oclass = p_matchesTypes_0_[i];
                Class oclass1 = p_matchesTypes_1_[i];

                if (oclass != oclass1) {
                    return false;
                }
            }

            return true;
        }
    }
}
