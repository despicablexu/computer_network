/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

public class Base64Encoder {
	public static byte[] encode(byte[] inbuf) {
		int size = inbuf.length;
		byte[] outbuf = new byte[((size + 2) / 3) * 4];
		int inpos, outpos;
		int val;

		for (inpos = 0, outpos = 0; size >= 3; size -= 3, outpos += 4) {
			val = inbuf[inpos++] & 0xff;
			val <<= 8;
			val |= inbuf[inpos++] & 0xff;
			val <<= 8;
			val |= inbuf[inpos++] & 0xff;

			outbuf[outpos + 3] = (byte) pem_array[val & 0x3f];
			val >>= 6;
			outbuf[outpos + 2] = (byte) pem_array[val & 0x3f];
			val >>= 6;
			outbuf[outpos + 1] = (byte) pem_array[val & 0x3f];
			val >>= 6;
			outbuf[outpos + 0] = (byte) pem_array[val & 0x3f];
		}

		if (size == 1) {
			val = inbuf[inpos++] & 0xff;
			val <<= 4;

			outbuf[outpos + 3] = (byte) '='; // pad character;
			outbuf[outpos + 2] = (byte) '='; // pad character;
			outbuf[outpos + 1] = (byte) pem_array[val & 0x3f];
			val >>= 6;
			outbuf[outpos + 0] = (byte) pem_array[val & 0x3f];
		} else if (size == 2) {
			val = inbuf[inpos++] & 0xff;
			val <<= 8;
			val |= inbuf[inpos++] & 0xff;
			val <<= 2;

			outbuf[outpos + 3] = (byte) '='; // pad character;
			outbuf[outpos + 2] = (byte) pem_array[val & 0x3f];
			val >>= 6;
			outbuf[outpos + 1] = (byte) pem_array[val & 0x3f];
			val >>= 6;
			outbuf[outpos + 0] = (byte) pem_array[val & 0x3f];
		}
		return outbuf;
	}

	private final static char pem_array[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', // 0
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', // 1
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', // 2
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', // 3
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', // 4
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', // 5
			'w', 'x', 'y', 'z', '0', '1', '2', '3', // 6
			'4', '5', '6', '7', '8', '9', '+', '/' // 7
	};
}

