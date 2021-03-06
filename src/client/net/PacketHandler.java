package client.net;

import java.util.Calendar;

import client.net.packets.AddPlayerPacket;
import client.net.packets.KeyboardInputPacket;
import client.net.packets.MessagePacket;
import client.net.packets.Packet;
import client.net.packets.UpdatePlayerPacket;

public class PacketHandler {

	public static Packet[] packets = new Packet[32];
	
	public static void loadPackets(){
		packets[1] = new AddPlayerPacket(01, null, null);
		packets[2] = new UpdatePlayerPacket(02, null, null);
		packets[3] = new KeyboardInputPacket(03, null, null);
	}
	
	public static Packet buildPacket(int id, String senderIP, String data){
		System.out.println("Building "+id);
		Packet packet = packets[id];
		packet.setSenderIP(senderIP);
		packet.setData(data);
		
		return packet;
	}
	
	public static void log(Packet packet){
		
		Calendar date = Calendar.getInstance();
		int hours = date.HOUR_OF_DAY;
		int minutes = date.MINUTE;
		int seconds = date.SECOND;
		
		String time = hours+":"+minutes+":"+seconds;
		
		System.out.println("[" + time + "] " + "received package " + packet.getPacketID() + " containing " + packet.getData());
	}
}
