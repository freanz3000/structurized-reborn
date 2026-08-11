package fzzyhmstrs.structurized_reborn.api;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

/**
 * Represents a modifiable structure pool that has several helper methods for modders.
 */
public interface FabricStructurePool {
    /**
     * Adds a new {@link StructurePoolElement} to the {@link StructureTemplatePool}.
     * See the alternative {@link #addStructurePoolElement(StructurePoolElement, int)} for details.
     *
     * @param element the element to add
     */
    void addStructurePoolElement(StructurePoolElement element);

    /**
     * Adds a new {@link StructurePoolElement} to the {@link StructureTemplatePool}.
     *
     * @param element the element to add
     * @param weight  Minecraft handles weight by adding it that amount of times into a list.}
     */
    void addStructurePoolElement(StructurePoolElement element, int weight);

    /**
     * Gets the underlying structure pool.
     */
    StructureTemplatePool getUnderlyingPool();

    /**
     * Gets the identifier for the pool.
     */
    Identifier getId();
}
