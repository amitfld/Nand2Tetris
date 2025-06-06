from enum import Enum

class Constant(Enum):
    """
    Enum to represent the type of VM commands.
    """
    C_POP = "C_POP"
    C_PUSH = "C_PUSH"
    C_ARITHMETIC = "C_ARITHMETIC"

class Parser:
    """
    Handles the parsing of a single .vm file.
    Reads VM commands, parses them into their lexical components,
    and provides convenient access to these components.
    """
    def __init__(self, filename: str):
        """
        Opens the input file and prepares to parse it.

        :param filename: The path to the .vm file to be parsed.
        """
        self.filename = filename
        self.file = open(filename, 'r')
        self.buffer = None
        self.current_command = None

    def has_more_lines(self) -> bool:
        """
        Checks if there are more lines to read in the input file.

        :return: True if there are more lines to read, False otherwise.
        """
        if self.buffer is None:
            pos = self.file.tell()
            self.buffer = self.file.readline().strip()
            self.file.seek(pos)
        return bool(self.buffer)

    def advance(self):
        """
        Reads the next command in the input file and makes it the current command.
        Should only be called if `has_more_lines()` is True.
        """
        while self.has_more_lines():
            self.buffer = self.file.readline()
            if self.buffer.strip().startswith('//') or self.buffer.strip() == "":
                continue
            else:
                self.current_command = self.buffer
                self.buffer = None
                break
        self.buffer = None

    def command_type(self) -> Constant:
        """
        Determines the type of the current VM command.

        :return: A Constant indicating the command type:
                 - C_ARITHMETIC for arithmetic commands.
                 - C_PUSH for push commands.
                 - C_POP for pop commands.
        """
        line = self.current_command
        if "pop" in line:
            return Constant.C_POP
        elif "push" in line:
            return Constant.C_PUSH
        else:
            return Constant.C_ARITHMETIC

    def arg1(self) -> str:
        """
        Gets the first argument of the current VM command.
        In the case of C_ARITHMETIC, this is the command itself.

        :return: The first argument of the command as a string.
        """
        if self.command_type() == Constant.C_ARITHMETIC:
            return self.current_command
        return self.current_command.split()[1].split()[0]

    def arg2(self) -> int:
        """
        Gets the second argument of the current VM command.
        Should only be called if the current command is C_PUSH, C_POP,

        :return: The second argument of the command as an integer.
        """
        if self.command_type() in [Constant.C_POP, Constant.C_PUSH]:
            return int(self.current_command.split()[2])
