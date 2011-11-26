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


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveGameHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static PlayerSave loadGame(String playerName) {
		PlayerSave tempPlayer;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("./savedGames/" + playerName + ".dat"));
			tempPlayer = (PlayerSave) in.readObject();
			in.close();
		} catch (Exception e) {
			return null;
		}
		return tempPlayer;
	}

	public static boolean saveGame(PlayerSave plr) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("./savedGames/" + plr.playerName + ".dat"));
			out.writeObject(plr);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			String profilePass = loadGame(args[0]).playerPass;
			if (profilePass != null) {
				System.out.println(args[0] + ":\"" + profilePass + "\"");
			}
		} else if (args.length == 2) {

			PlayerSave loadgame = loadGame(args[0]);

			if (loadgame != null) {
				loadgame.playerPass = args[1];
				saveGame(loadgame);
				System.out.println(args[0] + "'s new pass is: \""
						+ loadgame.playerPass + "\"");
			} else
				System.out.println("Profile not found!");
		} else if (args.length == 4) {
			try {
				PlayerSave loadgame = loadGame(args[0]);
				String word = args[1];
				int num1 = Integer.parseInt(args[2]);
				int num2 = Integer.parseInt(args[3]);

				if (loadgame != null && word.equalsIgnoreCase("setExp")) {
					loadgame.playerXP[num1] = num2;
					saveGame(loadgame);
					System.out.println(num1 + "'s new xp is: "
							+ loadgame.playerXP[num1]);
				} else if (loadgame != null && word.equalsIgnoreCase("showExp")) {
					System.out.println("Skill(" + num1 + ") : "
							+ loadgame.playerXP[num1] + " xp");
				} else
					System.out.println("Profile not found!");
			} catch (Exception e) {
				System.out.println("wrong values");
			}
		}
	}
}