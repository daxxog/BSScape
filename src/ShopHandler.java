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


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ShopHandler {
	public static int MaxShops = 101; // 1 more because we don't use [0] !
	public static int MaxShopItems = 101; // 1 more because we don't use [0] !
	public static int MaxInShopItems = 40;
	public static int MaxShowDelay = 60;
	public static int TotalShops = 0;
	public static int[][] ShopItems = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsN = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsDelay = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsSN = new int[MaxShops][MaxShopItems];
	public static int[] ShopItemsStandard = new int[MaxShops];
	public static String[] ShopName = new String[MaxShops];
	public static int[] ShopSModifier = new int[MaxShops];
	public static int[] ShopBModifier = new int[MaxShops];

	ShopHandler() {
		for (int i = 0; i < MaxShops; i++) {
			for (int j = 0; j < MaxShopItems; j++) {
				ResetItem(i, j);
				ShopItemsSN[i][j] = 0; // Special resetting, only at begin !
			}
			ShopItemsStandard[i] = 0; // Special resetting, only at begin !
			ShopSModifier[i] = 0;
			ShopBModifier[i] = 0;
			ShopName[i] = "";
		}
		TotalShops = 0;
		loadShops("shops.cfg");
	}

	public void process() {
		boolean DidUpdate = false;
		for (int i = 1; i <= TotalShops; i++) {
			for (int j = 0; j < MaxShopItems; j++) {
				if (ShopItems[i][j] > 0) {
					if (ShopItemsDelay[i][j] >= MaxShowDelay) {
						if (j <= ShopItemsStandard[i] && ShopItemsN[i][j] <= ShopItemsSN[i][j]) {
							if (ShopItemsN[i][j] < ShopItemsSN[i][j]) {
								ShopItemsN[i][j] += 1; // if amount lower then
														// standard, increase it
														// !
							}
						} else {
							DiscountItem(i, j);
						}
						ShopItemsDelay[i][j] = 0;
						DidUpdate = true;
					}
					ShopItemsDelay[i][j]++;
				}
			}
			if (DidUpdate == true) {
				for (int k = 1; k < PlayerHandler.maxPlayers; k++) {
					if (PlayerHandler.players[k] != null) {
						if (PlayerHandler.players[k].IsShopping == true && PlayerHandler.players[k].MyShopID == i) {
							PlayerHandler.players[k].UpdateShop = true;
						}
					}
				}
				DidUpdate = false;
			}
		}
	}

	public void DiscountItem(int ShopID, int ArrayID) {
		ShopItemsN[ShopID][ArrayID] -= 1;
		if (ShopItemsN[ShopID][ArrayID] <= 0) {
			ShopItemsN[ShopID][ArrayID] = 0;
			ResetItem(ShopID, ArrayID);
		}
	}

	public void ResetItem(int ShopID, int ArrayID) {
		ShopItems[ShopID][ArrayID] = 0;
		ShopItemsN[ShopID][ArrayID] = 0;
		ShopItemsDelay[ShopID][ArrayID] = 0;
	}

	public boolean loadShops(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[(MaxShopItems * 2)];
		boolean EndOfFile = false;
		BufferedReader shopcfg = null;
		try {
			shopcfg = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			misc.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = shopcfg.readLine();
		} catch (IOException ioexception) {
			misc.println(FileName + ": error loading file.");
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("shop")) {
					int ShopID = Integer.parseInt(token3[0]);
					ShopName[ShopID] = token3[1].replaceAll("_", " ");
					ShopSModifier[ShopID] = Integer.parseInt(token3[2]);
					ShopBModifier[ShopID] = Integer.parseInt(token3[3]);
					for (int i = 0; i < ((token3.length - 4) / 2); i++) {
						if (token3[(4 + (i * 2))] != null) {
							ShopItems[ShopID][i] = (Integer.parseInt(token3[(4 + (i * 2))]) + 1);
							ShopItemsN[ShopID][i] = Integer.parseInt(token3[(5 + (i * 2))]);
							ShopItemsSN[ShopID][i] = Integer.parseInt(token3[(5 + (i * 2))]);
							ShopItemsStandard[ShopID]++;
						} else {
							break;
						}
					}
					TotalShops++;
				}
			} else {
				if (line.equals("[ENDOFSHOPLIST]")) {
					try {
						shopcfg.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = shopcfg.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			shopcfg.close();
		} catch (IOException ioexception) {
		}
		return false;
	}
}
