package fzzyhmstrs.structurized_reborn.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

@Mixin(StructureTemplatePool.class)
public interface StructurePoolAccessor {
    @Accessor(value = "templates")
    ObjectArrayList<StructurePoolElement> getElements();

    @Accessor(value = "rawTemplates")
    List<Pair<StructurePoolElement, Integer>> getElementWeights();

    @Mutable
    @Accessor(value = "rawTemplates")
    void setElementWeights(List<Pair<StructurePoolElement, Integer>> list);
}
