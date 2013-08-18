/*Copyright (c) 2011, Raedism
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    *Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    *Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED 
TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/


import java.net.*;

public class server implements Runnable {

	public server() {
		// the current way of controlling the server at runtime and a great
		// debugging/testing tool
		// jserv js = new jserv(this);
		// js.start();

	}

	// TODO: yet to figure out proper value for timing, but 500 seems good
	public static final int cycleTime = 390;
	public static boolean updateServer = false;
	public static int updateSeconds = 180; // 180 because it doesnt make the time jump at the start :P
	public static long startTime;

	public static void main(java.lang.String args[]) {
		clientHandler = new server();
		(new Thread(clientHandler)).start(); // launch server listener
		playerHandler = new PlayerHandler();
		npcHandler = new NPCHandler();
		itemHandler = new ItemHandler();
		shopHandler = new ShopHandler();
		antilag = new antilag();
		itemspawnpoints = new itemspawnpoints();
		GraphicsHandler = new GraphicsHandler();
		objectHandler = new ObjectHandler();

		int waitFails = 0;
		long lastTicks = System.currentTimeMillis();
		int cycle = 0;
		BanManager.Initialize();
		while (!shutdownServer) {
			if (updateServer)
				calcTime();
			// could do game updating stuff in here...
			// maybe do all the major stuff here in a big loop and just do the
			// packet
			// sending/receiving in the client subthreads. The actual packet
			// forming code
			// will reside within here and all created packets are then relayed
			// by the subthreads.
			// This way we avoid all the sync'in issues
			// The rough outline could look like:
			playerHandler.process(); // updates all player related stuff
			npcHandler.process();
			itemHandler.process();
			shopHandler.process();
			antilag.process();
			itemspawnpoints.process();
			objectHandler.process();
			objectHandler.firemaking_process();
			System.gc();
			// doNpcs() // all npc related stuff
			// doObjects()
			// doWhatever()

			// taking into account the time spend in the processing code for
			// more accurate timing
			long timeSpent = System.currentTimeMillis() - lastTicks;
			if (timeSpent >= cycleTime) {
				timeSpent = cycleTime;
				if (++waitFails > 100) {
					// shutdownServer = true;
					misc.println("[KERNEL]: machine is too slow to run this server!");
				}
			}
			try {
				Thread.sleep(cycleTime - timeSpent);
			} catch (java.lang.Exception _ex) {
			}
			lastTicks = System.currentTimeMillis();
			cycle++;
			if (cycle % 100 == 0) {
			}
			if (cycle % 3600 == 0) {
				System.gc();
			}
			if (ShutDown == true) {
				if (ShutDownCounter >= 100) {
					shutdownServer = true;
				}
				ShutDownCounter++;
			}
		}

		// shut down the server
		playerHandler.destruct();
		clientHandler.killServer();
		clientHandler = null;
	}
	
	public Socket acceptSocketSafe(ServerSocket x) {
		   boolean socketFound = false;
		   Socket s = null;
		   do {
		      try {
		      s = x.accept();
		      int i = s.getInputStream().read();
		      if ((i & 0xFF) == 14) {
		        socketFound = true;
		      }
		   } catch (Exception e) {
		   }
		} while (!socketFound);

		return s;
		}

	public static server clientHandler = null;
	public static java.net.ServerSocket clientListener = null;
	public static boolean shutdownServer = false;
	public static boolean shutdownClientHandler;
	public static int serverlistenerPort = 43594; // 43594=default
	public static PlayerHandler playerHandler = null;
	public static NPCHandler npcHandler = null;
	public static ItemHandler itemHandler = null;
	public static ShopHandler shopHandler = null;
	public static antilag antilag = null;
	public static itemspawnpoints itemspawnpoints = null;
	public static GraphicsHandler GraphicsHandler = null;
	public static ObjectHandler objectHandler = null;

	public static void calcTime() {
		long curTime = System.currentTimeMillis();
		updateSeconds = 180 - ((int) (curTime - startTime) / 1000);
		if (updateSeconds == 0) {
			shutdownServer = true;
		}
	}

	@Override
	public void run() {
		try {
			shutdownClientHandler = false;
			clientListener = new java.net.ServerSocket(serverlistenerPort, 1, null);
			misc.println("Server started successfully on port " + serverlistenerPort + ".");

			while (true) {
				java.net.Socket s = acceptSocketSafe(clientListener);
				s.setTcpNoDelay(true);
				String connectingAddress = s.getInetAddress().getHostAddress();

				if (clientListener != null) {
					if (!BanManager.BannedAddress(connectingAddress))

					{
						misc.println("ClientHandler: Accepted from " + connectingAddress + ":" + s.getPort());
						playerHandler.newPlayerClient(s, connectingAddress);
					} else {
						misc.println("ClientHandler: Rejected from " + connectingAddress + ":" + s.getPort());
						s.close();
					}
				}
			}
		} catch (java.io.IOException ioe) {
			if (!shutdownClientHandler) {
				misc.println("Error: Unable to startup listener on " + serverlistenerPort + " - port already in use?");
			} else {
				misc.println("ClientHandler was shut down.");
			}
		}
	}

	public void killServer() {
		try {
			shutdownClientHandler = true;
			if (clientListener != null)
				clientListener.close();
				clientListener = null;
		} catch (java.lang.Exception __ex) {
			__ex.printStackTrace();
		}
	}

	public static int EnergyRegian = 60;

	public static int MaxConnections = 300;
	public static String[] Connections = new String[MaxConnections];
	public static int[] ConnectionCount = new int[MaxConnections];
	public static boolean ShutDown = false;
	public static int ShutDownCounter = 0;

}
