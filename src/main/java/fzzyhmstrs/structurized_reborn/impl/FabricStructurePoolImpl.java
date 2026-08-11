package fzzyhmstrs.structurized_reborn.impl;

import com.mojang.datafixers.util.Pair;
import fzzyhmstrs.structurized_reborn.api.FabricStructurePool;
import fzzyhmstrs.structurized_reborn.mixin.StructurePoolAccessor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;


public class FabricStructurePoolImpl implements FabricStructurePool {
    private final StructureTemplatePool pool;
    private final Identifier id;

    public FabricStructurePoolImpl(StructureTemplatePool pool, Identifier id) {
        this.pool = pool;
        this.id = id;
    }

    @Override
    public void addStructurePoolElement(StructurePoolElement element) {
        addStructurePoolElement(element, 1);
    }

    @Override
    public void addStructurePoolElement(StructurePoolElement element, int weight) {
        //adds to elementCounts list; minecraft makes these immutable lists, so we replace them with an array list
        StructurePoolAccessor pool = (StructurePoolAccessor) getUnderlyingPool();

        if (pool.getElementWeights() instanceof ArrayList) {
            pool.getElementWeights().add(Pair.of(element, weight));
        } else {
            List<Pair<StructurePoolElement, Integer>> list = new ArrayList<>(pool.getElementWeights());
            list.add(Pair.of(element, weight));
            pool.setElementWeights(list);
        }

        // adds to elements list
        for (int i = 0; i < weight; i++) {
            pool.getElements().add(element);
        }
    }

    @Override
    public StructureTemplatePool getUnderlyingPool() {
        return pool;
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
