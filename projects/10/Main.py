import sys
from JackAnalyzer import JackAnalyzer
from CompilationEngine import CompilationEngine

def main():
    """
    Main driver for the JackAnalyzer program. Processes each .jack file using CompilationEngine.
    """
    try:
        print("Starting JackAnalyzer...")
        if len(sys.argv) != 2:
            print("Usage: python Main.py <input_file_or_directory>")
            sys.exit(1)

        source_files = JackAnalyzer.get_files(sys.argv)
        if not source_files:
            print("No .jack files found to process!")
            sys.exit(1)

        for source_path in source_files:
            print(f"Processing file: {source_path}")
            try:
                engine_instance = CompilationEngine(
                    source_path, JackAnalyzer.generate_output_path(source_path)
                )
                engine_instance.compileClass()
                print(f"Finished processing: {source_path}")
            except Exception as e:
                print(f"Error processing {source_path}: {str(e)}")
                raise
    except Exception as e:
        print(f"Program error: {str(e)}")
        raise

if __name__ == "__main__":
    main()
