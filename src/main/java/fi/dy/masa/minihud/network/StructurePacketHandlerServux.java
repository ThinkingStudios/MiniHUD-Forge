package fi.dy.masa.minihud.network;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import fi.dy.masa.malilib.network.IPluginChannelHandler;
import fi.dy.masa.malilib.util.Constants;
import fi.dy.masa.minihud.MiniHUD;
import fi.dy.masa.minihud.util.DataStorage;

public class StructurePacketHandlerServux implements IPluginChannelHandler
{
    public static final int PROTOCOL_VERSION = 1;
    public static final int PACKET_S2C_METADATA = 1;
    public static final int PACKET_S2C_STRUCTURE_DATA = 2;

    public static final StructurePacketHandlerServux INSTANCE = new StructurePacketHandlerServux();

    private static final Identifier CHANNEL = new Identifier("servux:structures");
    private boolean registered;
    private int timeout;

    public void reset()
    {
        this.registered = false;
    }

    @Override
    public Identifier getChannel()
    {
        return CHANNEL;
    }

    @Override
    public void onPacketReceived(PacketByteBuf buf)
    {
        int id = buf.readVarInt();

        MiniHUD.printDebug("StructurePacketHandlerServux#onPacketReceived(): type: {} (old timeout: {}, old reg: {})",
                           id, this.timeout, this.registered);

        if (id == PACKET_S2C_STRUCTURE_DATA && this.registered)
        {
            NbtCompound tag = buf.readNbt();

            if (tag != null)
            {
                NbtList structures = tag.getList("Structures", Constants.NBT.TAG_COMPOUND);
                MiniHUD.printDebug("StructurePacketHandlerServux#onPacketReceived(): structures; list size: {}", structures.size());
                DataStorage.getInstance().addOrUpdateStructuresFromServer(structures, this.timeout, true);
            }
        }
        else if (id == PACKET_S2C_METADATA)
        {
            NbtCompound tag = buf.readNbt();

            if (tag != null &&
                tag.getInt("version") == PROTOCOL_VERSION &&
                tag.getString("id").equals(CHANNEL.toString()))
            {
                this.timeout = tag.getInt("timeout");
                this.registered = true;
                DataStorage.getInstance().setIsServuxServer();
                MiniHUD.printDebug("StructurePacketHandlerServux#onPacketReceived(): register; timeout: {}", this.timeout);
            }
        }
    }
}
