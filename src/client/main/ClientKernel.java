package client.main;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import client.entities.Player;
import client.gui.DisplayManager;
import client.net.ClientSocket;
import client.net.PacketHandler;
import client.net.packets.Packet;
import client.res.Assets;
import client.utils.Vector2;

public class ClientKernel {

	private ClientSocket socket;
	
	private BufferStrategy bs;
	private Graphics2D g;
	
	private static List<Player> players = new ArrayList<Player>();
	
	private boolean running;
	
	private static final int FPS = 60;
	
	public ClientKernel(){
		
	}
	
	public static void main(String[] ags){
		
		ClientKernel kernel = new ClientKernel();
		kernel.start();
	}
	
	/*
	 * Initiates a connection to the server
	 * 
	 * Creates a Display
	 * 
	 * Starts the gameloop
	 * 
	 * Stops clean afterwards
	 */
	public void start(){
		
		running = true;
		
		Assets.init();
		
		socket = new ClientSocket();
		socket.connect("localhost", 8888);
		
		socket.listen();
		
		PacketHandler.loadPackets();
		
		float duration = 1000 / FPS;
		float delta = 0;
		
		double last = System.currentTimeMillis();
		double now;
		
		Packet packet = PacketHandler.buildPacket(1, null, "Hello there");
		socket.send(packet);
		
		DisplayManager.createDisplay(800, 600);
		
		while(running){
			
			now = System.currentTimeMillis();
			
			delta += now - last;
			
			if(delta >= duration){
				
				tick();
				render();
				
				delta = 0;
			}
		}
		
		stop();
	}
	
	public void tick(){
		
	}
	
	/*
	 * Central render loop
	 * 
	 * Creates a BufferStrategy and draws everything to the screen with its Graphics object
	 */
	public void render(){
		if(bs == null){
			DisplayManager.getCanvas().createBufferStrategy(2);
			bs = DisplayManager.getCanvas().getBufferStrategy();
			
		}
		
		g = (Graphics2D) bs.getDrawGraphics();

		
		for(Player player : players){
			player.render(g);
		}
		
		bs.show();
	}
	/*
	 * Stops the program and cleans up
	 */
	public void stop(){
		
		DisplayManager.destroyDispaly();
	}
	
	public ClientSocket getClientSocket(){
		return this.socket;
	}
	
	public static void addPlayer(Player player){
		
		players.add(player);
	}
	
	public static void movePlayer(int playerID, Vector2 position){
		
		players.get(playerID).setPosition(position);
	}
}