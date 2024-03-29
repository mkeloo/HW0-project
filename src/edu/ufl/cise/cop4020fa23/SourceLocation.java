/*Copyright 2023 by Beverly A Sanders
 * 
 * This code is provided for solely for use of students in COP4020 Programming Language Concepts at the 
 * University of Florida during the fall semester 2023 as part of the course project.  
 * 
 * No other use is authorized. 
 * 
 * This code may not be posted on a public web site either during or after the course.  
 */
package edu.ufl.cise.cop4020fa23;

/**
 * 
 */
public record SourceLocation(int line, int column) {
    public SourceLocation(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int line() {
        return this.line;
    }

    public int column() {
        return this.column;
    }
}




