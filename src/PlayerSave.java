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


import java.io.Serializable;

public class PlayerSave implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String playerPass = "";
	public String playerName = "";
	// public String connectedFrom=""; //Don't enable this yet, or the old
	// save-files get corrupted
	public int playerPosX;
	public int playerPosY;
	public int playerHeight;
	public int playerRights;
	public int playerStatus;
	public int playerHeadIcon;
	public int[] playerItem;
	public int[] playerItemN;
	public int[] playerEquipment;
	public int[] playerEquipmentN;
	public int[] bankItems;
	public int[] bankItemsN;
	public int[] playerLevel;
	public int[] playerXP;
	public int[] playerQuest;
	public int currentHealth;
	public int maxHealth;

	public PlayerSave(Player plr) {
		playerPass = plr.playerPass;
		playerName = plr.playerName;
		playerPosX = plr.absX;
		playerPosY = plr.absY;
		playerHeight = plr.heightLevel;
		playerRights = plr.playerRights;
		playerItem = plr.playerItems;
		playerItemN = plr.playerItemsN;
		bankItems = plr.bankItems;
		bankItemsN = plr.bankItemsN;
		playerEquipment = plr.playerEquipment;
		playerEquipmentN = plr.playerEquipmentN;
		playerLevel = plr.playerLevel;
		maxHealth = plr.playerLevel[3];
		currentHealth = plr.maxHealth;
		playerXP = plr.playerXP;
		// connectedFrom = plr.connectedFrom;

	}
}