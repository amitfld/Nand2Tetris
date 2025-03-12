import re
import os
from os import listdir
from os.path import isfile, isdir, basename

INVALID_ARGS_MESSAGE = "Invalid input file or directory provided."
OUTPUT_EXTENSION = ".xml"
INPUT_EXTENSION = r"\.jack$"
INPUT_PATTERN = re.compile(INPUT_EXTENSION, re.IGNORECASE)  # Added re.IGNORECASE

class JackAnalyzer:
    """
    JackAnalyzer module for handling .jack file analysis and CompilationEngine execution.
    """

    @staticmethod
    def get_files(input_args):
        """
        :param input_args: Arguments passed to the program.
        :return: A list of .jack file paths.
        """
        print(f"Received arguments: {input_args}")

        file_paths = []
        input_path = input_args[1]
        print(f"Checking input path: {input_path}")

        if isfile(input_path):
            print(f"Path is a file")
            if INPUT_PATTERN.search(basename(input_path)):
                print(f"File matches .jack pattern")
                file_paths.append(input_path)
            else:
                print(f"File does not match .jack pattern")
        elif isdir(input_path):
            print(f"Path is a directory")
            for filename in listdir(input_path):
                if INPUT_PATTERN.search(filename):
                    file_paths.append(os.path.join(input_path, filename))
        else:
            print(f"Path is neither a valid file nor directory")

        print(f"Found files: {file_paths}")
        return file_paths

    @staticmethod
    def generate_output_path(input_path):
        """
        :param input_path: Path to the source .jack file.
        :return: Corresponding output .xml file path.
        """
        output_path = re.sub(INPUT_EXTENSION, OUTPUT_EXTENSION, input_path)
        print(f"Generated output path: {output_path}")
        return output_path
