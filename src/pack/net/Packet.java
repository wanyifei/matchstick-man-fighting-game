package pack.net;

public abstract class Packet 
{
  public static enum PacketTypes
  {
    INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02), KEY(03),CLOGIN(04);
    
    private int packetID;
    private PacketTypes(int id)
    {
      this.packetID = id;
    }
    
    public int getID()
    {
      return packetID;
    }
  }
  
  public byte packetId;
  public Packet(int packetId)
  {
    this.packetId = (byte) packetId;
  }
  
  public abstract void writeData(GameClient client);
  public abstract void writeData(GameServer server);
  
  public String readData(byte[] data)
  {
    String message = new String(data).trim();
    return message.substring(2);
  }

  public abstract byte[] getData();

  public static PacketTypes lookupPacket(String packetId)
  {
    try 
    {
      return lookupPacket(Integer.parseInt(packetId));
    } catch (NumberFormatException e)
    {
      return PacketTypes.INVALID;
    }
  }

  public static PacketTypes lookupPacket(int id)
  {
    for(PacketTypes p : PacketTypes.values())
    {
      if(p.getID() == id)
      {
        return p;
      }
    }
    return PacketTypes.INVALID;
  } 

}
