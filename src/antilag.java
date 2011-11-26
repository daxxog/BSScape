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


import java.io.IOException;

public class antilag {

	public int ResetTimer = 0;

	public void resetserver() {
		misc.println("RESETING SERVER!!!");
		misc.println("Saving all games...");
		PlayerHandler.kickAllPlayers = true;
		misc.println("GAME SUCCESSFULLY SAVED FOR ALL PLAYERS");
		ResetTimer = 0;
		closeListener();
		runserver();
	}

	public void process() {
		ResetTimer += 1;
		if (ResetTimer >= 999999) {
			resetserver();
		}
	}

	public void runserver() {
		try {
			String run = "runserver.bat";
			String xstr = "./" + run;
			Runtime.getRuntime().exec(xstr);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void closeListener() {
		try {
			server.shutdownClientHandler = true;
			if (server.clientListener != null)
				server.clientListener.close();
			server.clientListener = null;
		} catch (java.lang.Exception __ex) {
			__ex.printStackTrace();
		}
	}
}