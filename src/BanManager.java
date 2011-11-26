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


import java.util.ArrayList;

public class BanManager {

	public final static void Initialize() {
		AddName("SYIpkpker");
		AddName("SilabSoft");

		// AddAddress("127.0.0.1"); // Example :P
	}

	public final static void AddName(String Name) {
		Names.add(Name.toLowerCase());
	}

	public final static boolean BannedName(String _Name) {
		_Name = _Name.toLowerCase();

		for (String Name : Names)
			if (_Name.contains(Name))
				return true;

		for (Character c : _Name.toCharArray())
			// <3 Silabsoft
			if (!Character.isLetterOrDigit(c))
				if (!Character.isSpaceChar(c))
					return true;

		return false;
	}

	public final static void AddAddress(String Address) {
		Addresses.add(Address.toLowerCase());
	}

	public final static boolean BannedAddress(String _Address) {
		_Address = _Address.toLowerCase();

		for (String Address : Addresses)
			if (Address.equals(_Address))
				return true;

		return false;
	}

	public static ArrayList<String> Names = new ArrayList<String>();
	public static ArrayList<String> Addresses = new ArrayList<String>();
}