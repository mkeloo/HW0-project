//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.ufl.cise.cop4020fa23;

import java.util.Objects;

public class Token implements IToken {
	final Kind kind;
	final int pos;
	final int length;
	final char[] source;
	final SourceLocation location;

	public Token(Kind kind, int pos, int length, char[] source, SourceLocation location) {
		this.kind = kind;
		this.pos = pos;
		this.length = length;
		this.source = source;
		this.location = location;
	}

	public SourceLocation sourceLocation() {
		return this.location;
	}

	public Kind kind() {
		return this.kind;
	}

	public String text() {
		return new String(this.source);
	}

	public String toString() {
		String var10000 = String.valueOf(this.kind);
		return "[" + var10000 + " " + this.text() + "]";
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.kind, this.length, this.location, this.pos});
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			Token other = (Token)obj;
			return this.kind == other.kind && this.length == other.length && Objects.equals(this.location, other.location) && this.pos == other.pos;
		}
	}
}
