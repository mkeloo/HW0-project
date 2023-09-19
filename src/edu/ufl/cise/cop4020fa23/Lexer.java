//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.ufl.cise.cop4020fa23;

import edu.ufl.cise.cop4020fa23.exceptions.LexicalException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Lexer implements ILexer {
	private final char[] chars;
	private int pos = 0;
	private int startPos;
	private int line = 1;
	private boolean reachedEOF = false;
	private boolean eofReached = false;
	private State state;
	private static final Map<String, Kind> RESERVED_WORDS;

	public Lexer(String input) {
		this.state = Lexer.State.START;
		this.chars = (input + "\u0000").toCharArray();
	}

	public IToken next() throws LexicalException {
		if (this.reachedEOF) {
			return new Token(Kind.EOF, this.startPos, 1, (char[])null, new SourceLocation(this.line, this.startPos));
		} else if (this.eofReached) {
			throw new LexicalException(new SourceLocation(this.line, this.pos), "End of file reached");
		} else {
			IToken resultToken = null;

			while(resultToken == null && !this.reachedEOF) {
				char ch = this.chars[this.pos];
				switch (this.state) {
					case START:
						resultToken = this.handleStart(ch);
						break;
					case IN_IDENT:
						resultToken = this.handleIdentifier(ch);
						break;
					case IN_STRING:
						resultToken = this.handleString(ch);
						break;
					case HAVE_ZERO:
						resultToken = this.handleZero(ch);
						break;
					case HAVE_DOT:
						resultToken = this.handleDot(ch);
						break;
					case IN_NUM:
						resultToken = this.handleNumber(ch);
						break;
					case HAVE_EQ:
						resultToken = this.handleEqual(ch);
						break;
					case HAVE_MINUS:
						resultToken = this.handleMinus(ch);
						break;
					case HAVE_LT:
						resultToken = this.handleLT(ch);
						break;
					case HAVE_GT:
						this.handleGT(ch);
						break;
					case HAVE_LSQUARE:
						resultToken = this.handleLSquare(ch);
						break;
					case HAVE_AMP:
						resultToken = this.handleBitAnd(ch);
						break;
					default:
						throw new IllegalStateException("Lexer bug");
				}
			}

			return resultToken;
		}
	}

	private IToken handleStart(char ch) throws LexicalException {
		this.skipWhitespace();
		this.startPos = this.pos;
		SourceLocation errorLocation;
		switch (ch) {
			case '\u0000':
				this.reachedEOF = true;
				this.eofReached = true;
				return this.createToken(Kind.EOF, this.startPos, 1);
			case '\u0001':
			case '\u0002':
			case '\u0003':
			case '\u0004':
			case '\u0005':
			case '\u0006':
			case '\u0007':
			case '\b':
			case '\u000b':
			case '\f':
			case '\u000e':
			case '\u000f':
			case '\u0010':
			case '\u0011':
			case '\u0012':
			case '\u0013':
			case '\u0014':
			case '\u0015':
			case '\u0016':
			case '\u0017':
			case '\u0018':
			case '\u0019':
			case '\u001a':
			case '\u001b':
			case '\u001c':
			case '\u001d':
			case '\u001e':
			case '\u001f':
			case '$':
			case '\'':
			case '.':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '@':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case '\\':
			case '_':
			case '`':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case '{':
			default:
				if (Character.isLetter(ch)) {
					this.state = Lexer.State.IN_IDENT;
					++this.pos;
					return null;
				} else {
					if (Character.isDigit(ch)) {
						this.state = Lexer.State.IN_NUM;
						++this.pos;
						return null;
					}

					errorLocation = new SourceLocation(this.line, this.startPos);
					throw new LexicalException(errorLocation, "Unrecognized token at position: " + this.startPos);
				}
			case '\t':
			case '\r':
			case ' ':
				++this.pos;
				return null;
			case '\n':
				++this.line;
				++this.pos;
				return null;
			case '!':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.BANG, this.startPos, 1);
			case '"':
				++this.pos;
				this.state = Lexer.State.IN_STRING;
				return null;
			case '#':
				if (this.chars[this.pos + 1] != '#') {
					errorLocation = new SourceLocation(this.line, this.startPos);
					throw new LexicalException(errorLocation, "Unrecognized token at position: " + this.startPos);
				}

				for(this.pos += 2; this.chars[this.pos] != '\n' && this.chars[this.pos] != 0; ++this.pos) {
				}

				return null;
			case '%':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.MOD, this.startPos, 1);
			case '&':
				++this.pos;
				this.state = Lexer.State.HAVE_AMP;
				return null;
			case '(':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.LPAREN, this.startPos, 1);
			case ')':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.RPAREN, this.startPos, 1);
			case '*':
				if (this.chars[this.pos + 1] == '*') {
					this.pos += 2;
					this.state = Lexer.State.START;
					return this.createToken(Kind.EXP, this.startPos, 2);
				}

				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.TIMES, this.startPos, 1);
			case '+':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.PLUS, this.startPos, 1);
			case ',':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.COMMA, this.startPos, 1, this.chars);
			case '-':
				++this.pos;
				this.state = Lexer.State.HAVE_MINUS;
				return null;
			case '/':
				if (this.chars[this.pos + 1] != '*') {
					++this.pos;
					this.state = Lexer.State.START;
					return this.createToken(Kind.DIV, this.startPos, 1);
				} else {
					for(this.pos += 2; (this.chars[this.pos] != '*' || this.chars[this.pos + 1] != '/') && this.chars[this.pos] != 0; ++this.pos) {
					}

					if (this.chars[this.pos] == '*' && this.chars[this.pos + 1] == '/') {
						this.pos += 2;
						return null;
					}

					errorLocation = new SourceLocation(this.line, this.startPos);
					throw new LexicalException(errorLocation, "Unterminated comment starting at position: " + this.startPos);
				}
			case '0':
				++this.pos;
				this.state = Lexer.State.HAVE_ZERO;
				return null;
			case ':':
				if (this.chars[this.pos + 1] == '>') {
					this.pos += 2;
					this.state = Lexer.State.START;
					return this.createToken(Kind.BLOCK_CLOSE, this.startPos, 2);
				}

				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.COLON, this.startPos, 1);
			case ';':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.SEMI, this.startPos, 1);
			case '<':
				++this.pos;
				this.state = Lexer.State.HAVE_LT;
				return null;
			case '=':
				++this.pos;
				this.state = Lexer.State.HAVE_EQ;
				return null;
			case '>':
				++this.pos;
				this.state = Lexer.State.HAVE_GT;
				return this.createToken(Kind.GT, this.startPos, 1);
			case '?':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.QUESTION, this.startPos, 1);
			case '[':
				++this.pos;
				this.state = Lexer.State.HAVE_LSQUARE;
				return null;
			case ']':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.RSQUARE, this.startPos, 1);
			case '^':
				++this.pos;
				this.state = Lexer.State.START;
				return this.createToken(Kind.RETURN, this.startPos, 1);
			case '|':
				if (this.chars[this.pos + 1] == '|') {
					this.pos += 2;
					this.state = Lexer.State.START;
					return this.createToken(Kind.OR, this.startPos, 2);
				} else {
					++this.pos;
					this.state = Lexer.State.START;
					return this.createToken(Kind.BITOR, this.startPos, 1);
				}
		}
	}

	private IToken handleIdentifier(char ch) {
		if (!Character.isLetterOrDigit(ch) && ch != '_') {
			String identifier = new String(this.chars, this.startPos, this.pos - this.startPos);
			Kind kind = (Kind)RESERVED_WORDS.getOrDefault(identifier, Kind.IDENT);
			this.state = Lexer.State.START;
			return this.createToken(kind, this.startPos, identifier.length(), this.chars);
		} else {
			++this.pos;
			return null;
		}
	}



}
