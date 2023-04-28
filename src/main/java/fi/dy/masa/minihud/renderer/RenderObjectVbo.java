package fi.dy.masa.minihud.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;

public class RenderObjectVbo extends RenderObjectBase
{
    protected final VertexBuffer vertexBuffer;
    protected final VertexFormat format;
    protected final boolean hasTexture;

    public RenderObjectVbo(int glMode, VertexFormat format)
    {
        super(glMode);

        this.vertexBuffer = new VertexBuffer(format);
        this.format = format;

        boolean hasTexture = false;

        // This isn't really that nice and clean, but it'll do for now...
        for (VertexFormatElement el : this.format.getElements())
        {
            if (el.getType() == VertexFormatElement.Type.UV)
            {
                hasTexture = true;
                break;
            }
        }

        this.hasTexture = hasTexture;
    }

    @Override
    public void uploadData(BufferBuilder buffer)
    {
        this.vertexBuffer.submitUpload(buffer);
    }

    @Override
    public void draw(net.minecraft.client.util.math.MatrixStack matrixStack)
    {
        //RenderSystem.pushMatrix();

        if (this.hasTexture)
        {
            RenderSystem.enableTexture();
        }

        this.vertexBuffer.bind();
        this.format.startDrawing(0L);
        this.vertexBuffer.draw(matrixStack.peek().getModel(), this.getGlMode());
        this.format.endDrawing();

        if (this.hasTexture)
        {
            RenderSystem.disableTexture();
        }

        VertexBuffer.unbind();
        //RenderSystem.popMatrix();
    }

    @Override
    public void deleteGlResources()
    {
        this.vertexBuffer.close();
    }
}
