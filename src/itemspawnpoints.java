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


public class itemspawnpoints {
	public int spawntimer = 0;

	public void LoadItems() {
		for (int i = 0; i <= 5000; i++) {
			if (spawntimer <= 1) {
				ItemHandler.addItem(347, 3066, 3952, 1,
						ItemHandler.globalItemController[i], false); // herring
				ItemHandler.addItem(347, 3253, 3462, 1,
						ItemHandler.globalItemController[i], false); // herring
				ItemHandler.addItem(347, 3096, 3956, 1,
						ItemHandler.globalItemController[i], false); // herring
				ItemHandler.addItem(1351, 3259, 3433, 1,
						ItemHandler.globalItemController[i], false); // bronze
																		// axe
				ItemHandler.addItem(1351, 3194, 3424, 1,
						ItemHandler.globalItemController[i], false); // bronze
																		// axe
				ItemHandler.addItem(2339, 2760, 3481, 1,
						ItemHandler.globalItemController[i], false); // Leaf For
																		// Invisible
																		// Armour
																		// Quest
				ItemHandler.addItem(1944, 3229, 3299, 1,
						ItemHandler.globalItemController[i], false); // Egg
				ItemHandler.addItem(2166, 3260, 3437, 1,
						ItemHandler.globalItemController[i], false); // Gnomebowl
																		// mould
				ItemHandler.addItem(793, 2588, 3091, 1,
						ItemHandler.globalItemController[i], false); // Daconia
																		// Rock
																		// For
																		// Spells
																		// Of
																		// The
																		// Gods
																		// Quest
				ItemHandler.addItem(1917, 3252, 3439, 1,
						ItemHandler.globalItemController[i], false); // Light
																		// beer

				ItemHandler.addItem(3801, 3269, 3434, 1,
						ItemHandler.globalItemController[i], false); // Keg of
																		// beer
				spawntimer = 100;
			}
		}
	}

	public void process() {
		LoadItems();
		spawntimer -= 1;
	}
}