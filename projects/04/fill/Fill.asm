// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

// Fill.asm
(LOOP)
    // Initialize startat variable for the start of the screen size
    @16384
    D=A
    @startat
    M=D
    // Load the keyboard memory address
    @24576
    D=M
    @DRAW_BLACK_LOOP
    D;JNE            // If any key is pressed (D != 0), jump to DRAW_BLACK
    (CLEAR_LOOP)
        // if (startat >= 24576) stop the loop
        @startat
        D=M
        @24576
        D=A-D
        @STOP_CLEAR_LOOP
        D;JLE
        // Change the screen bit to white
        @startat
        A=M
        M=0         
        // increase index
        @startat
        M=M+1
        @CLEAR_LOOP
        0;JMP
    (STOP_CLEAR_LOOP)
    @LOOP
    0;JEQ
    (DRAW_BLACK_LOOP)
        // if (startat >= 24576) stop the loop
        @startat
        D=M
        @24576
        D=A-D
        @STOP_BLACK_LOOP
        D;JLE
        // Change the screen bit to black
        @startat
        A=M
        M=-1
        // increase index
        @startat
        M=M+1
        @DRAW_BLACK_LOOP
        0;JMP
    (STOP_BLACK_LOOP)

@LOOP
0;JMP
