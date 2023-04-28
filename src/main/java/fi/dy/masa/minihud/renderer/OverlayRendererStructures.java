package fi.dy.masa.minihud.renderer;

import java.util.Collection;
import org.lwjgl.opengl.GL11;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.IntBoundingBox;
import fi.dy.masa.minihud.config.RendererToggle;
import fi.dy.masa.minihud.util.DataStorage;
import fi.dy.masa.minihud.util.MiscUtils;
import fi.dy.masa.minihud.util.StructureData;
import fi.dy.masa.minihud.util.StructureType;

public class OverlayRendererStructures extends OverlayRendererBase
{
    public static final OverlayRendererStructures INSTANCE = new OverlayRendererStructures();

    private OverlayRendererStructures()
    {
    }

    @Override
    public boolean shouldRender(MinecraftClient mc)
    {
        if (RendererToggle.OVERLAY_STRUCTURE_MAIN_TOGGLE.getBooleanValue() == false)
        {
            return false;
        }

        for (StructureType type : StructureType.VALUES)
        {
            if (type.isEnabled() && type.existsInDimension(mc.world.getDimension()))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean needsUpdate(Entity entity, MinecraftClient mc)
    {
        int hysteresis = 16;

        return DataStorage.getInstance().structureRendererNeedsUpdate() ||
               Math.abs(entity.getX() - this.lastUpdatePos.getX()) > hysteresis ||
               Math.abs(entity.getY() - this.lastUpdatePos.getY()) > hysteresis ||
               Math.abs(entity.getZ() - this.lastUpdatePos.getZ()) > hysteresis;
    }

    @Override
    public void update(Vec3d cameraPos, Entity entity, MinecraftClient mc)
    {
        RenderObjectBase renderQuads = this.renderObjects.get(0);
        RenderObjectBase renderLines = this.renderObjects.get(1);
        BUFFER_1.begin(renderQuads.getGlMode(), VertexFormats.POSITION_COLOR);
        BUFFER_2.begin(renderLines.getGlMode(), VertexFormats.POSITION_COLOR);

        this.updateStructures(mc.world.getDimension(), this.lastUpdatePos, cameraPos, mc);

        BUFFER_1.end();
        BUFFER_2.end();

        renderQuads.uploadData(BUFFER_1);
        renderLines.uploadData(BUFFER_2);
    }

    @Override
    public void allocateGlResources()
    {
        this.allocateBuffer(GL11.GL_QUADS);
        this.allocateBuffer(GL11.GL_LINES);
    }

    private void updateStructures(DimensionType dimId, BlockPos playerPos, Vec3d cameraPos, MinecraftClient mc)
    {
        ArrayListMultimap<StructureType, StructureData> structures = DataStorage.getInstance().getCopyOfStructureData();
        int maxRange = (mc.options.viewDistance + 4) * 16;

        for (StructureType type : StructureType.VALUES)
        {
            if (type.isEnabled() && type.existsInDimension(dimId))
            {
                Collection<StructureData> structureData = structures.get(type);

                if (structureData.isEmpty() == false)
                {
                    this.renderStructuresWithinRange(type, structureData, playerPos, cameraPos, maxRange);
                }
            }
        }
    }

    private void renderStructuresWithinRange(StructureType type, Collection<StructureData> structureData, BlockPos playerPos, Vec3d cameraPos, int maxRange)
    {
        for (StructureData structure : structureData)
        {
            if (MiscUtils.isStructureWithinRange(structure.getBoundingBox(), playerPos, maxRange))
            {
                this.renderStructure(type, structure, cameraPos);
            }
        }
    }

    private void renderStructure(StructureType type, StructureData structure, Vec3d cameraPos)
    {
        Color4f color = type.getToggle().getColorMain().getColor();
        ImmutableList<IntBoundingBox> components = structure.getComponents();

        fi.dy.masa.malilib.render.RenderUtils.drawBox(structure.getBoundingBox(), cameraPos, color, BUFFER_1, BUFFER_2);

        if (components.isEmpty() == false)
        {
            if (components.size() > 1 || MiscUtils.areBoxesEqual(components.get(0), structure.getBoundingBox()) == false)
            {
                color = type.getToggle().getColorComponents().getColor();

                for (IntBoundingBox bb : components)
                {
                    fi.dy.masa.malilib.render.RenderUtils.drawBox(bb, cameraPos, color, BUFFER_1, BUFFER_2);
                }
            }
        }
    }
}
