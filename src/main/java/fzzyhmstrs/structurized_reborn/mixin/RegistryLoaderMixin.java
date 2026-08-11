package fzzyhmstrs.structurized_reborn.mixin;

import fzzyhmstrs.structurized_reborn.api.StructurePoolAddCallback;
import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolImpl;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(RegistryDataLoader.class)
public class RegistryLoaderMixin {

    @Inject(method = "load", at = @At("RETURN"), cancellable = true)
    private static void load(
            net.minecraft.server.packs.resources.ResourceManager resourceManager,
            List<net.minecraft.core.HolderLookup.RegistryLookup<?>> contextRegistries,
            List<RegistryDataLoader.RegistryData<?>> registriesToLoad,
            java.util.concurrent.Executor executor,
            CallbackInfoReturnable<CompletableFuture<net.minecraft.core.RegistryAccess.Frozen>> cir
    ) {
        CompletableFuture<net.minecraft.core.RegistryAccess.Frozen> original = cir.getReturnValue();

        cir.setReturnValue(
                original.thenApply(registryAccess -> {
                    Registry<StructureTemplatePool> templatePoolRegistry =
                            registryAccess.lookupOrThrow(Registries.TEMPLATE_POOL);

                    HolderGetter<StructureProcessorList> processorLookup =
                            registryAccess.lookupOrThrow(Registries.PROCESSOR_LIST);

                    for (StructureTemplatePool pool : templatePoolRegistry) {
                        Identifier id = templatePoolRegistry.getKey(pool);

                        if (id == null) {
                            continue;
                        }

                        StructurePoolAddCallback.EVENT.invoker().onAdd(
                                new FabricStructurePoolImpl(pool, id),
                                processorLookup
                        );
                    }

                    return registryAccess;
                })
        );
    }
}
