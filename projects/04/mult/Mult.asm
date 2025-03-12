// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

// Initialize i and mult to 0
@i
M=1
@mult
M=0
// Start loop for calculation
(LOOP)
    // if (i > R1) goto STOP
    @i
    D=M
    @R1
    D=D-M
    @STOP
    D;JGT
    // mult = mult + R0
    @mult
    D=M 
    @R0
    D=D+M
    @mult
    M=D
    // i = i + 1
    @i
    M=M+1
    // goto LOOP
    @LOOP
    0;JMP
(STOP)
    // R2 = mult
    @mult
    D=M
    @R2
    M=D
// infinite loop
(END)
    @END
    0;JMP


